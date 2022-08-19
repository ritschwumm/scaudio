package scaudio.midi

final case class MidiRange(start:MidiValue, size:Int) {
	require(size >= 0, "size too small")

	val end:MidiValue			= MidiValue(start.value+size-1)
	val all:Vector[MidiValue]	= start.value.to(end.value).toVector.map(MidiValue.apply)
}
