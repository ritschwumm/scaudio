package scaudio.midi

import javax.sound.midi.{MidiEvent as _, *}

import scutil.core.implicits.*
import scutil.lang.*

object MidiServer {
	def open(selectDevice:Predicate[String], handler:MidiHandler):IoResource[Boolean]	=
		for {
			candidates	<-	IoResource.lift(findCandidates(selectDevice))
			result		<-	candidates.headOption.traverse(openCandidate(handler))
		}
		yield result.isDefined

	private def openCandidate(handler:MidiHandler)(candidate:Candidate):IoResource[Unit]	=
		for {
			// closing the device closes all transmitters and receivers
			_			<-	IoResource.unsafe.disposing(candidate.device.open())(_ => candidate.device.close())
			_			<-	IoResource delay {
								candidate.transmitter setReceiver new Receiver {
									def send(message:MidiMessage, timeStamp:Long):Unit	= {
										MidiEvent.parse(message).traverse(handler.handle(_, timeStamp)).unsafeRun()
									}
									def close():Unit	= {}
								}
							}
		}
		yield ()

	private final case class Candidate(device:MidiDevice, transmitter:Transmitter)

	private def findCandidates(selectDevice:Predicate[String]):Io[Vector[Candidate]]	=
		Io delay {
			for {
				deviceInfo	<- MidiSystem.getMidiDeviceInfo.toVector
				if selectDevice(deviceInfo.getName)

				device		<- midiAvailable(MidiSystem.getMidiDevice(deviceInfo)).toVector
				if device.getMaxTransmitters != 0

				transmitter	<- midiAvailable(device.getTransmitter).toVector
			}
			yield Candidate(device, transmitter)
		}

	private def midiAvailable[T](it: =>T):Option[T]	=
		Catch.byType[MidiUnavailableException].in(it).toOption
}
