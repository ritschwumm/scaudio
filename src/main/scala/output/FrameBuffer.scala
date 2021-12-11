package scaudio.output

final class FrameBuffer {
	var left:Float	= 0
	var right:Float	= 0

	inline def clear():Unit	= {
		left	= 0
		right	= 0
	}

	//------------------------------------------------------------------------------

	inline def set(left:Float, right:Float):Unit	= {
		this.left	= left
		this.right	= right
	}

	inline def add(left:Float, right:Float):Unit	= {
		this.left	+= left
		this.right	+= right
	}

	inline def mul(left:Float, right:Float):Unit	= {
		this.left	*= left
		this.right	*= right
	}

	//------------------------------------------------------------------------------

	inline def setValue(that:FrameBuffer):Unit	= {
		set(that.left, that.right)
	}

	inline def addValue(that:FrameBuffer):Unit	= {
		add(that.left, that.right)
	}

	inline def mulValue(that:FrameBuffer):Unit	= {
		mul(that.left, that.right)
	}

	//------------------------------------------------------------------------------

	inline def setAll(value:Float):Unit	= {
		set(value, value)
	}

	inline def addAll(value:Float):Unit	= {
		add(value, value)
	}

	inline def mulAll(value:Float):Unit	= {
		mul(value, value)
	}
}
