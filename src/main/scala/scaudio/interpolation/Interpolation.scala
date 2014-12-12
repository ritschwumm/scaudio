package scaudio.interpolation

import scaudio.sample.Channel

trait Interpolation {
	def overshot(pitch:Double):Int
	def interpolate(buffer:Channel, frame:Double, pitch:Double):Float
}
