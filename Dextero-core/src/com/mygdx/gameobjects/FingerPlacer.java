package com.mygdx.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class FingerPlacer {

	private Vector2[] placerCoordinates;
	private Rectangle touchBoundary = new Rectangle(10,10,460,140);

	int activeTouch = 0;

	private boolean isPlacer1Scored = false, isPlacer2Scored = false,
			isScoreDeducted = false;

	private boolean isF1Decremented, isF2Decremented, isF3Decremented,
			isF4Decremented = false;


	public FingerPlacer() {
		placerCoordinates = new Vector2[4];
	}

	public Vector2[] getPlacerCoords() {

		return placerCoordinates;
	}

	public int getActiveTouch() {
		return activeTouch;
	}
	
	public Rectangle getTouchBoundary(){
		return touchBoundary;
	}

	/* realised this method is redundant and never used */
	public boolean isPlacersGenerated(int numberOfPlacers) {

		if (numberOfPlacers < 4) {
			return false;
		}
		return true;
	} 

	public void addFingerPlacers(OrthographicCamera cam) {
	/*goes through the coordinates value for each finger and fetches their x and y coordinates maps them
	 * onto the game world and adds it to the placerCoordinates array if the pointer is touching the screen
	 * and if it's within the touch boundary */
		for (int i = 0; i < 4; i++) {
			Vector3 touchVector = new Vector3(Gdx.input.getX(i),Gdx.input.getY(i),0);
			cam.unproject(touchVector);
			
			if (Gdx.app.getInput().isTouched(i) && touchBoundary.contains(touchVector.x,touchVector.y)) {
				placerCoordinates[i] = new Vector2(Gdx.input.getX(i),
						Gdx.input.getY(i));
			} 
		}
	}

	public void checkActiveTouch() {
 // checks how many fingers are on the screen currently.
		activeTouch = 0;
		for (int i = 0; i < 4; i++) {
			if (Gdx.app.getInput().isTouched(i)) {
				activeTouch++;
			}
		}

	}

	public boolean arePlacersFilled() {
		//checks whether the placerCoordinates array has been filled with coordinates and none are null
		for(int i =0; i<placerCoordinates.length; i++){
			if(placerCoordinates[i] == null){
				return false;
			}
		}
		return true;
	}	
	
	public void resetVariables() {
		isPlacer1Scored = false;
		isPlacer2Scored = false;

		isF1Decremented = false;
		isF2Decremented = false;
		isF3Decremented = false;
		isF4Decremented = false;

		isScoreDeducted = false;
	}

	public boolean isPlacer1Scored() {
		return isPlacer1Scored;
	}

	public boolean isPlacer2Scored() {
		return isPlacer2Scored;
	}

	public void setIsPlacer1Scored(boolean b) {
		isPlacer1Scored = b;
	}

	public void setIsPlacer2Scored(boolean b) {
		isPlacer2Scored = b;
	}

	public boolean isScoreDeducted() {
		return isScoreDeducted;
	}

	public void setIsF1Decremented(boolean b) {
		isF1Decremented = b;
	}

	public void setIsF2Decremented(boolean b) {
		isF2Decremented = b;
	}

	public void setIsF3Decremented(boolean b) {
		isF3Decremented = b;
	}

	public void setIsF4Decremented(boolean b) {
		isF4Decremented = b;
	}
	
	public boolean isF1Decremented(){
		return isF1Decremented;
	}
	
	public boolean isF2Decremented(){
		return isF2Decremented;
	}
	
	public boolean isF3Decremented(){
		return isF3Decremented;
	}
	
	public boolean isF4Decremented(){
		return isF4Decremented;
	}
	
}
