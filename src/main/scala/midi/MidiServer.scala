package scaudio.midi

import javax.sound.midi.{MidiEvent as _, *}

import scutil.core.implicits.*
import scutil.lang.*

object MidiServer {
	def open(selectDevice:Predicate[MidiDeviceInfo], handler:MidiHandler):IoResource[Boolean]	=
		for {
			candidates	<-	IoResource.lift(findDevice(selectDevice))
			result		<-	candidates.headOption.traverse(openDevice(handler))
		}
		yield result.isDefined

	private def findDevice(selectDevice:Predicate[MidiDevice.Info]):Io[Vector[MidiDevice]]	=
		Io delay {
			for {
				deviceInfo	<- MidiSystem.getMidiDeviceInfo.toVector
				if selectDevice(deviceInfo)

				device		<- midiAvailable(MidiSystem.getMidiDevice(deviceInfo)).toVector
				if device.getMaxTransmitters != 0
			}
			yield device
		}

	// NOTE closing the device closes all transmitters and receivers
	private def openDevice(handler:MidiHandler)(device:MidiDevice):IoResource[Unit]	=
		for {
			// TODO midi deal with MidiUnavailableException here?
			_			<-	IoResource.unsafe.disposing(device.open())(_ => device.close())
			// TODO midi deal with MidiUnavailableException here?
			transmitter	<-	IoResource.unsafe.disposing(device.getTransmitter)(_.close())
			_			<-	IoResource delay {
								transmitter setReceiver new Receiver {
									def send(message:MidiMessage, timeStamp:Long):Unit	= {
										MidiEvent.parse(message).traverse(handler.handle(_, timeStamp)).unsafeRun()
									}
									def close():Unit	= {}
								}
							}
		}
		yield ()

	private def midiAvailable[T](it: =>T):Option[T]	=
		Catch.byType[MidiUnavailableException].in(it).toOption
}
