package scaudio.util

import scutil.Math._

/** utility functions for cd quality 16 bit signed audio data */
object ShortAudio {
	val scaleFactor	= 32768f
	
	def audioShort2Float(value:Short):Float	= value.toFloat / scaleFactor
	def audioFloat2Short(value:Float):Short	= (value * scaleFactor).toShort
	
	def audioClampFloat2Short(value:Float):Short	=
			clamp(value * scaleFactor, -scaleFactor, +(scaleFactor-1)).toShort
}
