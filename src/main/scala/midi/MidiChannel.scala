package scaudio.midi

object MidiChannel {
	val min	= new MidiChannel(0)
	val max	= new MidiChannel(15)

	val all:Vector[MidiChannel]	= vector(min, max)

	def vector(min:MidiChannel, max:MidiChannel):Vector[MidiChannel]	= {
		require(min <= max, "bad order")
		min.value.to(max.value).toVector.map(MidiChannel.apply)
	}

	def apply(value:Int):MidiChannel	= {
		require(value >= MidiChannel.min.value,	"value too small")
		require(value <= MidiChannel.max.value,	"value too large")
		new MidiChannel(value)
	}
}

// 0..15
final case class MidiChannel private (value:Int) extends Ordered[MidiChannel] {
	def compare(that:MidiChannel):Int	= this.value.compare(that.value)
}
