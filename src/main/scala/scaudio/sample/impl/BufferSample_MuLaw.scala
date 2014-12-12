package scaudio.sample.impl

import java.nio.ByteBuffer

import scaudio.sample._
import scaudio.format._

/** a Sample with 1 byte mu-law data points interleaved per channel */
final class BufferSample_MuLaw(val frameRate:Int, channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	val frameCount	= byteBuffer.limit / channelCount
	
	val channels:Seq[Channel]	=
			(0 until channelCount).toArray map { channelIndex =>
				new BufferChannel_MuLaw(frameCount, channelCount, channelIndex, byteBuffer)
			}
}

final class BufferChannel_MuLaw(val frameCount:Int, channelCount:Int, channelIndex:Int, byteBuffer:ByteBuffer) extends Channel {
	def get(frame:Int):Float	=
			if (frame >= 0 && frame < frameCount) {
				AudioFormat_MuLaw decode (
					byteBuffer get index(frame)
				)
			}
			else 0f
			
	@inline
	def index(frame:Int):Int	=
			frame * channelCount + channelIndex
}
