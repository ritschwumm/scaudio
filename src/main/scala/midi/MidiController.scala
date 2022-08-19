package scaudio.midi

object MidiController {
	val min	= MidiController(0)
	val max	= MidiController(127)

	val all:Vector[MidiController]	= min.value.to(max.value).toVector.map(MidiController.apply)
}

// 0..127
final case class MidiController(value:Int) {
	require(value >= MidiController.min.value,	"value too small")
	require(value <= MidiController.max.value,	"value too large")
}
