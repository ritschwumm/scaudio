package scaudio.sample

object Sample {
	object empty extends Sample {
		val frameRate:Int				= 1
		val frameCount:Int				= 0
		val sampleBytes:Int				= 0
		val channels:IArray[Channel]	= IArray.empty
	}
}

trait Sample {
	def frameRate:Int
	def frameCount:Int
	def sampleBytes:Int
	def channels:IArray[Channel]

	inline def frameBytes:Int	=
		sampleBytes * channels.size

	inline def channelOrEmpty(index:Int):Channel	=
		channels lift index getOrElse Channel.empty
}
