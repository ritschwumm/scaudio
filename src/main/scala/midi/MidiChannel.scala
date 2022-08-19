package scaudio.midi

object MidiChannel {
	val min	= MidiChannel(0)
	val max	= MidiChannel(15)

	val all:Vector[MidiChannel]	= min.value.to(max.value).toVector.map(MidiChannel.apply)
}

// 0..15
final case class MidiChannel(value:Int) {
	require(value >= MidiChannel.min.value,	"value too small")
	require(value <= MidiChannel.max.value,	"value too large")
}
