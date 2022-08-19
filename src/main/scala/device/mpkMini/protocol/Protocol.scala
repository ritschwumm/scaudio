package scaudio.device.mpkMini.protocol

import scaudio.midi.*

object Protocol {
	def readInput(event:MidiEvent):Option[Input]	=
		event match {
			case MidiEvent.NoteOn(Keyboard.midiChannel, key, velocity)	=>
				Some(Input.KeyChange(key, velocity))

			case MidiEvent.NoteOn(Pad.midiChannel, key, velocity)	=>
				Pad.fromMidiKey(key).map(pad =>
					Input.PadChange(pad, velocity)
				)

			case MidiEvent.ControlChange(Knob.midiChannel, ctrl, value)	=>
				// TODO device ugly: the channel for sustain is the same!
				if (ctrl == Sustain.midiController) {
					Some(Input.SustainChange(value != MidiValue.min))
				}
				else {
					Knob.fromMidiController(ctrl).map(knob =>
						Input.KnobChange(knob, value)
					)
				}

			case _	=>
				None
		}

	def writeOutput(output:Output):MidiEvent	=
		output match {
			case Output.LedChange(led, on)	=>
				MidiEvent.NoteOn(
					Led.midiChannel,
					led.midiKey,
					MidiVelocity.onOff(on)
				)
		}
}
