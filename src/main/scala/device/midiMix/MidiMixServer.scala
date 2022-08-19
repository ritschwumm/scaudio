package scaudio.device.midiMix

import scutil.core.implicits.*
import scutil.lang.*

import scaudio.midi.*

import scaudio.device.midiMix.protocol.*

object MidiMixServer {
	def open(onInput:Input=>Io[Unit]):IoResource[Boolean]	=
		MidiServer.open(
			MidiMixDevice.devicePredicate,
			(event:MidiEvent, time:MidiTime)	=> {
				Protocol.readInput(event).traverseVoid(onInput)
			}
		)
}
