package scaudio.sample

import scutil.Implicits._

import scaudio.interpolation.Channel

trait Sample {
	val frameRate:Int
	val frameCount:Int
	val channelCount:Int
	def get(frame:Int, channel:Int):Float
	
	def channel(index:Int):Option[Channel]	=
			index < channelCount guard new SampleChannel(this, index)
		
	def channels:Seq[Channel]	=
			0 until channelCount map { new SampleChannel(this, _) }
}
