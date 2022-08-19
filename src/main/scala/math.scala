package scaudio

import scala.math.*

import scutil.math.functions.*

/** math utilities */
object math {
	// NOTE unboxed newtypes could be useful here

	val zeroGain		= 0.0
	val unitGain		= 1.0

	val unitDb			= 0.0

	val zeroFrequency	= 0.0
	val unitFrequency	= 1.0

	val secondsPerMinute	= 60

	//------------------------------------------------------------------------------

	/** convert from an amplitude multiplication factor to a dB value */
	inline def gain2db(gain:Double):Double	= 20 * log10(gain)
	// roughly Math.log(gain) * 6.0 / Math.log(2);

	/** convert from a dB value to an amplitude multiplication factor */
	inline def db2gain(dB:Double):Double	= exp10(dB / 20)
	// roughly Math.exp(dB * Math.log(2) / 6.0);

	//------------------------------------------------------------------------------

	/** convert from an amplitude multiplication factor to a dBi value */
	inline def gain2dbi(gain:Double):Double	= 10 * log10(gain)

	/** convert from a dBi value to an amplitude multiplication factor */
	inline def dbi2gain(dB:Double):Double		= exp10(dB / 10)

	//------------------------------------------------------------------------------

	/** convert from a frequency factor to a linear 1-per-octave value */
	inline def frequency2octave(frequency:Double):Double	= log2(frequency)

	/** convert from a linear 1-per-octave value to a frequency factor */
	inline def octave2frequency(octave:Double):Double		= exp2(octave)

	//------------------------------------------------------------------------------

	/** negative: first slow, then fast; positive: first fast, then slow */
	def gammaFade(form:Double):Double=>Double	=
		it	=> exp(log(it) * exp(form))

	def cosineFade(it:Double):Double	=
		(1 - cos(it * Pi)) / 2
}
