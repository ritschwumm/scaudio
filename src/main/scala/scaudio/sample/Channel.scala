package scaudio.sample

object Channel {
	object empty extends Channel {
		val frameCount:Int			= 0
		def get(frame:Int):Float	= 0
	}
}

trait Channel {
	def frameCount:Int
	
	/** returns 0f if 0 <= v < size doesn't hold */
	def get(frame:Int):Float
}
