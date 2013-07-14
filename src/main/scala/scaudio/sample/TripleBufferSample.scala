package scaudio.sample

import java.nio.ByteBuffer
import java.nio.ShortBuffer

import scaudio.util._

/** a Sample with 3 byte signed little endian data points */
private final class TripleBufferSample(val frameRate:Int, val channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	val frameCount	= byteBuffer.limit / channelCount / 3
	
	def get(frame:Int, channel:Int):Float	= {
		val offset	= (frame * channelCount + channel) * 3
		val a		= byteBuffer get offset+0
		val b		= byteBuffer get offset+1
		val c		= byteBuffer get offset+2
		val i		=
				((c & 0xff) << 24)	|
				((b & 0xff) << 16)	|
				((a & 0xff) <<  8)
		(i >> 8).toFloat / (1 << 23).toFloat
	}
}
