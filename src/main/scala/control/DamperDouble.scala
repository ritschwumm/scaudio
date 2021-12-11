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
	inline def current:Double	= cur

	/** set the target value */
	def target(trg:Double):Unit	= {
		this.trg	= trg
	}

	/** immediately set the current value to the target value */
	def jump():Unit	= {
		cur	= trg
	}

	/** nudge the current value towards the target value */
	inline def step():Unit	= {
		val delta	= trg - cur
		cur	+= min(abs(delta), absMaxDelta) * signum(delta)
	}
}
