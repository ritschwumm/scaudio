package scaudio.output

import scutil.core.implicits._
import scutil.lang._
import scutil.log._

/** a audio output using javax.sound.sampled - legacy variant */
@deprecated("use NewOutput", "0.196.0")
final class Output(config:OutputConfig, producer:FrameProducer) extends Logging with AutoCloseable {
	private val newOutput	= NewOutput.find(config).getOrError("audio is not available")
	private var disposer	= Disposer.empty

	/** actual configuration */
	val info:OutputInfo	= newOutput.outputInfo

	def start():Unit	= {
		disposer	= newOutput.runProducerChunked(producer).openVoid()
	}

	/** release all resources and stop the Thread */
	def close():Unit	= {
		try {
			disposer.dispose()
		}
		catch { case e:InterruptedException =>
			WARN("driverThread cannot be joined", e)
		}
	}
}
