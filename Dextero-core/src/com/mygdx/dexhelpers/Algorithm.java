package com.mygdx.dexhelpers;

import com.badlogic.gdx.math.Vector2;

public class Algorithm {
	
	
	public Vector2[] vec2Sorter(Vector2[] args) {
		for (int i = 0; i < args.length; i++) {
			for (int j = 0; j < args.length; j++) {

				Vector2 currentNumber = args[j];
				if (j + 1 < args.length) {
					if (currentNumber.x > args[j + 1].x) {
						args[j] = args[j + 1];
						args[j + 1] = currentNumber;
					}
				}
			}
		}
		return args;
	}

}




