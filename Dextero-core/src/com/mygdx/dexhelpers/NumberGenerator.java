package com.mygdx.dexhelpers;

import java.util.Random;



public class NumberGenerator {
	
	private Random indexGenerator;
	
	public NumberGenerator(){	
		indexGenerator = new Random();
		
	}
	
	public int randomModeChanger(int randomModeGlobal){
		
		randomModeGlobal = indexGenerator.nextInt(11);
		
		return randomModeGlobal;
	}
	
	public int randomFingerPicker(int placer){
		 
		placer = indexGenerator.nextInt(4);
		
		return placer;
		
	}
	
	public int randomPhrasePicker(int phrase){
		phrase = indexGenerator.nextInt(11);
		
		return phrase;
	}


	
}
