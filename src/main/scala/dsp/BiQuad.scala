package scaudio.dsp

import scutil.bit.DoubleUtil.ftz

final class BiQuad {
	private var z1	= 0.0
	private var z2	= 0.0

	// transposed direct form 2
	def process(in:Double, cs:BiQuadCoeffs):Double	= {
		val out	= ftz(in * cs.a0) + z1
		z1		= ftz(in * cs.a1) + ftz(out * -cs.b1) + z2
		z2		= ftz(in * cs.a2) + ftz(out * -cs.b2)
		out
	}

	def reset():Unit	= {
		z1	= 0.0
		z2	= 0.0
	}
}
