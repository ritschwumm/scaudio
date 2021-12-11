package scaudio.format

import minitest.*

import scutil.lang.implicits.*

object FormatTest extends SimpleTestSuite {
	test("AudioFormat_S2LE should work") {
		assertEquals(
			 AudioFormat_S2LE decode -32768,
			 -1f
		)
	}
	test("AudioFormat_S2LE should work") {
		assertEquals(
			 AudioFormat_S2LE decode 0,
			 0f
		)
	}
	test("AudioFormat_S2LE should work") {
		assertEquals(
			 AudioFormat_S2LE decode 16384,
			 0.5f
		)
	}
	// 32768

	test("AudioFormat_S2LE should work") {
		assertEquals(
			 AudioFormat_S2LE encode -2f,
			 -32768
		)
	}
	test("AudioFormat_S2LE should work") {
		assertEquals(
			 AudioFormat_S2LE encode -1f,
			 -32768
		)
	}
	test("AudioFormat_S2LE should work") {
		assertEquals(
			 AudioFormat_S2LE encode 0f,
			 0
		)
	}
	test("AudioFormat_S2LE should work") {
		assertEquals(
			 AudioFormat_S2LE encode 1f,
			 +32767.toShort
		)
	}
	test("AudioFormat_S2LE should work") {
		assertEquals(
			 AudioFormat_S2LE encode 2f,
			 +32767.toShort
		)
	}

	//------------------------------------------------------------------------------

	test("AudioFormat_U1 should work") {
		assertEquals(
			 AudioFormat_U1 decode 0,
			 -1f
		)
	}
	test("AudioFormat_U1 should work") {
		assertEquals(
			 AudioFormat_U1 decode 128.toByte,
			 0f
		)
	}
	test("AudioFormat_U1 should work") {
		assertEquals(
			 AudioFormat_U1 decode 192.toByte,
			 0.5f
		)
	}
	// u255

	test("AudioFormat_U1 should work") {
		assertEquals(
			 AudioFormat_U1 encode -2f,
			 0
		)
	}
	test("AudioFormat_U1 should work") {
		assertEquals(
			 AudioFormat_U1 encode -1f,
			 0
		)
	}
	test("AudioFormat_U1 should work") {
		assertEquals(
			 AudioFormat_U1 encode 0f,
			 128.toByte
		)
	}
	test("AudioFormat_U1 should work") {
		assertEquals(
			 AudioFormat_U1 encode 0.5f,
			 192.toByte
		)
	}
	test("AudioFormat_U1 should work") {
		assertEquals(
			 AudioFormat_U1 encode 1f,
			 255.toByte
		)
	}
	test("AudioFormat_U1 should work") {
		assertEquals(
			 AudioFormat_U1 encode 2f,
			 255.toByte
		)
	}

	//------------------------------------------------------------------------------

	test("AudioFormat_S3LE should work") {
		assertEquals(
			 AudioFormat_S3LE.toInt(byte"56", byte"34", byte"12"),
			 int"00123456"
		)
	}
	test("AudioFormat_S3LE should work") {
		assertEquals(
			 AudioFormat_S3LE.toInt(byte"ab", byte"cd", byte"ef"),
			 int"ffefcdab"
		)
	}

	test("AudioFormat_S3LE should work") {
		assertEquals(
			 AudioFormat_S3LE.decode(byte"00", byte"00", byte"00"),
			 0f
		)
	}
	test("AudioFormat_S3LE should work") {
		assertEquals(
			 AudioFormat_S3LE.decode(byte"00", byte"00", byte"80"),
			 -1f
		)
	}
	test("AudioFormat_S3LE should work") {
		assertEquals(
			 AudioFormat_S3LE.decode(byte"00", byte"00", byte"40"),
			 0.5f
		)
	}
}
