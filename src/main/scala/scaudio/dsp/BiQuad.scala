package scaudio.dsp

import java.lang.{ Double => JDouble }

final class BiQuad {
	private var z1	= 0.0
	private var z2	= 0.0
	
	// transposed direct form 2
	def process(in:Double, cs:BiQuadCoeffs):Double	= {
		val out	= normalize(in * cs.a0 + z1)
		z1		= in * cs.a1 + z2 + out * -cs.b1
		z2		= in * cs.a2      + out * -cs.b2
		out
	}
	
	def reset() {
		z1	= 0.0
		z2	= 0.0
	}
	
	// force denormalized numbers to zero
	private def normalize(it:Double):Double	=
		if (it <= -JDouble.MIN_NORMAL || it >= JDouble.MIN_NORMAL)	it
		else														0.0
}
