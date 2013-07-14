package scaudio.sample

object EmptySample extends Sample {
	val frameRate:Int						= 1
	val frameCount:Int						= 0
	val channelCount:Int					= 0
	def get(frame:Int, channel:Int):Float	= 0
}
