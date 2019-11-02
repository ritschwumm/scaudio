package scaudio.dsp

import scala.math._
import scutil.math.functions._

/**
all freq parameters are pre-divided by the sample rate
bandwidth = freq / Q
*/
object BiQuadCoeffs {
	val sqrt2:Double		= sqrt(2)

	// standard Q
	val sqrt2half:Double	= sqrt2 / 2

	// 0	=> 1
	// 6	=> sqrt(2)
	// shelfA = db2gain(x/2)
	private def shelfA(dbGain:Double):Double	=
			pow(10, dbGain * 0.025)

	//------------------------------------------------------------------------------

	// LPF
	def lp(freq:Double, q:Double):BiQuadCoeffs	= {
	 	val w		= PiDouble * freq
		val sinw	= sin(w)
	 	val cosw	= cos(w)
		val a		= sinw / q * 0.5

		val b1	= 1 - cosw		// 1 - cosw
		val b0	= b1 * 0.5		// (1 - cosw) / 2
		val b2	= b0			// (1 - cosw) / 2
		val a0	= 1 + a
		val a1	= -2 * cosw
		val a2	= 1 - a

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	// HPF
	def hp(freq:Double, q:Double):BiQuadCoeffs	= {
	 	val w		= PiDouble * freq
		val sinw	= sin(w)
	 	val cosw	= cos(w)
		val a		= sinw / q * 0.5

		val b1	= -1 - cosw		// -(1 + cosw)
		val b0	= b1 * -0.5		// (1 + cosw) / 2
		val b2	= b0			// (1 + cosw) / 2
		val a0	= 1 + a
		val a1	= -2 * cosw
		val a2	= 1 - a

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	// BPF	constant skirt gain, peak gain = Q
	def bpSkirt(freq:Double, q:Double):BiQuadCoeffs	= {
	 	val w		= PiDouble * freq
		val sinw	= sin(w)
	 	val cosw	= cos(w)
		val a		= sinw / q * 0.5

		val b0	= sinw * 0.5	// sinw / 2
		val b1	= 0
		val b2	= -b0			// -sinw / 2
		val a0	= 1 + a
		val a1	= -2 * cosw
		val a2	= 1 - a

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	// BPF	(constant 0 dB peak gain)
	def bpPeak(freq:Double, q:Double):BiQuadCoeffs	= {
	 	val w		= PiDouble * freq
		val sinw	= sin(w)
	 	val cosw	= cos(w)
	 	val a		= sinw / q * 0.5

		val b0	= a
		val b1	= 0
		val b2	= -a
		val a0	= 1 + a
		val a1	= -2 * cosw
		val a2	= 1 - a

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	// notch
	def notch(freq:Double, q:Double):BiQuadCoeffs	= {
	 	val w		= PiDouble * freq
		val sinw	= sin(w)
	 	val cosw	= cos(w)
		val a		= sinw / q * 0.5

		val b0	= 1
		val b1	= -2 * cosw
		val b2	= 1
		val a0	= 1 + a
		val a1	= b1
		val a2	= 1 - a

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	// APF (allpass)
	def ap(freq:Double, q:Double):BiQuadCoeffs	= {
	 	val w		= PiDouble * freq
		val sinw	= sin(w)
	 	val cosw	= cos(w)
		val a		= sinw / q * 0.5

		val a2	= 1 - a			// 1 - a
		val a1	= -2 * cosw		// -2 * cosw
		val a0	= 1 + a			// 1 + a
		val b0	= a2			// 1 - a
		val b1	= a1			// -2 * cosw
		val b2	= a0			// 1 + a

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	// peakingEQ
	def pk(freq:Double, q:Double, gain:Double):BiQuadCoeffs	= {
		val A		= shelfA(gain)
	 	val w		= PiDouble * freq
		val sinw	= sin(w)
	 	val cosw	= cos(w)
		val a		= sinw / q * 0.5

		val b0	= 1 + a * A		// 1 + a * A
		val b2	= 2 - b0		// 1 - a * A
		val b1	= -2 * cosw		// -2 * cosw
		val a0	= 1 + a / A		// 1 + a / A
		val a1	= b1			// -2 * cosw
		val a2	= 2 - a0		// 1 - a / A

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	// lowShelf
	def ls(freq:Double, q:Double, gain:Double):BiQuadCoeffs	= {
		val A		= shelfA(gain)
	 	val w		= PiDouble * freq
		val sinw	= sin(w)
	 	val cosw	= cos(w)
		val a		= sinw / q * 0.5
		val b		= 2 * a * sqrt(A)
		val c		= (A - 1) * cosw

		val b0		= A * (A + 1 - c + b)
		val b1		= 2 * A * ((A - 1) - (A + 1) * cosw)
		val b2		= A * (A + 1 - c - b)
		val a0		= A + 1 + c + b
		val a1		= -2 * ((A - 1) + (A + 1) * cosw)
		val a2		= A + 1 + c - b

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	// highShelf
	def hs(freq:Double, q:Double, gain:Double):BiQuadCoeffs	= {
		val A		= shelfA(gain)
	 	val w		= PiDouble * freq
		val sinw	= sin(w)
	 	val cosw	= cos(w)
		val a		= sinw / q * 0.5
		val b		= 2 * a * sqrt(A)
		val c		= (A - 1) * cosw

		val b0		= A * (A + 1 + c + b)
		val b1		= -2 * A * (A - 1 + (A + 1) * cosw)
		val b2		= A * (A + 1 + c - b)
		val a0		= A + 1 - c + b
		val a1		= 2 * (A - 1 - (A + 1) * cosw)
		val a2		= A + 1 - c - b

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	//------------------------------------------------------------------------------

	def lpButterworth(freq:Double):BiQuadCoeffs	= {
		val k	= tan(Pi * freq)

		val b0	= k * k
		val b2	= b0
		val b1	= 2 * b0
		val a0	= b0 + sqrt2 * k + 1
		val a1	= 2 * (b0 - 1)
		val a2	= b0 - sqrt2 * k + 1

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	def hpButterworth(freq:Double):BiQuadCoeffs	= {
		val k		= tan(Pi * freq)
		val k2p1	= k * k + 1

		val b0	= 1
		val b1	= -2
		val b2	= 1
		val a0	= k2p1 + (sqrt2 * k)
		val a1	= 2f * (k2p1 - 2)
		val a2	= k2p1 - (sqrt2 * k)

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	def lpBessel(freq:Double):BiQuadCoeffs	= {
		val w	= tan(Pi * freq)

		val b0	= 3 * w * w
		val b2	= b0
		val b1	= 2 * b0
		val a0	= 1 + 3 * w + b0
		val a1	= -2 + b1
		val a2	= 1 - 3 * w + b0

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	def hpBessel(freq:Double):BiQuadCoeffs	= {
		val w	= tan(Pi * freq)
		val w2	= w * w

		val b0	= 3
		val b2	= b0
		val b1	= -6
		val a0	= w2 + 3 * w + 3
		val a1	= 2 * w2 - 6
		val a2	= w2 - 3 * w + 3

		mkCoeffs(a0, a1, a2, b0, b1, b2)
	}

	//------------------------------------------------------------------------------

	// NOTE this swaps a and b
	def mkCoeffs(a0:Double, a1:Double, a2:Double, b0:Double, b1:Double, b2:Double)	=
			BiQuadCoeffs(
				a0	= b0 / a0,
				a1	= b1 / a0,
				a2	= b2 / a0,
				b1	= a1 / a0,
				b2	= a2 / a0
			)
}

final case class BiQuadCoeffs(
	a0:Double,
	a1:Double,
	a2:Double,
	b1:Double,
	b2:Double
)
