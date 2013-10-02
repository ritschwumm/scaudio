package scaudio.sample.impl

import java.nio.ByteOrder
import java.nio.ByteBuffer
import java.nio.ShortBuffer

import scaudio.sample._
import scaudio.format._

/** a Sample with 4 byte IEEE floating point data points */
final class BufferSample_F4(val frameRate:Int, val channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	private val floatBuffer	= byteBuffer.duplicate.order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer
	
	val frameCount	= floatBuffer.limit / channelCount
	
	def get(frame:Int, channel:Int):Float	=
				floatBuffer get (frame * channelCount + channel)
}
