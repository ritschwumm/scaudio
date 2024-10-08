package scaudio.interpolation

import java.lang.Math.*

import scaudio.sample.Channel

/** simple linear interpolation */
object Linear extends Interpolation {
	inline def overshot(pitch:Double):Int	= 1

	inline def interpolate(buffer:Channel, frame:Double, pitch:Double):Float = {
		val x1	= floor(frame)
		val xa	= x1.toInt
		val xb	= xa + 1
		val fa	= (frame - x1).toFloat
		val fb	= (1.0f - fa)
		val ya	= buffer.get(xa)
		val yb	= buffer.get(xb)
		ya*fa + yb*fb
	}
}
