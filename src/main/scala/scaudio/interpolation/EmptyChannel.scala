package scaudio.interpolation

object EmptyChannel extends Channel {
	val size:Int				= 0
	def get(frame:Int):Float	= 0
}
