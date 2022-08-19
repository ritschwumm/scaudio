package scaudio.midi

import javax.sound.midi.{ MidiChannel as _ , * }

import scutil.core.implicits.*

object MidiEvent {
	def parse(message:MidiMessage):Option[MidiEvent]	=
		message matchOption {
			case sm:ShortMessage if sm.getCommand == ShortMessage.NOTE_ON			=> NoteOn(sm.getChannel, sm.getData1, sm.getData2)
			case sm:ShortMessage if sm.getCommand == ShortMessage.NOTE_OFF			=> NoteOff(sm.getChannel, sm.getData1, sm.getData2)
			case sm:ShortMessage if sm.getCommand == ShortMessage.CONTROL_CHANGE	=> ControlChange(sm.getChannel, sm.getData1, sm.getData2)
		}

	def unparse(event:MidiEvent):MidiMessage	=
		event match {
			case NoteOn(channel, note, velocity)		=> new ShortMessage(ShortMessage.NOTE_ON, note, velocity)
			case NoteOff(channel, note, velocity)		=> new ShortMessage(ShortMessage.NOTE_OFF, note, velocity)
			case ControlChange(channel, control, value)	=> new ShortMessage(ShortMessage.CONTROL_CHANGE, control, value)
		}
}

enum MidiEvent {
	case NoteOn(channel:MidiChannel, note:MidiValue, velocity:MidiValue)
	case NoteOff(channel:MidiChannel, note:MidiValue, velocity:MidiValue)
	case ControlChange(channel:MidiChannel, control:MidiValue, value:MidiValue)
}
