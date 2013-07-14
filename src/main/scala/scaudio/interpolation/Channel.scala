package scaudio.interpolation

trait Channel {
	/** number of frames available */
	val size:Int
	
	/** returns 0f if 0 <= v < size doesn't hold */
	def get(frame:Int):Float
}
