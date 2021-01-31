package scaudio.midi

import javax.sound.midi.{MidiEvent => _, _}

import scutil.core.implicits._
import scutil.lang._

// TODO drone.midi.MidiServer and melodica.midi.MidiServer are identical
object MidiServer {
	// TODO using throws
	def open(selectDevice:Predicate[String], handler:MidiHandler):IoResource[Unit]	=
		for {
			candidate	<-	IoResource delay {
								findDevices(selectDevice).headOption getOrError "no midi device found"
							}
			// closing the device closes all transmitters and receivers
			_			<-	IoResource.unsafe.disposing(candidate.device.open())(_ => candidate.device.close())
			_			<-	IoResource delay {
								candidate.transmitter setReceiver new Receiver {
									def send(message:MidiMessage, timeStamp:Long):Unit	= {
										MidiEvent parse message foreach { handler.handle(_, timeStamp) }
									}
									def close():Unit	= {}
								}
							}
		}
		yield ()

	private final case class Candidate(device:MidiDevice, transmitter:Transmitter)

	private def findDevices(selectDevice:Predicate[String]):Vector[Candidate]	=
		for {
			deviceInfo	<- MidiSystem.getMidiDeviceInfo.toVector
			if selectDevice(deviceInfo.getName)
			device		<- midiAvailable(MidiSystem getMidiDevice deviceInfo).toVector
			if device.getMaxTransmitters != 0
			transmitter	<- midiAvailable(device.getTransmitter).toVector
		}
		yield Candidate(device, transmitter)

	private def midiAvailable[T](it: =>T):Option[T]	=
		Catch.byType[MidiUnavailableException].in(it).toOption
}
