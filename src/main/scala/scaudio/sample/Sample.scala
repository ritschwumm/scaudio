package scaudio.sample

import scutil.lang.ISeq
import scutil.implicits._

object Sample {
	object empty extends Sample {
		val frameRate:Int						= 1
		val frameCount:Int						= 0
		val channelCount:Int					= 0
		def get(frame:Int, channel:Int):Float	= 0
	}
}

trait Sample {
	val frameRate:Int
	val frameCount:Int
	val channelCount:Int
	def get(frame:Int, channel:Int):Float
	
	def channel(index:Int):Option[Channel]	=
			index < channelCount guard new SampleChannel(this, index)
		
	def channels:ISeq[Channel]	=
			0 until channelCount map { new SampleChannel(this, _) }
}
