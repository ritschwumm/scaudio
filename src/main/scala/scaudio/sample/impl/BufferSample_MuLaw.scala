package scaudio.sample.impl

import java.nio.ByteBuffer

import scaudio.sample._
import scaudio.format._

/** a Sample with 1 byte mu-law data points */
final class BufferSample_MuLaw(val frameRate:Int, val channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	val frameCount	= byteBuffer.limit / channelCount
	
	def get(frame:Int, channel:Int):Float	=
			AudioFormat_MuLaw decode (
					byteBuffer get (
							frame * channelCount + channel))
}
