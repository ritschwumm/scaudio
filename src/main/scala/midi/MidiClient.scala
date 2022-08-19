package scaudio.midi

import javax.sound.midi.{MidiEvent as _, *}

import scutil.core.implicits.*
import scutil.lang.*

object MidiClient {
	def open(selectDevice:Predicate[MidiDeviceInfo]):IoResource[Option[MidiClient]]	=
		for {
			candidates	<-	IoResource.lift(findDevice(selectDevice))
			client		<-	candidates.headOption.traverse(openDevice)
		}
		yield client

	private def findDevice(selectDevice:Predicate[MidiDevice.Info]):Io[Vector[MidiDevice]]	=
		Io delay {
			for {
				deviceInfo	<- MidiSystem.getMidiDeviceInfo.toVector
				if selectDevice(deviceInfo)

				device		<- midiAvailable(MidiSystem getMidiDevice deviceInfo).toVector
				if device.getMaxReceivers != 0
			}
			yield device
		}

	// NOTE closing the device closes all transmitters and receivers
	private def openDevice(device:MidiDevice):IoResource[MidiClient]	=
		for {
			// TODO midi deal with MidiUnavailableException here?
			_			<-	IoResource.unsafe.disposing(device.open())(_ => device.close())
			// TODO midi deal with MidiUnavailableException here?
			receiver	<-	IoResource.unsafe.disposing(device.getReceiver)(_.close())
		}
		yield new MidiClient {
			def send(event:MidiEvent, time:MidiTime):Io[Either[Exception,Unit]]	=
				Io
				.delay {
					receiver.send(MidiEvent.unparse(event), time)
				}
				.attempt
		}

	private def midiAvailable[T](it: =>T):Option[T]	=
		Catch.byType[MidiUnavailableException].in(it).toOption
}

trait MidiClient {
	def send(event:MidiEvent, time:MidiTime):Io[Either[Exception,Unit]]
}
