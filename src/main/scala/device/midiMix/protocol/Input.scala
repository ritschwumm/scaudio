package scaudio.device.midiMix.protocol

import scaudio.midi.*

enum Input {
	case ButtonChange(button:Button, on:Boolean)
	case ControlChange(control:Control, value:MidiValue)
}
