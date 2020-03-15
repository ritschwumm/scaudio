package scaudio.format

import org.specs2.mutable._

import scutil.lang.implicits._

class FormatTest extends Specification {
	"AudioFormat_S2LE" should {
		"decode -32768 to -1f" in {
			 AudioFormat_S2LE decode -32768 mustEqual -1f
		}
		"decode 0 to 0f" in {
			 AudioFormat_S2LE decode 0 mustEqual 0f
		}
		"decode 16384 to 0.5f" in {
			 AudioFormat_S2LE decode 16384 mustEqual 0.5f
		}
		// 32768

		"encode -2f to -32768 because it is clamped" in {
			 AudioFormat_S2LE encode -2f mustEqual -32768
		}
		"encode -1f to -32768" in {
			 AudioFormat_S2LE encode -1f mustEqual -32768
		}
		"encode 0f to 0" in {
			 AudioFormat_S2LE encode 0f mustEqual 0
		}
		"encode 1f to +32767 because it is clamped" in {
			 AudioFormat_S2LE encode 1f mustEqual +32767.toShort
		}
		"encode 2f to +32767 because it is clamped" in {
			 AudioFormat_S2LE encode 2f mustEqual +32767.toShort
		}
	}

	"AudioFormat_U1" should {
		"decode u0 to -1f" in {
			 AudioFormat_U1 decode 0 mustEqual -1f
		}
		"decode u128 to 0f" in {
			 AudioFormat_U1 decode 128.toByte mustEqual 0f
		}
		"decode u192 to 0.5f" in {
			 AudioFormat_U1 decode 192.toByte mustEqual 0.5f
		}
		// u255

		"encode -2f to u0 because it is clamped" in {
			 AudioFormat_U1 encode -2f mustEqual 0
		}
		"encode -1f to u0" in {
			 AudioFormat_U1 encode -1f mustEqual 0
		}
		"encode 0f to u128" in {
			 AudioFormat_U1 encode 0f mustEqual 128.toByte
		}
		"encode 0.5f to u192" in {
			 AudioFormat_U1 encode 0.5f mustEqual 192.toByte
		}
		"encode 1f to u255 because it is clamped" in {
			 AudioFormat_U1 encode 1f mustEqual 255.toByte
		}
		"encode 2f to u255 because it is clamped" in {
			 AudioFormat_U1 encode 2f mustEqual 255.toByte
		}
	}

	"AudioFormat_S3LE" should {
		"see 56 34 12 as 0x00123456" in {
			 AudioFormat_S3LE toInt (byte"56", byte"34", byte"12") mustEqual int"00123456"
		}
		"see ab cd ef as 0xffefcdab" in {
			 AudioFormat_S3LE toInt (byte"ab", byte"cd", byte"ef") mustEqual int"ffefcdab"
		}

		"decode 0x000000 to 0f" in {
			 AudioFormat_S3LE decode (byte"00", byte"00", byte"00") mustEqual 0f
		}
		"decode 0x800000 to -1f" in {
			 AudioFormat_S3LE decode (byte"00", byte"00", byte"80") mustEqual -1f
		}
		"decode 0x400000 to 0.5f" in {
			 AudioFormat_S3LE decode (byte"00", byte"00", byte"40") mustEqual 0.5f
		}
	}
}
