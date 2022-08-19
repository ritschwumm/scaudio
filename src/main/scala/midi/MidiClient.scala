package scaudio.midi

import javax.sound.midi.{MidiEvent as _, *}

import scutil.core.implicits.*
import scutil.lang.*

object MidiClient {
	val noTime	= -1L

	def open(selectDevice:Predicate[String]):IoResource[Option[MidiClient]]	=
		for {
			candidates	<-	IoResource.lift(findCandidates(selectDevice))
			client		<-	candidates.headOption.traverse(openCandidate)
		}
		yield client

	private def openCandidate(candidate:Candidate):IoResource[MidiClient]	=
		for {
			// closing the device closes all transmitters and receivers
			_	<-	IoResource.unsafe.disposing(candidate.device.open())(_ => candidate.device.close())
		}
		yield new MidiClient {
			def send(event:MidiEvent, time:Long):Io[Unit]	=
				Io delay {
					candidate.receiver.send(MidiEvent.unparse(event), time)
				}
		}

	private final case class Candidate(device:MidiDevice, receiver:Receiver)

	private def findCandidates(selectDevice:Predicate[String]):Io[Vector[Candidate]]	=
		Io delay {
			for {
				deviceInfo	<- MidiSystem.getMidiDeviceInfo.toVector
				if selectDevice(deviceInfo.getName)

				device		<- midiAvailable(MidiSystem getMidiDevice deviceInfo).toVector
				if device.getMaxReceivers != 0

				receiver	<- midiAvailable(device.getReceiver).toVector
			}
			yield Candidate(device, receiver)
		}

	private def midiAvailable[T](it: =>T):Option[T]	=
		Catch.byType[MidiUnavailableException].in(it).toOption
}

trait MidiClient {
	// time is -1 (noTime) if not supported
	// TODO throws exceptions when the device is unplugged, right?
	def send(event:MidiEvent, time:Long):Io[Unit]
}
