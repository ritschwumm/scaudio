package scaudio.output

/** suggestions for an output's configuration */
case class OutputConfig(
	// tried in order, then the default mixer
	mixerNames:Seq[String],
	rate:Int,
	blockFrames:Int,
	lineBlocks:Int,
	// suggestion, might not be followed
	headphone:Boolean
)
