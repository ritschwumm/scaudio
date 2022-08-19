package scaudio.device.mpkMini.protocol

import scutil.core.implicits.*

import scaudio.midi.*

/*
LEDs on these can be switched on and off with noteon on the same channel and key and velocity 127/0 for on/off
@see https://cycling74.com/forums/akai-mpk-mini-send-get-signals-to-light-buttons
NOTE the mpk mini II alledgedly support changing the rest of the buttons on the top left
*/
object Pad {
	val midiChannel:MidiChannel	= MidiChannel(1)

	val all	= PadBank.all.flatMap(_.pads)

	val fromMidiKey:MidiKey=>Option[Pad]	=
		all.mapBy(_.midiKey).get(_)
}

final case class Pad(bank:PadBank, button:PadButton) {
	def midiKey:MidiKey	= MidiKey(bank.midiKeys.start.value + button.index)
}
