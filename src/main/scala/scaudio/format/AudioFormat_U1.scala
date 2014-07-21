package scaudio.format

import scutil.math._

/** utility functions for 8 bit unsigned audio data */
object AudioFormat_U1 {
	private val offset	= Byte.MinValue
	private val factor	= Byte.MaxValue.toFloat
	
	@inline def decode(value:Byte):Float	= ((value & 0xff) - 128).toFloat / factor
	@inline def encode(value:Float):Byte	= (value * factor - offset).toByte
}