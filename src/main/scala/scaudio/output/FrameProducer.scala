package scaudio.output

trait FrameProducer {
	def produce(speaker:FrameBuffer, headphone:FrameBuffer):Unit
}
