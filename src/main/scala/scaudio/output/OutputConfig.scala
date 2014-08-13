package scaudio.output

import scutil.lang.ISeq

/** suggestions for an output's configuration */
case class OutputConfig(
	// tried in order, then the default mixer
	mixerNames:ISeq[String],
	rate:Int,
	blockFrames:Int,
	lineBlocks:Int,
	// suggestion, might not be followed
	headphone:Boolean
)
