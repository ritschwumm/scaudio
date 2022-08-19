package scaudio.midi

object MidiKey {
	val min	= new MidiKey(0)
	val max	= new MidiKey(127)

	val all:Vector[MidiKey]	= vector(min, max)

	def vector(min:MidiKey, max:MidiKey):Vector[MidiKey]	= {
		require(min <= max, "bad order")
		min.value.to(max.value).toVector.map(MidiKey.apply)
	}

	def apply(value:Int):MidiKey	= {
		require(value >= MidiKey.min.value,	"value too small")
		require(value <= MidiKey.max.value,	"value too large")
		new MidiKey(value)
	}
}

// 0..127
final case class MidiKey private (value:Int) extends Ordered[MidiKey] {
	def compare(that:MidiKey):Int	= this.value.compare(that.value)
}
