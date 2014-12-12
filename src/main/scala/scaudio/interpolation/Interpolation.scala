package scaudio.interpolation

import scaudio.sample.Channel

trait Interpolation {
	def interpolate(buffer:Channel, frame:Double, pitch:Double):Float
}
