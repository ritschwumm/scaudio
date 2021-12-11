package scaudio.midi

import javax.sound.midi.{MidiEvent as _, *}

import scutil.lang.*

object MidiInfo {
	@SuppressWarnings(Array("org.wartremover.warts.ToString"))
	def main(args:Array[String]):Unit	= {
		for {
			deviceInfo	<- MidiSystem.getMidiDeviceInfo
			device		<- midiAvailable(MidiSystem getMidiDevice deviceInfo)
		} {
			println("-------------------------------")

			println("device:             " + device.toString)
			println("getMaxTransmitters: " + device.getMaxTransmitters.toString)
			println("getMaxReceivers:    " + device.getMaxReceivers.toString)

			println("deviceInfo:  " + deviceInfo.toString)
			println("name:        " + deviceInfo.getName)
			println("description: " + deviceInfo.getDescription)
			println("vendor:      " + deviceInfo.getVendor)
			println("version:     " + deviceInfo.getVersion)
		}
	}

	private def midiAvailable[T](it: =>T):Option[T]	=
		Catch.byType[MidiUnavailableException].in(it).toOption
}
