package scaudio.device.midiMix.protocol

import scutil.core.implicits.*

import scaudio.midi.*

object Button {
	val all	=
		Vector(BankLeft, BankRight, ShiftSolo)	++
		Strip.all.map(StripMute.apply)			++
		Strip.all.map(StripSolo.apply)			++
		Strip.all.map(StripRecArm.apply)

	val byMidiKey:MidiKey=>Option[Button]	=
		all.mapBy(_.midiKey).get(_)
}

enum Button {
	case BankLeft
	case BankRight
	case ShiftSolo
	case StripMute(strip:Strip)
	case StripSolo(strip:Strip)
	case StripRecArm(strip:Strip)

	def midiKey:MidiKey	=
		MidiKey(
			this match {
				case StripMute(strip)	=> strip.index*3+1
				case StripSolo(strip)	=> strip.index*3+2
				case StripRecArm(strip)	=> strip.index*3+3
				case BankLeft			=> 25
				case BankRight			=> 26
				case ShiftSolo			=> 27
			}
		)
}
