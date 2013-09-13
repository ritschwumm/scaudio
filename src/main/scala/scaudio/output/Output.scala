package scaudio.output

import javax.sound.sampled._

import scutil.lang._
import scutil.Implicits._
import scutil.log._

import scaudio.util._

/** a audio output using javax.sound.sampled */
final class Output(config:OutputConfig, producer:FrameProducer) extends Logging {
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
					audioFormat)
					
	private def mkAudioFormat(channels:Int):AudioFormat	=
			new AudioFormat(
					config.rate,	// sampleRate
					16,				// sampleSizeInBits
					channels,		// channels
					true,   		// signed
					false)			// little endian
	
	private val desiredChannels:Seq[Int]	= 
			config.headphone cata (Seq(2), Seq(4, 2))
		
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
			openDataLineCandidates.headOption getOrError "audio not available" apply ()
	
	private val usedOutputFormat	= sourceDataLine.getFormat
	
	private val phoneEnabled	= usedOutputFormat.getChannels == 4
	
	private val blockBytes		= config.blockFrames * usedOutputFormat.getFrameSize
	private val outputBuffer 	= new Array[Byte](blockBytes)
	
	private val speakerBuffer	= new FrameBuffer
	private val phoneBuffer		= new FrameBuffer
	
	sourceDataLine open (usedOutputFormat, config.lineBlocks * blockBytes)
	sourceDataLine.start()
	
	@volatile 
	private var keepOn	= true
	
	private object driverThread extends Thread { 
		setPriority(Thread.MAX_PRIORITY)
		setName("audio driver")
		
		override def run() {
			while (keepOn) {
				loop()
			}
		}
	}
	
	/** Thread main loop */
	private def loop() {
		// NOTE could use sourceDataLine.available to determine how much data can be written without blocking
		// SourceDataLine docs says: bufferSize - available
		// DataLine docs says: available
		var offset	= 0
		val end		= outputBuffer.length
		while (offset < end) {
			producer produce (speakerBuffer, phoneBuffer)
			
			ShortAudio putClamped (speakerBuffer.left,	outputBuffer, offset+0)
			ShortAudio putClamped (speakerBuffer.right,	outputBuffer, offset+2)
			offset	+= 4
			speakerBuffer.clear()
			
			if (phoneEnabled) {
				ShortAudio putClamped (phoneBuffer.left,	outputBuffer, offset+0)
				ShortAudio putClamped (phoneBuffer.right,	outputBuffer, offset+2)
				offset	+= 4
				phoneBuffer.clear()
			}
		}
		
		sourceDataLine write (outputBuffer, 0, outputBuffer.length)
	}
	
	//------------------------------------------------------------------------------
	//## public api
	
	/** actual configuration */
	val info:OutputInfo	= 
			OutputInfo(
				rate			= config.rate,
				blockFrames		= config.blockFrames,
				lineBlocks		= config.lineBlocks,
				headphone		= phoneEnabled
			)
			
	def start() {
		require(keepOn, "already disposed Output cannot be started")
		driverThread.start()
	}
	
	/** release all resources and stop the Thread */
	def dispose() {
		// NOTE sometimes isInterrupted doesn't seem to return true at all
		keepOn	= false
		driverThread.interrupt()
		try {
			driverThread.join()
		}
		catch { case e:InterruptedException =>
			WARN("driverThread cannot be joined", e)
		}
		sourceDataLine.close()
	}
}
