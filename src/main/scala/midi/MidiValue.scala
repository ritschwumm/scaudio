package scaudio.midi

object MidiValue {
	val min	= new MidiValue(0)
	val max	= new MidiValue(127)

	def onOff(onFlag:Boolean):MidiValue	=
		if (onFlag) max else min

	def apply(value:Int):MidiValue	= {
		require(value >= MidiValue.min.value,	"value too small")
		require(value <= MidiValue.max.value,	"value too large")
		new MidiValue(value)
	}
}

// 0..127
final case class MidiValue private (value:Int) extends Ordered[MidiValue] {
	def compare(that:MidiValue):Int	= this.value.compare(that.value)
}
