package scaudio.format

/** utility functions for 8 bit unsigned audio data */
object AudioFormat_U1 {
	private final val decodeFactor	= Math.scalb(1, -7)
	private final val encodeFactor	= Math.scalb(1, +7)

	private final val offset	= Byte.MinValue

	inline def decode(value:Byte):Float	= ((value & 0xff) + offset).toFloat * decodeFactor
	inline def encode(value:Float):Byte	= (clamp(value * encodeFactor) - offset).toByte

	inline def clamp(value:Float):Float	=
		if		(value < Byte.MinValue)	Byte.MinValue
		else if	(value > Byte.MaxValue)	Byte.MaxValue
		else							value
}
