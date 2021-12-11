package scaudio.dsp

import scala.math._

object Equalizer {
	/** denormal fix */
	val vsa = 1.0 / 4294967295.0
}

/**
 * @see http://www.musicdsp.org/showone.php?id=236
 *
 * Recommended frequencies are ...
 * lowfreq	= 880  Hz
 * highfreq = 5000 Hz
 *
 * Set mixfreq to whatever rate your system is using (eg 48Khz)
 */
final class Equalizer(lowfreq:Double, highfreq:Double, mixfreq:Double) {
	// filter #1 (lowpass)
	private val lf:Double	= 2 * sin(Pi * lowfreq / mixfreq)
	private var f1p0:Double = 0.0		// poles
	private var f1p1:Double = 0.0
	private var f1p2:Double = 0.0
	private var f1p3:Double = 0.0

	// filter #2 (highpass)
	private val hf:Double	= 2 * sin(Pi * highfreq / mixfreq)
	private var f2p0:Double = 0.0		// poles
	private var f2p1:Double = 0.0
	private var f2p2:Double = 0.0
	private var f2p3:Double = 0.0

	// history buffer
	private var sdm1:Double = 0.0		// t-1
	private var sdm2:Double = 0.0		// t-2
	private var sdm3:Double = 0.0		// t-3

	// output
	var l:Double	= 0.0
	var m:Double	= 0.0
	var h:Double	= 0.0

	inline def process(in:Double, lowGain:Double, midGain:Double, highGain:Double):Double = {
		step(in)
		l * lowGain + m * midGain + h * highGain
	}

	/**  EQ one sample */
	inline def step(in:Double):Unit	= {
		// filter #1 (lowpass)
		f1p0  += (lf * (in - f1p0)) + Equalizer.vsa
		f1p1  += (lf * (f1p0 - f1p1))
		f1p2  += (lf * (f1p1 - f1p2))
		f1p3  += (lf * (f1p2 - f1p3))

		// low output
		l	= f1p3

		// filter #2 (highpass)
		f2p0  += (hf * (in - f2p0)) + Equalizer.vsa
		f2p1  += (hf * (f2p0 - f2p1))
		f2p2  += (hf * (f2p1 - f2p2))
		f2p3  += (hf * (f2p2 - f2p3))

		// high output
		h	= sdm3 - f2p3

		// mid calculation and output
		m	= sdm3 - (h + l)	// original code
		// m	= sample - (h + l)	// comment
		// m	= f2p3 - f1p3		// optimized comment

		// shuffle history buffer
		sdm3   = sdm2
		sdm2   = sdm1
		sdm1   = in
	}

	inline def reset():Unit	= {
		// lf	= 0.0
		f1p0	= 0.0
		f1p1	= 0.0
		f1p2	= 0.0
		f1p3	= 0.0
		// hf	= 0.0
		f2p0	= 0.0
		f2p1	= 0.0
		f2p2	= 0.0
		f2p3	= 0.0
		sdm1	= 0.0
		sdm2	= 0.0
		sdm3	= 0.0
		l		= 0.0
		m		= 0.0
		h		= 0.0
	}
}
