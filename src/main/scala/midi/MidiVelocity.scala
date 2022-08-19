package scaudio.midi

object MidiVelocity {
	val min	= MidiVelocity(0)
	val max	= MidiVelocity(127)

	def onOff(onFlag:Boolean):MidiVelocity	=
		if (onFlag) min else max
}

// 0..127
final case class MidiVelocity(value:Int) extends Ordered[MidiVelocity] {
	require(value >= MidiVelocity.min.value,	"value too small")
	require(value <= MidiVelocity.max.value,	"value too large")

	def compare(that:MidiVelocity):Int	= this.value.compare(that.value)
}
