package scaudio.output

final class FrameBuffer {
	var left:Float	= 0
	var right:Float	= 0

	def clear():Unit	= {
		left	= 0
		right	= 0
	}

	//------------------------------------------------------------------------------

	def set(left:Float, right:Float):Unit	= {
		this.left	= left
		this.right	= right
	}

	def add(left:Float, right:Float):Unit	= {
		this.left	+= left
		this.right	+= right
	}

	def mul(left:Float, right:Float):Unit	= {
		this.left	*= left
		this.right	*= right
	}

	//------------------------------------------------------------------------------

	def setValue(that:FrameBuffer):Unit	= {
		set(that.left, that.right)
	}

	def addValue(that:FrameBuffer):Unit	= {
		add(that.left, that.right)
	}

	def mulValue(that:FrameBuffer):Unit	= {
		mul(that.left, that.right)
	}

	//------------------------------------------------------------------------------

	def setAll(value:Float):Unit	= {
		set(value, value)
	}

	def addAll(value:Float):Unit	= {
		add(value, value)
	}

	def mulAll(value:Float):Unit	= {
		mul(value, value)
	}
}
