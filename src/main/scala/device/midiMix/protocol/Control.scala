package scaudio.device.midiMix.protocol

import scutil.core.implicits.*

import scaudio.midi.*

object Control {
	val all	=
		Vector(MasterFader)				++
		Strip.all.map(StripKnob1.apply)	++
		Strip.all.map(StripKnob2.apply)	++
		Strip.all.map(StripKnob3.apply)	++
		Strip.all.map(StripFader.apply)

	val byMidiController:MidiController=>Option[Control]	=
		all.mapBy(_.midiController).get(_)
}

enum Control {
	case MasterFader

	case StripKnob1(strip:Strip)
	case StripKnob2(strip:Strip)
	case StripKnob3(strip:Strip)
	case StripFader(strip:Strip)

	def midiController:MidiController	=
		MidiController(
			this match {
				case MasterFader		=> 62
				case StripKnob1(strip)	=> strip.index*4 + (if (strip.index < 4) 16 else 30) + 0
				case StripKnob2(strip)	=> strip.index*4 + (if (strip.index < 4) 16 else 30) + 1
				case StripKnob3(strip)	=> strip.index*4 + (if (strip.index < 4) 16 else 30) + 2
				case StripFader(strip)	=> strip.index*4 + (if (strip.index < 4) 16 else 30) + 3
			}
		)
}
