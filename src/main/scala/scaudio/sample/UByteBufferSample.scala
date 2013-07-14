package scaudio.sample

import java.nio.ByteBuffer

import scaudio.util._

/** a Sample with 1 byte unsigned data points */
private final class UByteBufferSample(val frameRate:Int, val channelCount:Int, byteBuffer:ByteBuffer) extends Sample {
	val frameCount	= byteBuffer.limit / channelCount
	
	def get(frame:Int, channel:Int):Float	=
			UByteAudio decode (
					byteBuffer get (
							frame * channelCount + channel))
}
