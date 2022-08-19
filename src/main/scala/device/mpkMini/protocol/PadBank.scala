package scaudio.device.mpkMini.protocol

import scaudio.midi.*

import scaudio.device.MidiKeyRange

object PadBank {
	val all	= values.toVector
}

enum PadBank {
	case _0, _1

	lazy val pads	= PadButton.all.map(button => Pad(this, button))

	def midiKeys:MidiKeyRange	=
		this match {
			// TODO scala why to i need the "PadBank" prefix here?
			case PadBank._0	=> MidiKeyRange(MidiKey(44), 8)
			case PadBank._1	=> MidiKeyRange(MidiKey(32), 8)
		}

	def index:Int	= this.ordinal
}
