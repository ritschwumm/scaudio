package scaudio.output

object FrameProducer {
	val blackhole:FrameProducer	=
		(_:FrameBuffer, _:FrameBuffer) => ()
}

trait FrameProducer {
	/**
	* this may be called in an arbitrary Thread created by the Output.
	* buffers are cleared before this method is called.
	*/
	def produce(speaker:FrameBuffer, headphone:FrameBuffer):Unit
}
