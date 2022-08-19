package scaudio.midi

import scutil.lang.*

trait MidiHandler {
	def handle(event:MidiEvent, time:MidiTime):Io[Unit]
}
