package scaudio.format

/** utility functions for 24 bit signed little endian audio data */
object AudioFormat_S3LE {
	private val factor	= 1 / (1 << 23).toFloat

	@inline def decode(b0:Byte, b1:Byte, b2:Byte):Float	= {
			factor *
			(	(	((b2 & 0xff) << 24)	|
					((b1 & 0xff) << 16)	|
					((b0 & 0xff) <<  8)
				)
				>> 8
			).toFloat
	}
}
