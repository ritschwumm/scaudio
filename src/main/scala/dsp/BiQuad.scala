package scaudio.dsp

import scaudio.math

final class BiQuad {
	private var z1	= 0.0
	private var z2	= 0.0

	// transposed direct form 2
	def process(in:Double, cs:BiQuadCoeffs):Double	= {
		val out	= math normalizeDouble (in * cs.a0 + z1)
		z1		= in * cs.a1 + out * -cs.b1 + z2
		z2		= in * cs.a2 + out * -cs.b2
		out
	}

	def reset():Unit	= {
		z1	= 0.0
		z2	= 0.0
	}
}
