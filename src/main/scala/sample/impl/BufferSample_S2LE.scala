package scaudio.sample.impl

import java.nio.ByteOrder
import java.nio.ByteBuffer
import java.nio.ShortBuffer

import scala.collection.immutable.ArraySeq

import scaudio.sample._
import scaudio.format._

/** a Sample with 2 byte signed little endian data points interleaved per channel */
final class BufferSample_S2LE(val frameRate:Int, channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	private val shortBuffer	= byteBuffer.duplicate.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer

	val frameCount	= shortBuffer.limit() / channelCount
	val sampleBytes	= 2

	val channels:Seq[Channel]	=
		0 until channelCount to ArraySeq map { channelIndex =>
			new BufferChannel_S2LE(frameCount, channelCount, channelIndex, shortBuffer)
		}
}

final class BufferChannel_S2LE(val frameCount:Int, channelCount:Int, channelIndex:Int, shortBuffer:ShortBuffer) extends Channel {
	inline def get(frame:Int):Float	=
		if (frame >= 0 && frame < frameCount) {
			AudioFormat_S2LE decode (
				shortBuffer get index(frame)
			)
		}
		else 0f

	inline def index(frame:Int):Int	=
		frame * channelCount + channelIndex
}
