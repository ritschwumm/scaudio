package scaudio.sample.impl

import java.nio.ByteBuffer

import scala.collection.immutable.ArraySeq

import scaudio.sample._
import scaudio.format._

/** a Sample with 1 byte unsigned data points interleaved per channel */
final class BufferSample_U1(val frameRate:Int, channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	val frameCount	= byteBuffer.limit() / channelCount
	val sampleBytes	= 1

	val channels:Seq[Channel]	=
			0 until channelCount to ArraySeq map { channelIndex =>
				new BufferChannel_U1(frameCount, channelCount, channelIndex, byteBuffer)
			}
}

final class BufferChannel_U1(val frameCount:Int, channelCount:Int, channelIndex:Int, byteBuffer:ByteBuffer) extends Channel {
	def get(frame:Int):Float	=
			if (frame >= 0 && frame < frameCount) {
				AudioFormat_U1 decode (
					byteBuffer get index(frame)
				)
			}
			else 0f

	@inline
	def index(frame:Int):Int	=
			frame * channelCount + channelIndex
}
