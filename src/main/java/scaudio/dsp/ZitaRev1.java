package scaudio.dsp;

public final class ZitaRev1 {
	private float	fRec44[]	= new float[3];
	private float	fRec45[]	= new float[3];
	private float	fRec2[]		= new float[3];
	private float	fRec3[]		= new float[3];
	private float	fRec11[]	= new float[3];
	private float	fRec10[]	= new float[3];
	private float	fRec9[]		= new float[3];
	private float	fRec8[]		= new float[3];
	private float	fRec7[]		= new float[3];
	private float	fRec6[]		= new float[3];
	private float	fRec5[]		= new float[3];
	private float	fRec4[]		= new float[3];
	private float	fRec40[]	= new float[2];
	private float	fVec17[]	= new float[1024];
	private float	fVec16[]	= new float[16384];
	private float	fRec42[]	= new float[2];
	private float	fRec43[]	= new float[2];
	private float	fRec36[]	= new float[2];
	private float	fVec15[]	= new float[2048];
	private float	fVec14[]	= new float[16384];
	private float	fRec38[]	= new float[2];
	private float	fRec39[]	= new float[2];
	private float	fRec32[]	= new float[2];
	private float	fVec13[]	= new float[2048];
	private float	fVec12[]	= new float[8192];
	private float	fRec34[]	= new float[2];
	private float	fRec35[]	= new float[2];
	private float	fRec28[]	= new float[2];
	private float	fVec11[]	= new float[2048];
	private float	fVec10[]	= new float[16384];
	private float	fRec30[]	= new float[2];
	private float	fRec31[]	= new float[2];
	private float	fVec9[]		= new float[8192];
	private float	fRec24[]	= new float[2];
	private float	fVec8[]		= new float[1024];
	private float	fVec7[]		= new float[8192];
	private float	fRec26[]	= new float[2];
	private float	fRec27[]	= new float[2];
	private float	fRec20[]	= new float[2];
	private float	fVec6[]		= new float[2048];
	private float	fVec5[]		= new float[8192];
	private float	fRec22[]	= new float[2];
	private float	fRec23[]	= new float[2];
	private float	fRec16[]	= new float[2];
	private float	fVec4[]		= new float[2048];
	private float	fVec3[]		= new float[8192];
	private float	fRec18[]	= new float[2];
	private float	fRec19[]	= new float[2];
	private float	fRec12[]	= new float[2];
	private float	fVec2[]		= new float[1024];
	private float	fVec1[]		= new float[8192];
	private float	fRec14[]	= new float[2];
	private float	fRec15[]	= new float[2];
	private float	fRec1[]		= new float[2];
	private float	fVec0[]		= new float[8192];
	private float	fRec0[]		= new float[2];
	private float	fvslider0;
	private int		IOTA;
	private float	fvslider1;
	private float	fvslider2;
	private int		iConst0;
	private float	fConst1;
	private float	fvslider3;
	private float	fvslider4;
	private float	fvslider5;
	private float	fConst2;
	private float	fvslider6;
	private float	fConst3;
	private float	fConst4;
	private float	fvslider7;
	private float	fConst5;
	private float	fvslider8;
	private float	fvslider9;
	private float	fConst6;
	private float	fvslider10;
	private float	fConst7;
	private int		iConst8;
	private int		iConst9;
	private float	fConst10;
	private float	fConst11;
	private float	fConst12;
	private int		iConst13;
	private int		iConst14;
	private float	fConst15;
	private float	fConst16;
	private float	fConst17;
	private int		iConst18;
	private int		iConst19;
	private float	fConst20;
	private float	fConst21;
	private float	fConst22;
	private int		iConst23;
	private int		iConst24;
	private float	fConst25;
	private float	fConst26;
	private float	fConst27;
	private int		iConst28;
	private int		iConst29;
	private float	fConst30;
	private float	fConst31;
	private float	fConst32;
	private int		iConst33;
	private int		iConst34;
	private float	fConst35;
	private float	fConst36;
	private float	fConst37;
	private int		iConst38;
	private int		iConst39;
	private float	fConst40;
	private float	fConst41;
	private float	fConst42;
	private int		iConst43;
	private int		iConst44;

	public ZitaRev1(int samplingFreq) {
		init(samplingFreq);
	}
	
	/**
	In Delay
	Delay in ms before reverberation begins
	
	unit	ms
	init	60.0
	min		20.0
	max		100.0
	step	1.0
	*/
	public void setInDelay(float it) {
		this.fvslider6	= it;
	}
	
	// Decay Times in Bands
	
	/**
	LF X
	Crossover frequency (Hz) separating low and middle frequencies
	
	unit	Hz
	init	200.0
	min		50.0
	max		1000.0
	step	1.0
	*/
	public void setLFCrossover(float it) {
		this.fvslider10	= it;
	}
	
