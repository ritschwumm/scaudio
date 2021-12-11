package scaudio.midi

import javax.sound.midi.*

import scutil.core.implicits.*

object MidiEvent {
	def parse(message:MidiMessage):Option[MidiEvent]	=
		message matchOption {
			case sm:ShortMessage if sm.getCommand == ShortMessage.NOTE_ON			=> NoteOn(sm.getChannel, sm.getData1, sm.getData2)
			case sm:ShortMessage if sm.getCommand == ShortMessage.NOTE_OFF			=> NoteOff(sm.getChannel, sm.getData1, sm.getData2)
			case sm:ShortMessage if sm.getCommand == ShortMessage.CONTROL_CHANGE	=> ControlChange(sm.getChannel, sm.getData1, sm.getData2)
		}

	final case class NoteOn(channel:Int, note:Int, velocity:Int)		extends MidiEvent
	final case class NoteOff(channel:Int, note:Int, velocity:Int)		extends MidiEvent
	final case class ControlChange(channel:Int, control:Int, value:Int)	extends MidiEvent
}

sealed trait MidiEvent
