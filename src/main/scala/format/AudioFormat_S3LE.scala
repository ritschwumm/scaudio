package scaudio.format

/** utility functions for 24 bit signed little endian audio data */
object AudioFormat_S3LE {
	private final val decodeFactor	= Math.scalb(1, -23)

	@inline def decode(b0:Byte, b1:Byte, b2:Byte):Float	=
		toInt(b0, b1, b2).toFloat * decodeFactor

	@inline def toInt(b0:Byte, b1:Byte, b2:Byte):Int	=
		// two bytes to keep sign extension alive
		((b2 & 0xffff)	<< 16)	|
		((b1 & 0xff)	<< 8)	|
		((b0 & 0xff) 	<< 0)
}
