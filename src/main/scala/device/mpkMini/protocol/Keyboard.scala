package scaudio.device.mpkMini.protocol

import scaudio.midi.*

import scaudio.device.MidiKeyRange

object Keyboard {
	val midiChannel:MidiChannel	= MidiChannel(0)

	// unless shifted!
	val midiKeys:MidiKeyRange	= MidiKeyRange(MidiKey(48), 25)
}
