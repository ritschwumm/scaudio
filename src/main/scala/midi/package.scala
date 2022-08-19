package scaudio.midi

import javax.sound.midi.MidiDevice

//------------------------------------------------------------------------------

type MidiDeviceInfo	= MidiDevice.Info

//------------------------------------------------------------------------------

object MidiChannel {
	val min	= 0
	val max	= 16
}

// 0..16
type MidiChannel	= Int

//------------------------------------------------------------------------------

object MidiValue {
	val min	= 0
	val max	= 127
}

// 0..127
type MidiValue	= Int

//------------------------------------------------------------------------------

object MidiTime {
	val unsupported:MidiTime	= -1L
}

type MidiTime	= Long
