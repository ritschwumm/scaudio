package scaudio.sample.impl

import java.nio.ByteBuffer

import scala.collection.immutable.ArraySeq

import scaudio.sample._
import scaudio.format._

/** a Sample with 3 byte signed little endian data points interleaved per channel */
final class BufferSample_S3LE(val frameRate:Int, channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	val frameCount	= byteBuffer.limit() / channelCount / 3
	val sampleBytes	= 3

	val channels:Seq[Channel]	=
		0 until channelCount to ArraySeq map { channelIndex =>
			new BufferChannel_S3LE(frameCount, channelCount, channelIndex, byteBuffer)
		}
}

final class BufferChannel_S3LE(val frameCount:Int, channelCount:Int, channelIndex:Int, byteBuffer:ByteBuffer) extends Channel {
	def get(frame:Int):Float	=
		if (frame >= 0 && frame < frameCount) {
			val offset	= index(frame) * 3
			val b0		= byteBuffer get offset+0
			val b1		= byteBuffer get offset+1
			val b2		= byteBuffer get offset+2
			AudioFormat_S3LE.decode(b0, b1, b2)
		}
		else 0f

	@inline
	def index(frame:Int):Int	=
		frame * channelCount + channelIndex
}
