package com.mygdx.gameobjects;

import com.badlogic.gdx.math.Vector2;


public class Indicator {
	
	private Vector2[] indicatorCoords;
	private FingerPlacer placer;

	public Indicator(FingerPlacer placer){
		this.placer = placer;

		indicatorCoords = new Vector2[4];

		
	}

	
	public void generateCoords(){
/*loops through each index inside the indicator coordinates array and writes the x and y coordinates value of
 * its corresponding finger placer inside the finger placer coordinates array. However the y axis is offset. */
		for(int i=0; i<indicatorCoords.length;i++){

				  indicatorCoords[i] = new Vector2(placer.getPlacerCoords()[i].x,placer.getPlacerCoords()[i].y-120);
			  }
		}
	
	public Vector2[] getIndicatorCoords(){
		
		return indicatorCoords;
	}
	
}
