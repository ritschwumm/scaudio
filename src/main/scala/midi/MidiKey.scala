package scaudio.midi

object MidiKey {
	val min	= MidiKey(0)
	val max	= MidiKey(127)

	val all:Vector[MidiKey]	= vector(min, max)

	def vector(min:MidiKey, max:MidiKey):Vector[MidiKey]	= {
		require(min <= max, "bad order")
		min.value.to(max.value).toVector.map(MidiKey.apply)
	}
}

// 0..127
final case class MidiKey(value:Int) extends Ordered[MidiKey] {
	require(value >= MidiKey.min.value,	"value too small")
	require(value <= MidiKey.max.value,	"value too large")

	def compare(that:MidiKey):Int	= this.value.compare(that.value)
}
