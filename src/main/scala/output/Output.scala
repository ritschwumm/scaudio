package scaudio.output

import javax.sound.sampled._

import scutil.core.implicits._
import scutil.lang._
import scutil.concurrent._

import scaudio.format._

/** an audio output using javax.sound.sampled */
object Output {
	private val sampleBits		= 16
	private val lineChannels	= 2
	private val frameBytes		= sampleBits/8*lineChannels

	//------------------------------------------------------------------------------

	/** returns None when no audio is available */
	def find(config:OutputConfig):Option[Output]	=
		makeSourceDataLine(config) map { sourceDataLine =>
			val outputInfo:OutputInfo	=
				OutputInfo(
					rate			= config.rate,
					blockFrames		= config.blockFrames,
					lineBlocks		= config.lineBlocks,
					headphone		= sourceDataLine.getFormat.getChannels == 4,
					frameBytes		= frameBytes
				)

			new Output(outputInfo, sourceDataLine)
		}

	//------------------------------------------------------------------------------

	private def makeSourceDataLine(config:OutputConfig):Option[SourceDataLine]	= {
		@SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
		def openDataLineCandidates(config:OutputConfig):Seq[Thunk[SourceDataLine]]	=
			for {
				mixer		<- mixers
				channels	<- desiredChannels
				audioFormat	= mkAudioFormat(channels)
				lineInfo	= mkLineInfo(audioFormat)
				if mixer isLineSupported lineInfo
			}
			yield thunk {
				mixer.getLine(lineInfo).asInstanceOf[SourceDataLine]
			}

		// NOTE null is the default mixer, we try this as a last resort
		def mixers:Seq[Mixer]	=
			mixerInfos :+ null map AudioSystem.getMixer

		def mixerInfos:Seq[Mixer.Info]	=
			for {
				mixerName	<- config.mixerNames
				mixerInfo	<- AudioSystem.getMixerInfo.toVector
				if mixerInfo.getName == mixerName
			}
			yield mixerInfo

		def mkLineInfo(audioFormat:AudioFormat):DataLine.Info	=
			new DataLine.Info(
				classOf[SourceDataLine],
				audioFormat
			)

		def mkAudioFormat(channels:Int):AudioFormat	=
			new AudioFormat(
				config.rate.toFloat,	// sampleRate
				sampleBits,				// sampleSizeInBits
				channels,				// channels
				true,   				// signed
				false					// little endian
			)

		def desiredChannels:Seq[Int]	=
			config.headphone.vector(2*lineChannels) :+ lineChannels

		openDataLineCandidates(config).headOption.map(_.apply())
	}
}

final class Output(info:OutputInfo, sourceDataLine:SourceDataLine) {
	private val usedOutputFormat	= sourceDataLine.getFormat
	private val blockBytes			= info.blockFrames * usedOutputFormat.getFrameSize

	val outputInfo:OutputInfo	= info

	// TODO check whether this buys us anything over runProducerUsingConsumer
	def runProducerChunked(producer:FrameProducer):IoResource[Unit]	= {
		for {
			_				<-	sourceDataLineLifecycle(sourceDataLine, usedOutputFormat, info.lineBlocks * blockBytes)
			_				<-	IoResource delay { sourceDataLine.start() }
			outputBuffer	<-	IoResource delay new Array[Byte](blockBytes)
			speakerBuffer	<-	IoResource delay new FrameBuffer
			phoneBuffer		<-	IoResource delay new FrameBuffer
			_				<-	SimpleWorker.create(
								"audio driver",
								Thread.MAX_PRIORITY,
								Io.delay {
									writeChunk(producer, speakerBuffer, phoneBuffer, outputBuffer, info.headphone)
									sourceDataLine.write(outputBuffer, 0, outputBuffer.length)
									// if (sourceDataLine.available == sourceDataLine.getBufferSize) { ERROR("underrun") }
									true
								}
							)
		}
		yield ()
	}

	def runProducerUsingConsumer(producer:FrameProducer):IoResource[Unit]	=
		for {
			consumer	<-	createConsumer
			forwarder	<-	IoResource delay new Forwarder(producer, consumer, info)
			_			<-	SimpleWorker.create(
								"audio driver",
								Thread.MAX_PRIORITY,
								Io.delay { forwarder.forward(); true }
							)
		}
		yield ()

	def createConsumer:IoResource[FrameConsumer]	= {
		for {
			_	<-	sourceDataLineLifecycle(sourceDataLine, usedOutputFormat, info.lineBlocks * blockBytes)
			_	<-	IoResource delay { sourceDataLine.start() }
		}
		yield {
			val outputBuffer	= new Array[Byte](blockBytes)
			new FrameConsumer(outputBuffer, sourceDataLine)
		}
	}

	private def sourceDataLineLifecycle(sourceDataLine:SourceDataLine, audioFormat:AudioFormat, bufferSize:Int):IoResource[Unit]	=
		IoResource.unsafe.disposing{
			sourceDataLine.open(audioFormat, bufferSize)
		}{ _	=>
			sourceDataLine.close()
		}

	private final class Forwarder(producer:FrameProducer, consumer:FrameConsumer, outputInfo:OutputInfo) {
		val speakerBuffer	= new FrameBuffer
		val phoneBuffer		= new FrameBuffer

		def forward():Unit	= {
			var i = 0
			while (i < outputInfo.blockFrames) {
				producer.produce(speakerBuffer, phoneBuffer)

				consumer.consume(speakerBuffer.left, speakerBuffer.right)
				speakerBuffer.clear()

				if (outputInfo.headphone) {
					consumer.consume(phoneBuffer.left, phoneBuffer.right)
					phoneBuffer.clear()
				}

				i	= i + 1
			}
		}
	}

	/** Thread main loop */
	private def writeChunk(producer:FrameProducer, speakerBuffer:FrameBuffer, phoneBuffer:FrameBuffer, outputBuffer:Array[Byte], phoneEnabled:Boolean):Unit	= {
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
	}
}
