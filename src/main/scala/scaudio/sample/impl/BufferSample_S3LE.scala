package scaudio.sample.impl

import java.nio.ByteBuffer
import java.nio.ShortBuffer

import scaudio.sample._
import scaudio.format._

/** a Sample with 3 byte signed little endian data points */
final class BufferSample_S3LE(val frameRate:Int, val channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	val frameCount	= byteBuffer.limit / channelCount / 3
	
	def get(frame:Int, channel:Int):Float	= {
		val offset	= (frame * channelCount + channel) * 3
		val b0		= byteBuffer get offset+0
		val b1		= byteBuffer get offset+1
		val b2		= byteBuffer get offset+2
		AudioFormat_S3LE decode (b0, b1, b2)
	}
}
