package scaudio.sample

import java.nio.ByteOrder
import java.nio.ByteBuffer
import java.nio.ShortBuffer

import scaudio.util._

/** a Sample with 2 byte signed little endian data points */
private final class ShortBufferSample(val frameRate:Int, val channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	private val shortBuffer	= byteBuffer.duplicate.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer
	
	val frameCount	= shortBuffer.limit / channelCount
	
	def get(frame:Int, channel:Int):Float	=
			ShortAudio decode (
					shortBuffer get (
							frame * channelCount + channel))
}
