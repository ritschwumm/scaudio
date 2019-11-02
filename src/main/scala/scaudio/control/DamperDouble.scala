package scaudio.control

import scala.math._

object DamperDouble {
	/**
	stepRate is how ofter step is called per second,
	unitTime how many seconds a change of unit size should take
	*/
	def forRates(initial:Double, unitTime:Double, stepRate:Double):DamperDouble	=
			new DamperDouble(initial, 1d / (stepRate * unitTime))
}

/** a simple, linear animator to be used for parameters */
final class DamperDouble(initial:Double, absMaxDelta:Double) {
	require(absMaxDelta > 0, "absMaxDelta must be positive and non-zero")

	private var cur:Double	= initial
	private var trg:Double	= initial

	/** get the current value */
	def current:Double	= cur

	/** set the target value */
	def target(trg:Double) {
		this.trg	= trg
	}

	/** immediately set the current value to the target value */
	def jump() {
		cur	= trg
	}

	/** nudge the current value towards the target value */
	def step() {
		val delta	= trg - cur
		cur	+= min(abs(delta), absMaxDelta) * signum(delta)
	}
}
