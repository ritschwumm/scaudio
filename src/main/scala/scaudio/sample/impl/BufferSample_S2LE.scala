package scaudio.sample.impl

import java.nio.ByteOrder
import java.nio.ByteBuffer
import java.nio.ShortBuffer

import scaudio.sample._
import scaudio.format._

/** a Sample with 2 byte signed little endian data points */
final class BufferSample_S2LE(val frameRate:Int, val channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	private val shortBuffer	= byteBuffer.duplicate.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer
	
	val frameCount	= shortBuffer.limit / channelCount
	
	def get(frame:Int, channel:Int):Float	=
			AudioFormat_S2LE decode (
					shortBuffer get (
							frame * channelCount + channel))
}
