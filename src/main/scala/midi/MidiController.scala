package scaudio.midi

object MidiController {
	val min	= new MidiController(0)
	val max	= new MidiController(127)

	val all:Vector[MidiController]	= vector(min, max)

	def vector(min:MidiController, max:MidiController):Vector[MidiController]	= {
		require(min <= max, "bad order")
		Vector.range(min.value, max.value-1).map(MidiController.apply)
	}

	def apply(value:Int):MidiController	= {
		require(value >= MidiController.min.value,	"value too small")
		require(value <= MidiController.max.value,	"value too large")
		new MidiController(value)
	}
}

// 0..127
final case class MidiController private (value:Int) extends Ordered[MidiController] {
	def compare(that:MidiController):Int	= this.value.compare(that.value)
}
