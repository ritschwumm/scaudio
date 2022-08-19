package scaudio.midi

object MidiValue {
	val min	= MidiValue(0)
	val max	= MidiValue(127)

	def onOff(onFlag:Boolean):MidiValue	=
		if (onFlag) min else max
}

// 0..127
final case class MidiValue(value:Int) extends Ordered[MidiValue] {
	require(value >= MidiValue.min.value,	"value too small")
	require(value <= MidiValue.max.value,	"value too large")

	def compare(that:MidiValue):Int	= this.value.compare(that.value)
}
