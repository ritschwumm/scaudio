package scaudio.midi

object MidiKey {
	val min	= MidiKey(0)
	val max	= MidiKey(127)

	val all:Vector[MidiKey]	= min.value.to(max.value).toVector.map(MidiKey.apply)
}

// 0..127
final case class MidiKey(value:Int) {
	require(value >= MidiKey.min.value,	"value too small")
	require(value <= MidiKey.max.value,	"value too large")
}
