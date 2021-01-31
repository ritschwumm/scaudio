package scaudio.output

import javax.sound.sampled._

import scutil.core.implicits._
import scutil.lang._
import scutil.concurrent._
import scutil.log._

import scaudio.format._

object Output {
	private val sampleBits		= 16
	private val lineChannels	= 2
	private val frameBytes		= sampleBits/8*lineChannels

	// TODO construct this from multiple Usings to ensure all resources are properly freed
	def create(config:OutputConfig, producer:FrameProducer):Using[Unit]	=
		Using.of(
			()	=> new Output(config, producer) doto (_.start())
		)(
			_.close()
		)
		.void
}

/** a audio output using javax.sound.sampled */
final class Output(config:OutputConfig, producer:FrameProducer) extends Logging with AutoCloseable {
	private val mixerInfos:Seq[Mixer.Info]	=
		for {
			mixerName	<- config.mixerNames map Some.apply
			mixerInfo	<- AudioSystem.getMixerInfo.toVector
			if mixerInfo.getName == mixerName
		}
		yield mixerInfo

	// NOTE null is the default mixer, we try this as a last resort
	private val mixers:Seq[Mixer]	=
		mixerInfos :+ null map AudioSystem.getMixer

	private def mkLineInfo(audioFormat:AudioFormat):DataLine.Info	=
		new DataLine.Info(
			classOf[SourceDataLine],
			audioFormat
		)

	private def mkAudioFormat(channels:Int):AudioFormat	=
		new AudioFormat(
			config.rate.toFloat,	// sampleRate
			Output.sampleBits,		// sampleSizeInBits
			channels,				// channels
			true,   				// signed
			false					// little endian
		)

	private val desiredChannels:Seq[Int]	=
		config.headphone.vector(2*Output.lineChannels) :+ Output.lineChannels

	@SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
	private val openDataLineCandidates:Seq[Thunk[SourceDataLine]]	=
		for {
			mixer		<- mixers
			channels	<- desiredChannels
			audioFormat	= mkAudioFormat(channels)
			lineInfo	= mkLineInfo(audioFormat)
			if mixer isLineSupported lineInfo
		}
		yield thunk {
			(mixer getLine lineInfo).asInstanceOf[SourceDataLine]
		}

	private val sourceDataLine	=
		(openDataLineCandidates.headOption getOrError "audio not available").apply()

	private val usedOutputFormat	= sourceDataLine.getFormat

	private val phoneEnabled	= usedOutputFormat.getChannels == 4

	private val blockBytes		= config.blockFrames * usedOutputFormat.getFrameSize
	private val outputBuffer 	= new Array[Byte](blockBytes)

	private val speakerBuffer	= new FrameBuffer
	private val phoneBuffer		= new FrameBuffer

	sourceDataLine.open(usedOutputFormat, config.lineBlocks * blockBytes)
	sourceDataLine.start()

	/** Thread main loop */
	private def writeChunk():Unit	= {
		// NOTE could use sourceDataLine.available to determine how much data can be written without blocking
		// SourceDataLine docs says: bufferSize - available
		// DataLine docs says: available
		var offset	= 0
		val end		= outputBuffer.length
		while (offset < end) {
			producer.produce(speakerBuffer, phoneBuffer)

			AudioFormat_S2LE.putClamped(speakerBuffer.left,		outputBuffer, offset+0)
			AudioFormat_S2LE.putClamped(speakerBuffer.right,	outputBuffer, offset+2)
			offset	+= 4
			speakerBuffer.clear()

			if (phoneEnabled) {
				AudioFormat_S2LE.putClamped(phoneBuffer.left,	outputBuffer, offset+0)
				AudioFormat_S2LE.putClamped(phoneBuffer.right,	outputBuffer, offset+2)
				offset	+= 4
				phoneBuffer.clear()
			}
		}

		// if (sourceDataLine.available == sourceDataLine.getBufferSize) {
		// 	ERROR("underrun")
		// }
		sourceDataLine.write(outputBuffer, 0, outputBuffer.length)
	}

	//------------------------------------------------------------------------------
	//## public api

	/** actual configuration */
	val info:OutputInfo	=
		OutputInfo(
			rate			= config.rate,
			blockFrames		= config.blockFrames,
			lineBlocks		= config.lineBlocks,
			headphone		= phoneEnabled,
			frameBytes		= Output.frameBytes
		)

	@volatile
	private var driverThread:Disposable	= Disposable.empty

	def start():Unit	= {
		driverThread	=
			SimpleWorker.build("audio driver", Thread.MAX_PRIORITY, Io.delay { writeChunk(); true }).openVoid()
	}

	/** release all resources and stop the Thread */
	def close():Unit	= {
		try {
			driverThread.dispose()
		}
		catch { case e:InterruptedException =>
			WARN("driverThread cannot be joined", e)
		}
		sourceDataLine.close()
	}
}
