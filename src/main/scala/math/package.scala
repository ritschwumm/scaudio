package scaudio

import java.lang.{ Double => JDouble, Float => JFloat }

import scala.math._

import scutil.math.functions._

/** math utilities */
package object math {
	// NOTE unboxed newtypes could be useful here

	val zeroGain		= 0.0
	val unitGain		= 1.0

	val unitDb			= 0.0

	val zeroFrequency	= 0.0
	val unitFrequency	= 1.0

	val secondsPerMinute	= 60

	//------------------------------------------------------------------------------

	/** convert from an amplitude multiplication factor to a dB value */
	def gain2db(gain:Double):Double	= 20 * log10(gain)
	// roughly Math.log(gain) * 6.0 / Math.log(2);

	/** convert from a dB value to an amplitude multiplication factor */
	def db2gain(dB:Double):Double	= exp10(dB / 20)
	// roughly Math.exp(dB * Math.log(2) / 6.0);

	//------------------------------------------------------------------------------

	/** convert from an amplitude multiplication factor to a dBi value */
	def gain2dbi(gain:Double):Double	= 10 * log10(gain)

	/** convert from a dBi value to an amplitude multiplication factor */
	def dbi2gain(dB:Double):Double		= exp10(dB / 10)

	//------------------------------------------------------------------------------

	/** convert from a frequency factor to a linear 1-per-octave value */
	@inline def frequency2octave(frequency:Double):Double	= log2(frequency)

	/** convert from a linear 1-per-octave value to a frequency factor */
	@inline def octave2frequency(octave:Double):Double		= exp2(octave)

	//------------------------------------------------------------------------------

	/** negative: first slow, then fast; positive: first fast, then slow */
	def gammaFade(form:Double):Double=>Double	= {
		val scale	= exp(form)
		it	=> exp(log(it) * scale)
	}

	def cosineFade(it:Double):Double	=
		(1 - cos(it * Pi)) / 2

	//------------------------------------------------------------------------------

	/** force denormalized numbers to zero */
	@deprecated("use scutil.bit.DoubleUtil.ftz", "0.201.0")
	def normalizeDouble(it:Double):Double	=
		if (it <= -JDouble.MIN_NORMAL || it >= JDouble.MIN_NORMAL)	it
		else														0.0

	/** force denormalized numbers to zero */
	@deprecated("use scutil.bit.FloatUtil.ftz", "0.201.0")
	def normalizeFloat(it:Float):Float	=
		if (it <= -JFloat.MIN_NORMAL || it >= JFloat.MIN_NORMAL)	it
		else														0.0f
}
