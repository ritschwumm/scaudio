package scaudio.midi

object MidiVelocity {
	val min	= new MidiVelocity(0)
	val max	= new MidiVelocity(127)

	def onOff(onFlag:Boolean):MidiVelocity	=
		if (onFlag) max else min

	def apply(value:Int):MidiVelocity	= {
		require(value >= MidiVelocity.min.value,	"value too small")
		require(value <= MidiVelocity.max.value,	"value too large")
		new MidiVelocity(value)
	}
}

// 0..127
final case class MidiVelocity private (value:Int) extends Ordered[MidiVelocity] {
	def compare(that:MidiVelocity):Int	= this.value.compare(that.value)
}
