package scaudio.interpolation

import scala.math._

object Sinc extends Interpolation {
	val wingSize	= 13
	
	//------------------------------------------------------------------------------
	
	def interpolate(buffer:Channel, frame:Double, pitch:Double):Float	= {
		val	apitch	= abs(pitch)
			 if (apitch == 0)	0
		else if (apitch < 1)	slower(buffer, frame, apitch)
		else					faster(buffer, frame, apitch)
	}
		
	/**
	upsampling
	-	pitch is lower than 1
	-	output has more samples
	-	output has higher sampling rate
	-	sinc zero crossings are aligned to input
	*/
	def slower(buffer:Channel, frame:Double, pitch:Double):Float	= {
		val ceiling	= ceil(frame)
		val index	= ceiling.toInt
		val fract	= ceiling - frame
		// if (fract == 0.0) return (buffer get index)
		val size	= wingSize
		
		/*
		// slow but clear
		val parts	= -size-1 to size map { pos => 
			windowedSinc(fract + pos) * (buffer get (index + pos)) 
		}
		parts.sum.toFloat
		*/
		
		/*
		// faster and using table, still doing too many calculations
		var pos	= -size-1
		var acc	= 0.0
		while (pos <= size) {
			val part	= sincFromTable(fract + pos) * (buffer get (index + pos))
			acc	= acc + part
			pos	= pos + 1
		}
		acc.toFloat
		*/
		
		// forward optimized
		var sampleIndex	= index - size - 1
		val sampleEnd	= index + size
		var tableIndex	= fract - size - 1
		var accumulator	= 0.0f
		while (sampleIndex <= sampleEnd) {
			accumulator	= accumulator + (sincFromTable(tableIndex).toFloat * (buffer get sampleIndex))
			sampleIndex	= sampleIndex + 1
			tableIndex	= tableIndex + 1
		}
		accumulator
	}
	
	/**
	downsampling
	-	pitch is higher than 1
	-	output has less samples
	-	output has lower sampling rate
	-	sinc zero crossings are aligned to output
	*/
	def faster(buffer:Channel, frame:Double, pitch:Double):Float	= {
		val ceiling	= ceil(frame)
		val index	= ceiling.toInt
		val fract	= ceiling - frame
		val size	= ceil(wingSize*pitch).toInt
		
		/*
		// slow but clear
		val parts	= -size-1 to size map { pos => 
			windowedSinc((fract + pos) / pitch) * (buffer get (index + pos)) 
		}
		(parts.sum / pitch).toFloat
		*/
		
		/*
		// faster and using table, still doing too many calculations
		val scl	= 1.0 / pitch
		var pos	= -size-1
		var acc	= 0.0
		while (pos <= size) {
			val part	= sincFromTable((fract + pos) * scl) * (buffer get (index + pos))
			acc	= acc + part
			pos	= pos + 1
		}
		(acc * scl).toFloat
		*/
		
		// forward optimized
		val scale		= 1.0 / pitch
		var sampleIndex	= index - size - 1
		val sampleEnd	= index + size
		var tableIndex	= (fract - size - 1) * scale
		var accumulator	= 0.0f
		while (sampleIndex <= sampleEnd) {
			accumulator	= accumulator + (sincFromTable(tableIndex).toFloat * (buffer get sampleIndex))
			sampleIndex	= sampleIndex + 1
			tableIndex	= tableIndex + scale
		}
		accumulator * scale.toFloat
	}
	
	//------------------------------------------------------------------------------
	
	val sincTableOversampling	= 512
	val sincTableSize			= wingSize * sincTableOversampling
	
	/** half sinc */
	val sincTable:Array[Float]	= {
		val out		= new Array[Float](sincTableSize)
		var i		= 0
		while (i < sincTableSize) {
			out(i)	= windowedSinc(i.toDouble / sincTableOversampling).toFloat
			i	= i + 1
		}
		out
	}
	
	def sincFromTable(x:Double):Double	= {
		// TODO include some overshot in the table to save the range check
		val index	= (abs(x) * sincTableOversampling).toInt
		if (index < sincTableSize)	sincTable(index)
		else						0.0
	}
		
	//------------------------------------------------------------------------------
		
	def windowedSinc(x:Double):Double	= 
			sinc(x) * blackman(x)
		
	def sinc(x:Double):Double	=
			if (x == 0.0)	1.0
			else			sin(Pi * x) / (Pi * x)
	
	def hanning(x:Double):Double	=
			if (abs(x) <= wingSize)	0.5 + 0.5 * cos(Pi * x / wingSize)
			else					0.0
			
	def blackman(x:Double):Double	=
			if (abs(x) <= wingSize)	0.42 + 0.5 * cos(Pi * x / wingSize) + 0.08 * cos(2 * Pi * x / wingSize)
			else					0.0
}