	/**
	Low RT60
	T60 = time (in seconds) to decay 60dB in low-frequency band
	
	unit	s
	init	3.0
	min		1.0
	max		8.0
	step	0.1
	*/
	public void setLowRT60(float it) {
		this.fvslider9	= it;
	}
	
	/**
	Mid RT60
	T60 = time (in seconds) to decay 60dB in middle band
			
	unit	s
	init	2.0
	min		1.0
	max		8.0
	step	0.1
	*/
	public void setMidRT60(float it) {
		this.fvslider7	= it;
	}
	
	/**
	HF Damping
	Frequency (Hz) at which the high-frequency T60 is half the middle-band's T60
	
	unit	Hz
	init	6000.0
	min		1500.0
	max		23520.0
	step	1.0
	*/
	public void setHFDamping(float it) {
		this.fvslider8	= it;
	}
	
	// RM Peaking Equalizer 1
	
	/**
	Eq1 Freq
	Center-frequency of second-order Regalia-Mitra peaking equalizer section 1
		
	unit	Hz
	init	315.0
	min		40.0
	max		2500.0
	step	1.0
	*/
	public void setEq1Freq(float it) {
		this.fvslider5	= it;
	}
	
	/**
	Eq1 Level
	Peak level in dB of second-order Regalia-Mitra peaking equalizer section 1
	
	unit	dB
	init	0.0
	min		-15.0
	max		15.0
	step	0.1
	*/
	public void setEq1Level(float it) {
		this.fvslider4	= it;
	}
	
	// RM Peaking Equalizer 2
	
	/**
	Eq2 Freq
	Center-frequency of second-order Regalia-Mitra peaking equalizer section 2
		
	unit	Hz
	init	315.0
	min		40.0
	max		2500.0
	step	1.0
	*/
	public void setEq2Freq(float it) {
		this.fvslider3	= it;
	}
	
	/**
	Eq2 Level
	Peak level in dB of second-order Regalia-Mitra peaking equalizer section 2
	
	unit	dB
	init	0.0
	min		-15.0
	max		15.0
	step	0.1
	*/
	public void setEq2Level(float it) {
		this.fvslider2	= it;
	}
	
	// Output
	
	/**
	Dry/Wet Mix
	
	unit	-1=dry, +1=wet
	init	0.0
	min		-1.0
	max		+1.0
	step	0.01
	*/
	public void setDryWetMix(float it) {
		this.fvslider1	= it;
	}
	
	/**
	Level
	Output scale factor
	
	unit	dB				
	init	-20.0
	min		-70.0
	max		40.0
	step	0.1
	*/
	public void setLevel(float it) {
		this.fvslider0	= it;
	}
	
