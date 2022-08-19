package scaudio.midi

object MidiController {
	val min	= MidiController(0)
	val max	= MidiController(127)

	val all:Vector[MidiController]	= vector(min, max)

	def vector(min:MidiController, max:MidiController):Vector[MidiController]	= {
		require(min <= max, "bad order")
		min.value.to(max.value).toVector.map(MidiController.apply)
	}
}

// 0..127
final case class MidiController(value:Int) extends Ordered[MidiController] {
	require(value >= MidiController.min.value,	"value too small")
	require(value <= MidiController.max.value,	"value too large")

	def compare(that:MidiController):Int	= this.value.compare(that.value)
}
