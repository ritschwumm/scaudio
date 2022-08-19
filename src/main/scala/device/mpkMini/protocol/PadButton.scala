package scaudio.device.mpkMini.protocol

object PadButton {
	val all	= values.toVector
}

enum PadButton {
	case _0, _1, _2, _3, _4, _5, _6, _7

	def index:Int	= this.ordinal
}
