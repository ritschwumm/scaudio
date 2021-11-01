package scaudio.format

/** utility functions for cd quality 16 bit signed audio data */
object AudioFormat_S2LE {
	private final val decodeFactor	= Math.scalb(1, -15)
	private final val encodeFactor	= Math.scalb(1, +15)

	@inline def decode(value:Short):Float	= value.toFloat * decodeFactor
	@inline def encode(value:Float):Short	= clamp(value * encodeFactor).toShort

	//------------------------------------------------------------------------------

	// next offset is offset+2
	def putClamped(value:Float, buffer:Array[Byte], offset:Int):Unit	= {
		putShort(encode(value), buffer, offset)
	}

	@inline def clamp(value:Float):Float	=
		if		(value < Short.MinValue)	Short.MinValue
		else if	(value > Short.MaxValue)	Short.MaxValue
		else								value

	def putShort(value:Short, buffer:Array[Byte], offset:Int):Unit	= {
		buffer(offset+0)	= (value >> 0).toByte
		buffer(offset+1)	= (value >> 8).toByte
	}
}
