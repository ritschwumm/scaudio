package scaudio.sample

import java.io._
import java.nio.ByteOrder
import java.nio.ByteBuffer
import java.nio.channels.FileChannel 

import scutil.lang._
import scutil.Implicits._
import scutil.log._

import scaudio.sample.impl._

/** audio sample loaded from a wav file using a MappedByteBuffer */
object Wav extends Logging {
	def load(file:File):Tried[Exception,Sample] =
			Tried catchException loadImpl(file)
	
	/** may throw exception if not successful */
	private def loadImpl(file:File):Sample = {
		// little endian 4 characters
		def mkTag(s:String):Long	= {
			val bytes	= s getBytes "US-ASCII"
			require(bytes.length == 4, "expected exactly 4 characters")
			((bytes(3) & 0xff) << 24) |
			((bytes(2) & 0xff) << 16) |
			((bytes(1) & 0xff) <<  8) |
			((bytes(0) & 0xff) <<  0)
		}
	
		val	mapped:ByteBuffer	=
				new RandomAccessFile(file, "r").getChannel use { fc => 
					fc map (FileChannel.MapMode.READ_ONLY, 0, file.length)
				}
		// mapped.load()
		mapped order ByteOrder.LITTLE_ENDIAN
		/*
		// madplay in raw mode outputs native order
		mapped order ByteOrder.nativeOrder
		mapped.asShortBuffer
		*/
		
		/*
		// RIFX instead of RIFF for big endian data
		// 8 bit is unsigned, 16 bit is signed
		// left channel comes before right channel
		*/
		
		if (mapped.remaining < 12)	throw WavFormatException("cannot find RIFF header")
		val	tag1	= mapped.getInt
		if (tag1 != mkTag("RIFF"))	throw WavFormatException("expected RIFF header")
		val	flen	= mapped.getInt
		val	tag2	= mapped.getInt
		if (tag2 != mkTag("WAVE"))	throw WavFormatException("expected WAVE header")
		
		var decoder:Option[ByteBuffer=>Sample]	= None
		var sampleData:Option[ByteBuffer]		= None
		
		while (mapped.remaining != 0 && (decoder.isEmpty || sampleData.isEmpty)) {
			if (mapped.remaining < 8)	throw WavFormatException("cannot find data tag")
			val	tag	= mapped.getInt
			val siz	= mapped.getInt
			if (siz + 1 == 0)	throw WavFormatException(s"unexpected chunk size: ${siz}")
			// chunks are aligned to even addresses
			val skp	= (siz + 1) & -2
			if (tag == mkTag("fmt ")) {
				if (siz < 16)				throw WavFormatException(s"unexpected fmt chunk size:  ${siz}")
				/*
				0x0001 	WAVE_FORMAT_PCM			PCM
				0x0003 	WAVE_FORMAT_IEEE_FLOAT	IEEE float
				0x0006 	WAVE_FORMAT_ALAW		8-bit ITU-T G.711 A-law
				0x0007 	WAVE_FORMAT_MULAW		8-bit ITU-T G.711 µ-law
				0xFFFE 	WAVE_FORMAT_EXTENSIBLE 	Determined by SubFormat
				*/
				/*
				WAVE_FORMAT_IEEE_FLOAT 
				-	can have 24 bit sample size with 4 bytes container size (32 bits)
				-	container size derived from block size and channel count
				-	data is actually 32 bit IEEE float
				-	normalization is full scale 2^23
				*/
				val	format			= mapped.getShort
				val channelCount	= mapped.getShort
				val frameRate		= mapped.getInt
				val byteRate		= mapped.getInt		// == SampleRate * NumChannels * BitsPerSample / 8
				val byteAlign		= mapped.getShort	// == NumChannels * BitsPerSample / 8
				val bitsPerSample	= mapped.getShort
				
				// if (channelCount != 2)	throw WavFormatException("unexpected channel count in fmt chunk, expected 2 for stereo: " + channelCount)
				// if (frameRate != 44100)	throw WavFormatException("unexpected sample rate in fmt chunk, expected 44100 for cd quality: " + frameRate)
				
				decoder	= (format, bitsPerSample) matchOption {
					case (1,  8)	=> new BufferSample_U1(frameRate, channelCount, _)
					case (1, 16)	=> new BufferSample_S2LE(frameRate, channelCount, _)
					case (1, 24)	=> new BufferSample_S3LE(frameRate, channelCount, _)
					case (1,  x)	=> throw WavFormatException(s"unexpected wordsize in fmt chunk ${format} (PCM), expected 8, 16 or 24: ${x}")
					case (3, 32)	=> new BufferSample_F4(frameRate, channelCount, _)
					// NOTE (3, 64) is allowed, too
					case (3,  x)	=> throw WavFormatException(s"unexpected wordsize in fmt chunk ${format} (IEEE), expected 32: ${x}")
					case (6,  8)	=> new BufferSample_ALaw(frameRate, channelCount, _)
					case (6,  x)	=> throw WavFormatException(s"unexpected wordsize in fmt chunk ${format} (A-Law), expected 8: ${x}")
					case (7,  8)	=> new BufferSample_MuLaw(frameRate, channelCount, _)
					case (7,  x)	=> throw WavFormatException(s"unexpected wordsize in fmt chunk ${format} (Mu-Law), expected 8: ${x}")
					case (f,  _)	=> throw WavFormatException(s"unexpected audio format in fmt chunk, expected 1 (PCM), 3 (IEEE), 6 (A-Law) or 7 (Mu-Law): ${f}")
				}

				if (skp > 16) {
					// skip extension:
					// short size of extra params, doesn't exist with PCM
					// extra params
					mapped position (mapped.position + skp - 16)
				}
			}
			else if (tag == mkTag("data")) {
				val oldLimit	= mapped.limit
				val enough		= skp <= mapped.remaining
				if (!enough)	WARN("data tag too large")
				val (todo, skip)	=
						if (enough)	(siz,				skp)
						else		(mapped.remaining,	mapped.remaining)
				mapped limit	(mapped.position + todo)
				sampleData		= Some(mapped.slice.asReadOnlyBuffer)
				mapped limit	oldLimit
				mapped position	(mapped.position + skip)
			}
			else {
				// skip unexpected chunk
				mapped position (mapped.position + skp)
			}
		}
		
		val decoderX	= decoder		getOrElse { throw WavFormatException("missing format data")	}
		val sampleDataX	= sampleData	getOrElse { throw WavFormatException("missing sample data") }
		
		decoderX(sampleDataX)
	}
}
