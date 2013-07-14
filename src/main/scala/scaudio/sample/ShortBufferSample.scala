package scaudio.sample

import java.nio.ShortBuffer

import scaudio.util._

// NOTE treats the ShortBuffer as immutable

/** a sample getting its data from a ShortBuffer */
private final case class ShortBufferSample(val frameRate:Int, val channelCount:Int, sampleBuffer:ShortBuffer) extends Sample {
	val frameCount	= sampleBuffer.limit / channelCount
	
	def get(frame:Int, channel:Int):Float	=
			ShortAudio audioShort2Float (
					sampleBuffer get (
							frame * channelCount + channel))
}
