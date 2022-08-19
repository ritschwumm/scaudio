package scaudio.midi

import scutil.lang.*

object MidiHandler {
	val noTime	= -1L
}

trait MidiHandler {
	// time is -1 (noTime) if not supported
	def handle(event:MidiEvent, time:Long):Io[Unit]
}
