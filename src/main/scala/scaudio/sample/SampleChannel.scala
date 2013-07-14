package scaudio.sample

import scaudio.interpolation.Channel

/** reads a single channel from a Sample */
final class SampleChannel(sample:Sample, index:Int) extends Channel {
	/** number of frames available */
	val size:Int	= sample.frameCount
	
	/** returns 0f if 0 <= v < size doesn't hold */
	def get(frame:Int):Float = 
			if (frame >= 0 && frame < size)	sample get (frame, index) 
			else 							0
}
