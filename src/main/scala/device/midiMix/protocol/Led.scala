package scaudio.device.midiMix.protocol

import scaudio.midi.*

object Led {
	val all	=
		Vector(BankLeft, BankRight)		++
		Strip.all.map(StripMute.apply)	++
		Strip.all.map(StripRecArm.apply)
}

enum Led {
	// NOTE StripSolo and ShiftSolo buttons do not have an LED
	case BankLeft
	case BankRight
	case StripMute(strip:Strip)
	case StripRecArm(strip:Strip)

	def midiKey:MidiKey	= toButton.midiKey

	private def toButton:Button	=
		this match {
			case BankLeft			=> Button.BankLeft
			case BankRight			=> Button.BankRight
			case StripMute(strip)	=> Button.StripMute(strip)
			case StripRecArm(strip)	=> Button.StripRecArm(strip)
		}
}
