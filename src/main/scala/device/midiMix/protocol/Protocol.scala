package scaudio.device.midiMix.protocol

import scaudio.midi.*

import scaudio.device.midiMix.MidiMixDevice

object Protocol {
	def readInput(event:MidiEvent):Option[Input]	= {
		val midiChannel	= MidiMixDevice.midiChannel

		event match {
			case MidiEvent.NoteOn(`midiChannel`, key, _)	=>
				Button.byMidiKey(key).map(Input.ButtonChange(_, true))

			case MidiEvent.NoteOff(`midiChannel`, key, _)	=>
				Button.byMidiKey(key).map(Input.ButtonChange(_, false))

			case MidiEvent.ControlChange(`midiChannel`, ctrl, value)	=>
				Control.byMidiController(ctrl).map(Input.ControlChange(_, value))

			case _	=>
				None
		}
	}

	def writeOutput(output:Output):MidiEvent	=
		output match {
			case Output.LedChange(led, on)	=>
				MidiEvent.NoteOn(
					MidiMixDevice.midiChannel,
					led.midiKey,
					MidiVelocity.onOff(on)
				)
		}
}
