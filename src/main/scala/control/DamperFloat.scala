package scaudio.control

import scala.math.*

object DamperFloat {
	/**
	stepRate is how ofter step is called per second,
	unitTime how many seconds a change of unit size should take
	*/
	def forRates(initial:Float, unitTime:Float, stepRate:Float):DamperFloat	=
		new DamperFloat(initial, 1f / (stepRate * unitTime))
}

/** a simple, linear animator to be used for parameters */
final class DamperFloat(initial:Float, absMaxDelta:Float) {
	require(absMaxDelta > 0, "absMaxDelta must be positive and non-zero")

	private var cur:Float	= initial
	private var trg:Float	= initial

	/** get the current value */
	inline def current:Float	= cur

	/** set the target value */
	def target(trg:Float):Unit	= {
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
