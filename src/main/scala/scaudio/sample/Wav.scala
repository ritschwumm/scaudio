package scaudio.sample

import java.io._
import java.nio.ByteOrder
import java.nio.ShortBuffer
import java.nio.channels.FileChannel 

import scutil.Implicits._
import scutil.Resource._
import scutil.log._
import scutil.tried._

/** audio sample loaded from a wav file using a MappedByteBuffer */
object Wav extends Logging {
	/** may throw exception if not successful */
	def load(file:File):Tried[Exception,Sample] =
			Tried exceptionCatch loadImpl(file)
	
	/** may throw exception if not successful */
	private def loadImpl(file:File):Sample = {
		/*
		// raw file works like this
		val	mappedBuffer	= new RandomAccessFile(decoded, "r").getChannel use { fc => 
			fc map (FileChannel.MapMode.READ_ONLY, 0, decoded.length)
		}
		// madplay in raw mode outputs native order
		mappedBuffer order ByteOrder.nativeOrder
		mappedBuffer.asShortBuffer
		*/
		
		// little endian 4 characters
		def mkTag(s:String):Long	= {
			val bytes	= s getBytes "US-ASCII"
			require(bytes.length == 4, "expected exactly 4 characters")
			((bytes(3) & 0xff) << 24) |
			((bytes(2) & 0xff) << 16) |
			((bytes(1) & 0xff) <<  8) |
			((bytes(0) & 0xff) <<  0)
		}
	
		val	mapped	= new RandomAccessFile(file, "r").getChannel use { fc => 
			fc map (FileChannel.MapMode.READ_ONLY, 0, file.length)
		}
		// mapped.load()
		mapped order ByteOrder.LITTLE_ENDIAN 
		// RIFX instead of RIFF for big endian data
		// 8 bit is unsigned, 16 bit is signed
		// left channel comes before right channel
		if (mapped.remaining < 12)	throw WavFormatException("cannot find RIFF header")
		val	tag1	= mapped.getInt
		if (tag1 != mkTag("RIFF"))	throw WavFormatException("expected RIFF header")
		val	flen	= mapped.getInt
		val	tag2	= mapped.getInt
		if (tag2 != mkTag("WAVE"))	throw WavFormatException("expected WAVE header")
		
		var frameRateX		= None:Option[Int]
		var channelCountX	= None:Option[Int]
		var sampleBufferX	= None:Option[ShortBuffer]
		while (mapped.remaining != 0) {
			if (mapped.remaining < 8)	throw WavFormatException("cannot find data tag")
			val	tag	= mapped.getInt
			val siz	= mapped.getInt
			if (siz + 1 == 0)	throw WavFormatException("unexpected chunk size: " + siz)
			// chunks are aligned to even addresses
			val skp	= (siz + 1) & -2
			if (tag == mkTag("fmt ")) {
				if (siz < 16)				throw WavFormatException("unexpected fmt chunk size: " + siz)
				val	format			= mapped.getShort
				if (format != 1)			throw WavFormatException("unexpected audio format in fmt chunk, expected 1 for PCM: " + format)
				val channelCount	= mapped.getShort
				// if (channelCount != 2)			throw WavFormatException("unexpected channel count in fmt chunk, expected 2 for stereo: " + channelCount)
				val frameRate		= mapped.getInt
				// if (frameRate != 44100)			throw WavFormatException("unexpected sample rate in fmt chunk, expected 44100 for cd quality: " + frameRate)
				val byteRate		= mapped.getInt		// == SampleRate * NumChannels * BitsPerSample/8
				val byteAlign		= mapped.getShort	// == NumChannels * BitsPerSample/8
				val bitsPerSample	= mapped.getShort
				if (bitsPerSample != 16)	throw WavFormatException("unexpected wordsize in fmt chunk, expected 16 for cd quality: " + bitsPerSample)
				if (skp > 16) {
					// skip extension:
					// short size of extra params, doesn't exist with PCM
					// extra params
					mapped position (mapped.position + skp - 16)
				}
				channelCountX	= Some(channelCount)
				frameRateX		= Some(frameRate)
			}
			else if (tag == mkTag("data")) {
				if (siz != skp)		throw WavFormatException("unexpected data chunk size: " + siz) 
				val oldLimit	= mapped.limit
				mapped order	ByteOrder.LITTLE_ENDIAN
				val enough	= skp <= mapped.remaining
				if (!enough)	WARN("data tag too large")
				val (todo,skip)	=
						if (enough)	(siz,				skp)
						else		(mapped.remaining,	mapped.remaining)
				mapped limit	(mapped.position + todo)
				sampleBufferX	= Some(mapped.asShortBuffer)
				mapped limit	oldLimit
				mapped position	(mapped.position + skip)
			}
			else {
				// skip unexpected chunk
				mapped position (mapped.position + skp)
			}
		}
		
		val frameRateY		= frameRateX	getOrElse	{ throw WavFormatException("no frameRate found")	}
		val channelCountY	= channelCountX	getOrElse	{ throw WavFormatException("no channelCount found")	}
		val sampleBufferY	= sampleBufferX	getOrElse	{ throw WavFormatException("no sampleBuffer found")	}
		
		ShortBufferSample(frameRateY, channelCountY, sampleBufferY)
	}
}
