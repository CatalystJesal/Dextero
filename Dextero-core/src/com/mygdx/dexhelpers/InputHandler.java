package com.mygdx.dexhelpers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.gameworld.GameWorld;


public class InputHandler implements InputProcessor {

	private GameWorld world;

	private OrthographicCamera camera;
	
	private Vector3 placerVector, gdxPointer;
	private float distance = 0;
	private boolean isCurrentTouched = true;
	private int radius = 25;


	public InputHandler(GameWorld world, OrthographicCamera camera) {

		this.world = world;
		this.camera = camera;

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if ((world.getFingerPlacer().arePlacersFilled() && (world
				.isEasyModeRunning() || world.isMediumModeRunning() || world
					.isHardModeRunning()))) {

			//takes the screen coordinates of the finger placer
			placerVector = new Vector3(screenX, screenY, 0);
			//maps it onto the game world 
			camera.unproject(placerVector);
//goes through each pointer...
			for (int i = 0; i < 4; i++) {
				//checks if each of them are touched 
				isCurrentTouched = Gdx.input.isTouched(i);
//the x and y coordinates of the pointer are retrieved and mapped onto the game world
				gdxPointer = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i),
						0);
				camera.unproject(gdxPointer);

				//the distance between the finger placer and the pointer is checked against
				distance = placerVector.dst(gdxPointer.x, gdxPointer.y, 0);
				//if the distance is within the radius of the finger placer and the finger is lifted then it means the person lifted the active placer 
				if (distance <= radius && !isCurrentTouched) {
					return true;
				}
			}
		}
		
		//otherwise they haven't lifted the active placer
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
