package scaudio.midi

trait MidiHandler {
	def handle(event:MidiEvent, time:Long):Unit
}
