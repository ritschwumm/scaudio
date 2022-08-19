package scaudio.device.mpkMini.protocol

import scutil.core.implicits.*

import scaudio.midi.*

object Knob {
	val midiChannel:MidiChannel	= MidiChannel(0)

	val all	= values.toVector

	val fromMidiController:MidiController=>Option[Knob]	=
		all.mapBy(_.midiController).get(_)
}

enum Knob {
	case _0, _1, _2, _3, _4, _5, _6, _7

	def midiController:MidiController	= MidiController(index + 1)

	def index:Int	= this.ordinal
}
