package scaudio.output

final class FrameBuffer {
	var left:Float	= 0
	var right:Float	= 0
	
	def clear() {
		left	= 0
		right	= 0
	}
	
	def set(left:Float, right:Float) {
		this.left	= left
		this.right	= right
	}
	
	def add(left:Float, right:Float) {
		this.left	+= left
		this.right	+= right
	}
	
	def mul(left:Float, right:Float) {
		this.left	*= left
		this.right	*= right
	}
}
