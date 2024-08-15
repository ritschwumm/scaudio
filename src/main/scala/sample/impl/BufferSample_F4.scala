package scaudio.sample.impl

import java.nio.ByteOrder
import java.nio.ByteBuffer
import java.nio.FloatBuffer

import scaudio.sample.*

/** a Sample with 4 byte IEEE floating point data points interleaved per channel */
final class BufferSample_F4(val frameRate:Int, val channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	private val floatBuffer	= byteBuffer.duplicate.order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer

	val frameCount	= floatBuffer.limit() / channelCount
	val sampleBytes	= 4

	val channels:IArray[Channel]	=
		IArray.from(0.until(channelCount)).map { channelIndex =>
			new BufferChannel_F4(frameCount, channelCount, channelIndex, floatBuffer)
		}
}

final class BufferChannel_F4(val frameCount:Int, channelCount:Int, channelIndex:Int, floatBuffer:FloatBuffer) extends Channel {
	inline def get(frame:Int):Float	=
		if (frame >= 0 && frame < frameCount) {
			floatBuffer.get(index(frame))
		}
		else 0f

	inline def index(frame:Int):Int	=
		frame * channelCount + channelIndex
}
