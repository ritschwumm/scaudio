package scaudio.output

/** actual configuration used in an output */
final case class OutputInfo(
	rate:Int,
	blockFrames:Int,
	lineBlocks:Int,
	headphone:Boolean,
	frameBytes:Int
)
