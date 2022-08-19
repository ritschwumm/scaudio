package scaudio.device.mpkMini.protocol

import scaudio.midi.*

enum Input {
	case KeyChange(key:MidiKey, velocity:MidiVelocity)
	case PadChange(pad:Pad, velocity:MidiVelocity)
	case KnobChange(knob:Knob, value:MidiValue)
	// this is a controller, but it can only be off (0) or not-off (naything else)
	case SustainChange(on:Boolean)
}