	@SuppressWarnings("cast")
	public void init(int samplingFreq) {
		fvslider0 = (float) -20.;
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec0[i] = 0.f;

		}
		IOTA = 0;
		for (int i = 0; (i < 8192); i = (i + 1)) {
			fVec0[i] = 0.f;

		}
		fvslider1 = (float) 0.;
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec1[i] = 0.f;

		}
		fvslider2 = (float) 0.;
		iConst0 = java.lang.Math.min(192000,
				java.lang.Math.max(1, samplingFreq));
		fConst1 = (6.28319f / (float) iConst0);
		fvslider3 = (float) 315.;
		fvslider4 = (float) 0.;
		fvslider5 = (float) 315.;
		fConst2 = (0.001f * (float) iConst0);
		fvslider6 = (float) 60.;
		fConst3 = (float) java.lang.Math
				.floor((0.5f + (0.153129f * (float) iConst0)));
		fConst4 = ((0.f - (6.90776f * fConst3)) / (float) iConst0);
		fvslider7 = (float) 2.;
		fConst5 = (6.28319f / (float) iConst0);
		fvslider8 = (float) 6000.;
		fvslider9 = (float) 3.;
		fConst6 = (3.14159f / (float) iConst0);
		fvslider10 = (float) 200.;
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec15[i] = 0.f;

		}
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec14[i] = 0.f;

		}
		for (int i = 0; (i < 8192); i = (i + 1)) {
			fVec1[i] = 0.f;

		}
		fConst7 = (float) java.lang.Math
				.floor((0.5f + (0.020346f * (float) iConst0)));
		iConst8 = ((int) (fConst3 - fConst7) & 8191);
		for (int i = 0; (i < 1024); i = (i + 1)) {
			fVec2[i] = 0.f;

		}
		iConst9 = ((int) (fConst7 - 1.f) & 1023);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec12[i] = 0.f;

		}
		fConst10 = (float) java.lang.Math
				.floor((0.5f + (0.174713f * (float) iConst0)));
		fConst11 = ((0.f - (6.90776f * fConst10)) / (float) iConst0);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec19[i] = 0.f;

		}
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec18[i] = 0.f;

		}
		for (int i = 0; (i < 8192); i = (i + 1)) {
			fVec3[i] = 0.f;

		}
		fConst12 = (float) java.lang.Math
				.floor((0.5f + (0.022904f * (float) iConst0)));
		iConst13 = ((int) (fConst10 - fConst12) & 8191);
		for (int i = 0; (i < 2048); i = (i + 1)) {
			fVec4[i] = 0.f;

		}
		iConst14 = ((int) (fConst12 - 1.f) & 2047);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec16[i] = 0.f;

		}
		fConst15 = (float) java.lang.Math
				.floor((0.5f + (0.127837f * (float) iConst0)));
		fConst16 = ((0.f - (6.90776f * fConst15)) / (float) iConst0);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec23[i] = 0.f;

		}
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec22[i] = 0.f;

		}
		for (int i = 0; (i < 8192); i = (i + 1)) {
			fVec5[i] = 0.f;

		}
		fConst17 = (float) java.lang.Math
				.floor((0.5f + (0.031604f * (float) iConst0)));
		iConst18 = ((int) (fConst15 - fConst17) & 8191);
		for (int i = 0; (i < 2048); i = (i + 1)) {
			fVec6[i] = 0.f;

		}
		iConst19 = ((int) (fConst17 - 1.f) & 2047);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec20[i] = 0.f;

		}
		fConst20 = (float) java.lang.Math
				.floor((0.5f + (0.125f * (float) iConst0)));
		fConst21 = ((0.f - (6.90776f * fConst20)) / (float) iConst0);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec27[i] = 0.f;

		}
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec26[i] = 0.f;

		}
		for (int i = 0; (i < 8192); i = (i + 1)) {
			fVec7[i] = 0.f;

		}
		fConst22 = (float) java.lang.Math
				.floor((0.5f + (0.013458f * (float) iConst0)));
		iConst23 = ((int) (fConst20 - fConst22) & 8191);
		for (int i = 0; (i < 1024); i = (i + 1)) {
			fVec8[i] = 0.f;

		}
		iConst24 = ((int) (fConst22 - 1.f) & 1023);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec24[i] = 0.f;

		}
		for (int i = 0; (i < 8192); i = (i + 1)) {
			fVec9[i] = 0.f;

		}
		fConst25 = (float) java.lang.Math
				.floor((0.5f + (0.210389f * (float) iConst0)));
		fConst26 = ((0.f - (6.90776f * fConst25)) / (float) iConst0);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec31[i] = 0.f;

		}
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec30[i] = 0.f;

		}
		for (int i = 0; (i < 16384); i = (i + 1)) {
			fVec10[i] = 0.f;

		}
		fConst27 = (float) java.lang.Math
				.floor((0.5f + (0.024421f * (float) iConst0)));
		iConst28 = ((int) (fConst25 - fConst27) & 16383);
		for (int i = 0; (i < 2048); i = (i + 1)) {
			fVec11[i] = 0.f;

		}
		iConst29 = ((int) (fConst27 - 1.f) & 2047);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec28[i] = 0.f;

		}
		fConst30 = (float) java.lang.Math
				.floor((0.5f + (0.192303f * (float) iConst0)));
		fConst31 = ((0.f - (6.90776f * fConst30)) / (float) iConst0);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec35[i] = 0.f;

		}
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec34[i] = 0.f;

		}
		for (int i = 0; (i < 8192); i = (i + 1)) {
			fVec12[i] = 0.f;

		}
		fConst32 = (float) java.lang.Math
				.floor((0.5f + (0.029291f * (float) iConst0)));
		iConst33 = ((int) (fConst30 - fConst32) & 8191);
		for (int i = 0; (i < 2048); i = (i + 1)) {
			fVec13[i] = 0.f;

		}
		iConst34 = ((int) (fConst32 - 1.f) & 2047);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec32[i] = 0.f;

		}
		fConst35 = (float) java.lang.Math
				.floor((0.5f + (0.256891f * (float) iConst0)));
		fConst36 = ((0.f - (6.90776f * fConst35)) / (float) iConst0);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec39[i] = 0.f;

		}
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec38[i] = 0.f;

		}
		for (int i = 0; (i < 16384); i = (i + 1)) {
			fVec14[i] = 0.f;

		}
		fConst37 = (float) java.lang.Math
				.floor((0.5f + (0.027333f * (float) iConst0)));
		iConst38 = ((int) (fConst35 - fConst37) & 16383);
		for (int i = 0; (i < 2048); i = (i + 1)) {
			fVec15[i] = 0.f;

		}
		iConst39 = ((int) (fConst37 - 1.f) & 2047);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec36[i] = 0.f;

		}
		fConst40 = (float) java.lang.Math
				.floor((0.5f + (0.219991f * (float) iConst0)));
		fConst41 = ((0.f - (6.90776f * fConst40)) / (float) iConst0);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec43[i] = 0.f;

		}
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec42[i] = 0.f;

		}
		for (int i = 0; (i < 16384); i = (i + 1)) {
			fVec16[i] = 0.f;

		}
		fConst42 = (float) java.lang.Math
				.floor((0.5f + (0.019123f * (float) iConst0)));
		iConst43 = ((int) (fConst40 - fConst42) & 16383);
		for (int i = 0; (i < 1024); i = (i + 1)) {
			fVec17[i] = 0.f;

		}
		iConst44 = ((int) (fConst42 - 1.f) & 1023);
		for (int i = 0; (i < 2); i = (i + 1)) {
			fRec40[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec4[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec5[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec6[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec7[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec8[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec9[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec10[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec11[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec3[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec2[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec45[i] = 0.f;

		}
		for (int i = 0; (i < 3); i = (i + 1)) {
			fRec44[i] = 0.f;

		}
	}

	public void compute(int count, float[][] inputs, float[][] outputs) {
		float[] input0 = inputs[0];
		float[] input1 = inputs[1];
		float[] output0 = outputs[0];
		float[] output1 = outputs[1];
		float fSlow0 = (0.001f * (float) java.lang.Math.pow(10.f,
				(0.05f * fvslider0)));
		float fSlow1 = (0.001f * fvslider1);
		float fSlow2 = (float) java.lang.Math.pow(10.f, (0.05f * fvslider2));
		float fSlow3 = fvslider3;
		float fSlow4 = (fConst1 * (fSlow3 / (float) java.lang.Math
				.sqrt(java.lang.Math.max(0.f, fSlow2))));
		float fSlow5 = ((1.f - fSlow4) / (1.f + fSlow4));
		float fSlow6 = ((1.f + fSlow5) * (0.f - (float) java.lang.Math
				.cos((fConst1 * fSlow3))));
		float fSlow7 = (float) java.lang.Math.pow(10.f, (0.05f * fvslider4));
		float fSlow8 = fvslider5;
		float fSlow9 = (fConst1 * (fSlow8 / (float) java.lang.Math
				.sqrt(java.lang.Math.max(0.f, fSlow7))));
		float fSlow10 = ((1.f - fSlow9) / (1.f + fSlow9));
		float fSlow11 = ((1.f + fSlow10) * (0.f - (float) java.lang.Math
				.cos((fConst1 * fSlow8))));
		int iSlow12 = ((int) (fConst2 * fvslider6) & 8191);
		float fSlow13 = fvslider7;
		float fSlow14 = (float) java.lang.Math.exp((fConst4 / fSlow13));
		float fSlow15 = (float) java.lang.Math.cos((fConst5 * fvslider8));
		float fSlow16 = faustpower2_f(fSlow14);
		float fSlow17 = (1.f - (fSlow15 * fSlow16));
		float fSlow18 = (1.f - fSlow16);
		float fSlow19 = (float) java.lang.Math.sqrt(java.lang.Math.max(0.f,
				((faustpower2_f(fSlow17) / faustpower2_f(fSlow18)) - 1.f)));
		float fSlow20 = (fSlow17 / fSlow18);
		float fSlow21 = (fSlow14 * ((1.f + fSlow19) - fSlow20));
		float fSlow22 = fvslider9;
		float fSlow23 = (((float) java.lang.Math.exp((fConst4 / fSlow22)) / fSlow14) - 1.f);
		float fSlow24 = (1.f / (float) java.lang.Math
				.tan((fConst6 * fvslider10)));
		float fSlow25 = (1.f + fSlow24);
		float fSlow26 = (1.f / fSlow25);
		float fSlow27 = (0.f - ((1.f - fSlow24) / fSlow25));
		float fSlow28 = (fSlow20 - fSlow19);
		float fSlow29 = (float) java.lang.Math.exp((fConst11 / fSlow13));
		float fSlow30 = faustpower2_f(fSlow29);
		float fSlow31 = (1.f - (fSlow15 * fSlow30));
		float fSlow32 = (1.f - fSlow30);
		float fSlow33 = (float) java.lang.Math.sqrt(java.lang.Math.max(0.f,
				((faustpower2_f(fSlow31) / faustpower2_f(fSlow32)) - 1.f)));
		float fSlow34 = (fSlow31 / fSlow32);
		float fSlow35 = (fSlow29 * ((1.f + fSlow33) - fSlow34));
		float fSlow36 = (((float) java.lang.Math.exp((fConst11 / fSlow22)) / fSlow29) - 1.f);
		float fSlow37 = (fSlow34 - fSlow33);
		float fSlow38 = (float) java.lang.Math.exp((fConst16 / fSlow13));
		float fSlow39 = faustpower2_f(fSlow38);
		float fSlow40 = (1.f - (fSlow15 * fSlow39));
		float fSlow41 = (1.f - fSlow39);
		float fSlow42 = (float) java.lang.Math.sqrt(java.lang.Math.max(0.f,
				((faustpower2_f(fSlow40) / faustpower2_f(fSlow41)) - 1.f)));
		float fSlow43 = (fSlow40 / fSlow41);
		float fSlow44 = (fSlow38 * ((1.f + fSlow42) - fSlow43));
		float fSlow45 = (((float) java.lang.Math.exp((fConst16 / fSlow22)) / fSlow38) - 1.f);
		float fSlow46 = (fSlow43 - fSlow42);
		float fSlow47 = (float) java.lang.Math.exp((fConst21 / fSlow13));
		float fSlow48 = faustpower2_f(fSlow47);
		float fSlow49 = (1.f - (fSlow15 * fSlow48));
		float fSlow50 = (1.f - fSlow48);
		float fSlow51 = (float) java.lang.Math.sqrt(java.lang.Math.max(0.f,
				((faustpower2_f(fSlow49) / faustpower2_f(fSlow50)) - 1.f)));
		float fSlow52 = (fSlow49 / fSlow50);
		float fSlow53 = (fSlow47 * ((1.f + fSlow51) - fSlow52));
		float fSlow54 = (((float) java.lang.Math.exp((fConst21 / fSlow22)) / fSlow47) - 1.f);
		float fSlow55 = (fSlow52 - fSlow51);
		float fSlow56 = (float) java.lang.Math.exp((fConst26 / fSlow13));
		float fSlow57 = faustpower2_f(fSlow56);
		float fSlow58 = (1.f - (fSlow15 * fSlow57));
		float fSlow59 = (1.f - fSlow57);
		float fSlow60 = (float) java.lang.Math.sqrt(java.lang.Math.max(0.f,
				((faustpower2_f(fSlow58) / faustpower2_f(fSlow59)) - 1.f)));
		float fSlow61 = (fSlow58 / fSlow59);
		float fSlow62 = (fSlow56 * ((1.f + fSlow60) - fSlow61));
		float fSlow63 = (((float) java.lang.Math.exp((fConst26 / fSlow22)) / fSlow56) - 1.f);
		float fSlow64 = (fSlow61 - fSlow60);
		float fSlow65 = (float) java.lang.Math.exp((fConst31 / fSlow13));
		float fSlow66 = faustpower2_f(fSlow65);
		float fSlow67 = (1.f - (fSlow15 * fSlow66));
		float fSlow68 = (1.f - fSlow66);
		float fSlow69 = (float) java.lang.Math.sqrt(java.lang.Math.max(0.f,
				((faustpower2_f(fSlow67) / faustpower2_f(fSlow68)) - 1.f)));
		float fSlow70 = (fSlow67 / fSlow68);
		float fSlow71 = (fSlow65 * ((1.f + fSlow69) - fSlow70));
		float fSlow72 = (((float) java.lang.Math.exp((fConst31 / fSlow22)) / fSlow65) - 1.f);
		float fSlow73 = (fSlow70 - fSlow69);
		float fSlow74 = (float) java.lang.Math.exp((fConst36 / fSlow13));
		float fSlow75 = faustpower2_f(fSlow74);
		float fSlow76 = (1.f - (fSlow15 * fSlow75));
		float fSlow77 = (1.f - fSlow75);
		float fSlow78 = (float) java.lang.Math.sqrt(java.lang.Math.max(0.f,
				((faustpower2_f(fSlow76) / faustpower2_f(fSlow77)) - 1.f)));
		float fSlow79 = (fSlow76 / fSlow77);
		float fSlow80 = (fSlow74 * ((1.f + fSlow78) - fSlow79));
		float fSlow81 = (((float) java.lang.Math.exp((fConst36 / fSlow22)) / fSlow74) - 1.f);
		float fSlow82 = (fSlow79 - fSlow78);
		float fSlow83 = (float) java.lang.Math.exp((fConst41 / fSlow13));
		float fSlow84 = faustpower2_f(fSlow83);
		float fSlow85 = (1.f - (fSlow84 * fSlow15));
		float fSlow86 = (1.f - fSlow84);
		float fSlow87 = (float) java.lang.Math.sqrt(java.lang.Math.max(0.f,
				((faustpower2_f(fSlow85) / faustpower2_f(fSlow86)) - 1.f)));
		float fSlow88 = (fSlow85 / fSlow86);
		float fSlow89 = (fSlow83 * ((1.f + fSlow87) - fSlow88));
		float fSlow90 = (((float) java.lang.Math.exp((fConst41 / fSlow22)) / fSlow83) - 1.f);
		float fSlow91 = (fSlow88 - fSlow87);
		for (int i = 0; (i < count); i = (i + 1)) {
			fRec0[0] = (fSlow0 + (0.999f * fRec0[1]));
			float fTemp0 = input0[i];
			fVec0[(IOTA & 8191)] = fTemp0;
			fRec1[0] = (fSlow1 + (0.999f * fRec1[1]));
			float fTemp1 = (1.f + fRec1[0]);
			float fTemp2 = (1.f - (0.5f * fTemp1));
			float fTemp3 = (fSlow6 * fRec2[1]);
			float fTemp4 = (fSlow11 * fRec3[1]);
			float fTemp5 = (0.3f * fVec0[((IOTA - iSlow12) & 8191)]);
			fRec15[0] = ((fSlow26 * (fRec4[1] + fRec4[2])) + (fSlow27 * fRec15[1]));
			fRec14[0] = ((fSlow21 * (fRec4[1] + (fSlow23 * fRec15[0]))) + (fSlow28 * fRec14[1]));
			fVec1[(IOTA & 8191)] = (1e-20f + (0.353553f * fRec14[0]));
			float fTemp6 = ((fTemp5 + fVec1[((IOTA - iConst8) & 8191)]) - (0.6f * fRec12[1]));
			fVec2[(IOTA & 1023)] = fTemp6;
			fRec12[0] = fVec2[((IOTA - iConst9) & 1023)];
			float fRec13 = (0.6f * fTemp6);
			fRec19[0] = ((fSlow26 * (fRec8[1] + fRec8[2])) + (fSlow27 * fRec19[1]));
			fRec18[0] = ((fSlow35 * (fRec8[1] + (fSlow36 * fRec19[0]))) + (fSlow37 * fRec18[1]));
			fVec3[(IOTA & 8191)] = (1e-20f + (0.353553f * fRec18[0]));
			float fTemp7 = ((fTemp5 + fVec3[((IOTA - iConst13) & 8191)]) - (0.6f * fRec16[1]));
			fVec4[(IOTA & 2047)] = fTemp7;
			fRec16[0] = fVec4[((IOTA - iConst14) & 2047)];
			float fRec17 = (0.6f * fTemp7);
			fRec23[0] = ((fSlow26 * (fRec6[1] + fRec6[2])) + (fSlow27 * fRec23[1]));
			fRec22[0] = ((fSlow44 * (fRec6[1] + (fSlow45 * fRec23[0]))) + (fSlow46 * fRec22[1]));
			fVec5[(IOTA & 8191)] = (1e-20f + (0.353553f * fRec22[0]));
			float fTemp8 = (fVec5[((IOTA - iConst18) & 8191)] - (fTemp5 + (0.6f * fRec20[1])));
			fVec6[(IOTA & 2047)] = fTemp8;
			fRec20[0] = fVec6[((IOTA - iConst19) & 2047)];
			float fRec21 = (0.6f * fTemp8);
			fRec27[0] = ((fSlow26 * (fRec10[1] + fRec10[2])) + (fSlow27 * fRec27[1]));
			fRec26[0] = ((fSlow53 * (fRec10[1] + (fSlow54 * fRec27[0]))) + (fSlow55 * fRec26[1]));
			fVec7[(IOTA & 8191)] = (1e-20f + (0.353553f * fRec26[0]));
			float fTemp9 = (fVec7[((IOTA - iConst23) & 8191)] - (fTemp5 + (0.6f * fRec24[1])));
			fVec8[(IOTA & 1023)] = fTemp9;
			fRec24[0] = fVec8[((IOTA - iConst24) & 1023)];
			float fRec25 = (0.6f * fTemp9);
			float fTemp10 = input1[i];
			fVec9[(IOTA & 8191)] = fTemp10;
			float fTemp11 = (0.3f * fVec9[((IOTA - iSlow12) & 8191)]);
			fRec31[0] = ((fSlow26 * (fRec5[1] + fRec5[2])) + (fSlow27 * fRec31[1]));
			fRec30[0] = ((fSlow62 * (fRec5[1] + (fSlow63 * fRec31[0]))) + (fSlow64 * fRec30[1]));
			fVec10[(IOTA & 16383)] = (1e-20f + (0.353553f * fRec30[0]));
			float fTemp12 = ((fTemp11 + fVec10[((IOTA - iConst28) & 16383)]) + (0.6f * fRec28[1]));
			fVec11[(IOTA & 2047)] = fTemp12;
			fRec28[0] = fVec11[((IOTA - iConst29) & 2047)];
			float fRec29 = (0.f - (0.6f * fTemp12));
			fRec35[0] = ((fSlow26 * (fRec9[1] + fRec9[2])) + (fSlow27 * fRec35[1]));
			fRec34[0] = ((fSlow71 * (fRec9[1] + (fSlow72 * fRec35[0]))) + (fSlow73 * fRec34[1]));
			fVec12[(IOTA & 8191)] = (1e-20f + (0.353553f * fRec34[0]));
			float fTemp13 = ((fTemp11 + fVec12[((IOTA - iConst33) & 8191)]) + (0.6f * fRec32[1]));
			fVec13[(IOTA & 2047)] = fTemp13;
			fRec32[0] = fVec13[((IOTA - iConst34) & 2047)];
			float fRec33 = (0.f - (0.6f * fTemp13));
			fRec39[0] = ((fSlow26 * (fRec7[1] + fRec7[2])) + (fSlow27 * fRec39[1]));
			fRec38[0] = ((fSlow80 * (fRec7[1] + (fSlow81 * fRec39[0]))) + (fSlow82 * fRec38[1]));
			fVec14[(IOTA & 16383)] = (1e-20f + (0.353553f * fRec38[0]));
			float fTemp14 = ((fVec14[((IOTA - iConst38) & 16383)] + (0.6f * fRec36[1])) - fTemp11);
			fVec15[(IOTA & 2047)] = fTemp14;
			fRec36[0] = fVec15[((IOTA - iConst39) & 2047)];
			float fRec37 = (0.f - (0.6f * fTemp14));
			fRec43[0] = ((fSlow26 * (fRec11[1] + fRec11[2])) + (fSlow27 * fRec43[1]));
			fRec42[0] = ((fSlow89 * (fRec11[1] + (fSlow90 * fRec43[0]))) + (fSlow91 * fRec42[1]));
			fVec16[(IOTA & 16383)] = (1e-20f + (0.353553f * fRec42[0]));
			float fTemp15 = ((fVec16[((IOTA - iConst43) & 16383)] + (0.6f * fRec40[1])) - fTemp11);
			fVec17[(IOTA & 1023)] = fTemp15;
			fRec40[0] = fVec17[((IOTA - iConst44) & 1023)];
			float fRec41 = (0.f - (0.6f * fTemp15));
			float fTemp16 = (fRec13 + fRec17);
			float fTemp17 = (fRec25 + (fRec21 + fTemp16));
			fRec4[0] = (fRec12[1] + (fRec16[1] + (fRec20[1] + (fRec24[1] + (fRec28[1] + (fRec32[1] + (fRec36[1] + (fRec40[1] + (fRec41 + (fRec37 + (fRec33 + (fRec29 + fTemp17))))))))))));
			fRec5[0] = (0.f - ((fRec28[1] + (fRec32[1] + (fRec36[1] + (fRec40[1] + (fRec41 + (fRec37 + (fRec29 + fRec33))))))) - (fRec12[1] + (fRec16[1] + (fRec20[1] + (fRec24[1] + fTemp17))))));
			float fTemp18 = (fRec21 + fRec25);
			fRec6[0] = (0.f - ((fRec20[1] + (fRec24[1] + (fRec36[1] + (fRec40[1] + (fRec41 + (fRec37 + fTemp18)))))) - (fRec12[1] + (fRec16[1] + (fRec28[1] + (fRec32[1] + (fRec33 + (fRec29 + fTemp16))))))));
			fRec7[0] = (0.f - ((fRec20[1] + (fRec24[1] + (fRec28[1] + (fRec32[1] + (fRec33 + (fRec29 + fTemp18)))))) - (fRec12[1] + (fRec16[1] + (fRec36[1] + (fRec40[1] + (fRec41 + (fRec37 + fTemp16))))))));
			float fTemp19 = (fRec17 + fRec25);
			float fTemp20 = (fRec13 + fRec21);
			fRec8[0] = (0.f - ((fRec16[1] + (fRec24[1] + (fRec32[1] + (fRec40[1] + (fRec41 + (fRec33 + fTemp19)))))) - (fRec12[1] + (fRec20[1] + (fRec28[1] + (fRec36[1] + (fRec37 + (fRec29 + fTemp20))))))));
			fRec9[0] = (0.f - ((fRec16[1] + (fRec24[1] + (fRec28[1] + (fRec36[1] + (fRec37 + (fRec29 + fTemp19)))))) - (fRec12[1] + (fRec20[1] + (fRec32[1] + (fRec40[1] + (fRec41 + (fRec33 + fTemp20))))))));
			float fTemp21 = (fRec17 + fRec21);
			float fTemp22 = (fRec13 + fRec25);
			fRec10[0] = (0.f - ((fRec16[1] + (fRec20[1] + (fRec32[1] + (fRec36[1] + (fRec37 + (fRec33 + fTemp21)))))) - (fRec12[1] + (fRec24[1] + (fRec28[1] + (fRec40[1] + (fRec41 + (fRec29 + fTemp22))))))));
			fRec11[0] = (0.f - ((fRec16[1] + (fRec20[1] + (fRec28[1] + (fRec40[1] + (fRec41 + (fRec29 + fTemp21)))))) - (fRec12[1] + (fRec24[1] + (fRec32[1] + (fRec36[1] + (fRec37 + (fRec33 + fTemp22))))))));
			float fTemp23 = (0.37f * (fRec5[0] + fRec6[0]));
			fRec3[0] = (0.f - (((fSlow10 * fRec3[2]) + fTemp4) - fTemp23));
			float fTemp24 = (fSlow10 * fRec3[0]);
			float fTemp25 = (0.5f * ((fSlow7 * ((fTemp24 + (fRec3[2] + fTemp4)) - fTemp23)) + (fTemp24 + (fTemp4 + (fTemp23 + fRec3[2])))));
			fRec2[0] = (0.f - (((fSlow5 * fRec2[2]) + fTemp3) - fTemp25));
			float fTemp26 = (fSlow5 * fRec2[0]);
			output0[i] = (fRec0[0] * ((fTemp0 * fTemp2) + (0.25f * (fTemp1 * ((fSlow2 * ((fTemp3 + (fRec2[2] + fTemp26)) - fTemp25)) + (fTemp3 + (fTemp26 + (fTemp25 + fRec2[2]))))))));
			float fTemp27 = (fSlow6 * fRec44[1]);
			float fTemp28 = (fSlow11 * fRec45[1]);
			float fTemp29 = (0.37f * (fRec5[0] - fRec6[0]));
			fRec45[0] = (0.f - (((fSlow10 * fRec45[2]) + fTemp28) - fTemp29));
			float fTemp30 = (fSlow10 * fRec45[0]);
			float fTemp31 = (0.5f * ((fSlow7 * ((fTemp30 + (fRec45[2] + fTemp28)) - fTemp29)) + (fTemp30 + (fTemp28 + (fTemp29 + fRec45[2])))));
			fRec44[0] = (0.f - (((fSlow5 * fRec44[2]) + fTemp27) - fTemp31));
			float fTemp32 = (fSlow5 * fRec44[0]);
			output1[i] = (fRec0[0] * ((fTemp10 * fTemp2) + (0.25f * (fTemp1 * ((fSlow2 * ((fTemp32 + (fRec44[2] + fTemp27)) - fTemp31)) + (fTemp32 + (fTemp27 + (fTemp31 + fRec44[2]))))))));
			fRec0[1] = fRec0[0];
			IOTA = (IOTA + 1);
			fRec1[1] = fRec1[0];
			fRec15[1] = fRec15[0];
			fRec14[1] = fRec14[0];
			fRec12[1] = fRec12[0];
			fRec19[1] = fRec19[0];
			fRec18[1] = fRec18[0];
			fRec16[1] = fRec16[0];
			fRec23[1] = fRec23[0];
			fRec22[1] = fRec22[0];
			fRec20[1] = fRec20[0];
			fRec27[1] = fRec27[0];
			fRec26[1] = fRec26[0];
			fRec24[1] = fRec24[0];
			fRec31[1] = fRec31[0];
			fRec30[1] = fRec30[0];
			fRec28[1] = fRec28[0];
			fRec35[1] = fRec35[0];
			fRec34[1] = fRec34[0];
			fRec32[1] = fRec32[0];
			fRec39[1] = fRec39[0];
			fRec38[1] = fRec38[0];
			fRec36[1] = fRec36[0];
			fRec43[1] = fRec43[0];
			fRec42[1] = fRec42[0];
			fRec40[1] = fRec40[0];
			fRec4[2] = fRec4[1];
			fRec4[1] = fRec4[0];
			fRec5[2] = fRec5[1];
			fRec5[1] = fRec5[0];
			fRec6[2] = fRec6[1];
			fRec6[1] = fRec6[0];
			fRec7[2] = fRec7[1];
			fRec7[1] = fRec7[0];
			fRec8[2] = fRec8[1];
			fRec8[1] = fRec8[0];
			fRec9[2] = fRec9[1];
			fRec9[1] = fRec9[0];
			fRec10[2] = fRec10[1];
			fRec10[1] = fRec10[0];
			fRec11[2] = fRec11[1];
			fRec11[1] = fRec11[0];
			fRec3[2] = fRec3[1];
			fRec3[1] = fRec3[0];
			fRec2[2] = fRec2[1];
			fRec2[1] = fRec2[0];
			fRec45[2] = fRec45[1];
			fRec45[1] = fRec45[0];
			fRec44[2] = fRec44[1];
			fRec44[1] = fRec44[0];
		}
	}

	private float faustpower2_f(float value) {
		return (value * value);
	}
}
