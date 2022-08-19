package scaudio.device.midiMix.protocol

object Strip {
	val all	= values.toVector
}

/** identify the 8 strips */
enum Strip {
	case _0, _1, _2, _3, _4, _5, _6, _7

	def index:Int	= this.ordinal
}
