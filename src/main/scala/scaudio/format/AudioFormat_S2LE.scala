package scaudio.format

import scutil.math._

/** utility functions for cd quality 16 bit signed audio data */
object AudioFormat_S2LE {
	private val factor	= Short.MaxValue.toFloat
	
	@inline def decode(value:Short):Float	= value.toFloat / factor
	@inline def encode(value:Float):Short	= (value * factor).toShort
	
	//------------------------------------------------------------------------------
	
	// next offset is offset+2
	def putClamped(value:Float, buffer:Array[Byte], offset:Int) {
		putShort(clamp(value * factor).toShort, buffer, offset)
	}
	
	def clamp(value:Float):Float	=
				 if (value < Short.MinValue)	Short.MinValue
			else if (value > Short.MaxValue)	Short.MaxValue
			else								value
	
	def putShort(value:Short, buffer:Array[Byte], offset:Int) {
		buffer(offset+0)	= (value >> 0).toByte
		buffer(offset+1)	= (value >> 8).toByte
	}
}
