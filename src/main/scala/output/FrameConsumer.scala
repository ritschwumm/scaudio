package scaudio.output

import javax.sound.sampled.*

import scaudio.format.*

final class FrameConsumer(outputBuffer:Array[Byte], sourceDataLine:SourceDataLine) {
	private var outputIndex	= 0

	/**
	* to be called twice per frame when outputInfo.headphone is true, once otherwise
	*/
	def consume(left:Float, right:Float):Unit	= {
		AudioFormat_S2LE.putClamped(left,	outputBuffer, outputIndex+0)
		AudioFormat_S2LE.putClamped(right,	outputBuffer, outputIndex+2)
		outputIndex	+= 4
		if (outputIndex >= outputBuffer.size) {
			sourceDataLine.write(outputBuffer, 0, outputBuffer.length)
			outputIndex	= 0
		}
	}
}
