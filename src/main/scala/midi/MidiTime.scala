package scaudio.midi

object MidiTime {
	val unsupported:MidiTime	= MidiTime(-1L)
}

final case class MidiTime(value:Long)
