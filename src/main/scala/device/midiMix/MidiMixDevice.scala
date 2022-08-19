package scaudio.device.midiMix

import scutil.core.implicits.*
import scutil.lang.*

import scaudio.midi.*

object MidiMixDevice {
	val midiChannel:MidiChannel	= MidiChannel(0)

	// TODO device hardcoded
	val devicePredicate:Predicate[MidiDeviceInfo] = {
		val linuxPredicate:Predicate[MidiDeviceInfo]	= { _.getName startsWith "Mix " }
		val osxPredicate:Predicate[MidiDeviceInfo]		= { _.getName == "MIDI Mix" }
		linuxPredicate || osxPredicate
	}
}
