package scaudio.midi

import javax.sound.midi.{ MidiChannel as _ , * }

import scutil.core.implicits.*

object MidiEvent {
	def parse(message:MidiMessage):Option[MidiEvent]	=
		message matchOption {
			case sm:ShortMessage if sm.getCommand == ShortMessage.NOTE_ON			=> NoteOn(MidiChannel(sm.getChannel), MidiKey(sm.getData1), MidiVelocity(sm.getData2))
			case sm:ShortMessage if sm.getCommand == ShortMessage.NOTE_OFF			=> NoteOff(MidiChannel(sm.getChannel), MidiKey(sm.getData1), MidiVelocity(sm.getData2))
			case sm:ShortMessage if sm.getCommand == ShortMessage.CONTROL_CHANGE	=> ControlChange(MidiChannel(sm.getChannel), MidiController(sm.getData1), MidiValue(sm.getData2))
		}

	def unparse(event:MidiEvent):MidiMessage	=
		event match {
			case NoteOn(channel, note, velocity)		=> new ShortMessage(ShortMessage.NOTE_ON, note.value, velocity.value)
			case NoteOff(channel, note, velocity)		=> new ShortMessage(ShortMessage.NOTE_OFF, note.value, velocity.value)
			case ControlChange(channel, control, value)	=> new ShortMessage(ShortMessage.CONTROL_CHANGE, control.value, value.value)
		}
}

enum MidiEvent {
	case NoteOn(channel:MidiChannel, note:MidiKey, velocity:MidiVelocity)
	case NoteOff(channel:MidiChannel, note:MidiKey, velocity:MidiVelocity)
	case ControlChange(channel:MidiChannel, controller:MidiController, value:MidiValue)
}
