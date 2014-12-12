package scaudio.sample

object Sample {
	object empty extends Sample {
		val frameRate:Int			= 1
		val frameCount:Int			= 0
		val channels:Seq[Channel]	= Nil
	}
}

trait Sample {
	def frameRate:Int
	def frameCount:Int
	def channels:Seq[Channel]
	
	def channelOrEmpty(index:Int):Channel	=
			channels lift index getOrElse Channel.empty
}
