package scaudio.interpolation

trait Interpolation {
	def interpolate(buffer:Channel, frame:Double, pitch:Double):Float
}
