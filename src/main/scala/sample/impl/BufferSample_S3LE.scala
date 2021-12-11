package scaudio.sample.impl

import java.nio.ByteBuffer

import scaudio.sample.*
import scaudio.format.*

/** a Sample with 3 byte signed little endian data points interleaved per channel */
final class BufferSample_S3LE(val frameRate:Int, channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	val frameCount	= byteBuffer.limit() / channelCount / 3
	val sampleBytes	= 3

	val channels:IArray[Channel]	=
		IArray.from(0 until channelCount) map { channelIndex =>
			new BufferChannel_S3LE(frameCount, channelCount, channelIndex, byteBuffer)
		}
}

final class BufferChannel_S3LE(val frameCount:Int, channelCount:Int, channelIndex:Int, byteBuffer:ByteBuffer) extends Channel {
	inline def get(frame:Int):Float	=
		if (frame >= 0 && frame < frameCount) {
			val offset	= index(frame) * 3
			val b0		= byteBuffer get offset+0
			val b1		= byteBuffer get offset+1
			val b2		= byteBuffer get offset+2
			AudioFormat_S3LE.decode(b0, b1, b2)
		}
		else 0f

	inline def index(frame:Int):Int	=
		frame * channelCount + channelIndex
}
