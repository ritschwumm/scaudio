package scaudio.midi

object MidiTime {
	val unsupported:MidiTime	= MidiTime(-1L)
}

final case class MidiTime(value:Long) extends Ordered[MidiTime] {
	def compare(that:MidiTime):Int	= java.lang.Long.compareUnsigned(this.value, that.value)
}
