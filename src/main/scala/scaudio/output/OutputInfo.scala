package scaudio.output

/** actual configuration used in an output */
case class OutputInfo(
	rate:Int,
	blockFrames:Int,
	lineBlocks:Int,
	headphone:Boolean
)
