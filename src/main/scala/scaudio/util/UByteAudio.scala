package scaudio.util

import scutil.math._

/** utility functions for 8 bit unsigned audio data */
object UByteAudio {
	private val offset	= Byte.MinValue
	private val factor	= Byte.MaxValue.toFloat
	
	def decode(value:Byte):Float	= ((value & 0xff) - 128).toFloat / factor
	def encode(value:Float):Byte	= (value * factor - offset).toByte
}
