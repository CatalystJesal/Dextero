package com.mygdx.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.dexhelpers.AssetLoader;
import com.mygdx.ui.Button;

public class GameRenderer {

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private GameWorld world;

	private Vector3 indicatorVector1, indicatorVector2;
	private Rectangle touchBoundary;

	public GameRenderer(GameWorld world, OrthographicCamera camera,
			 SpriteBatch batcher) {

		this.camera = camera;
		camera.setToOrtho(false, 480, 270);
		this.batcher = batcher;
		batcher.setProjectionMatrix(camera.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		this.world = world;
		touchBoundary = world.getFingerPlacer().getTouchBoundary();

	}

	public void render() {
		Gdx.gl.glClearColor(255 / 255f, 255 / 255f, 204 / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawFonts(world.getTimer());

		if (world.isReadyDexterity()) {
			createTouchBoundary();
		}
		
		if ((!world.getFingerPlacer().arePlacersFilled()
				&& Gdx.input.isTouched() && world.isReadyDexterity())) {

			world.getFingerPlacer().addFingerPlacers(camera);
		}

		if (world.getFingerPlacer().arePlacersFilled()
				&& (world.isHardModeRunning() || world.isEasyModeRunning() || world
						.isMediumModeRunning())) {

			world.getIndicator().generateCoords();
			world.getFingerPlacer().checkActiveTouch();

			world.getAlgorithm().vec2Sorter(
					world.getFingerPlacer().getPlacerCoords());
			world.getAlgorithm().vec2Sorter(
					world.getIndicator().getIndicatorCoords());
		}
		
		if(world.isDexInstructions()){
			dexModeInstructions();
		}
		
		if(world.isSpeechInstructions()){
			speechModeInstructions();
		}


	}
	
	public void dexModeInstructions(){
		batcher.begin();
		AssetLoader.blackFont.setScale(0.25f, 0.32f);
		
		AssetLoader.blackFont.drawMultiLine(batcher, "The aim of the game is to score as many points as possible within the time limit. \n\n" +
				"Place all fingers of your left/right hand (excluding thumb) on the screen inside the faint rectangular \n" +
				"boundary to activate the game. \n\n" +
				"Circles will generate on the screen below each of your fingers and grey rectangles will appear above \n" +
				"particular fingers. You are required to lift fingers which have the grey rectangle above it, before \n" +
				"placing them back down on their circles. You score a point for lifting the correct finger, but lose a \n" +
				"point for lifting the incorrect finger. \n\n" +
				"A combo counter will be shown on the side which will add to the timer an extra 5 seconds for \n" +
				"consecutive successful finger lifts. However, the combo counter moves to the next multiple of 2 \n" +
				"after achieving the previous bound. So at first you will require 2 successful finger lifts to get 5 \n" +
				"seconds additional time which will afterwards become trickier requiring 4, 8, 12 etc... \n\n" +
				"The game timer and counters for each finger can be seen at the very top of the screen. \n" +
				"Good Luck and Enjoy!",20, 254);
		batcher.end();
	}
	
	public void speechModeInstructions(){
		batcher.begin();
		AssetLoader.blackFont.setScale(0.25f, 0.45f);
		
		AssetLoader.blackFont.drawMultiLine(batcher, "Requirements: You will need mobile data/ WIFI to play this game mode. \n\n" +
				"You will be shown a phrase on the screen in which you must communicate after selecting the ‘Speak’ \n" +
				"button. \n\n" +
				"You gain a single point for saying the correct phrase and lose an attempt for communicating the \n" +
				"wrong phrase. The gameplay ends if you have no remaining attempts. \n\n" +
				"Good Luck and Enjoy!", 20, 250);
		
		batcher.end();
		
	}


	public void renderGamePlay(int placerIndex1, int placerIndex2,
			int globalRandomIndex) {
		renderFingerPlacers();
		renderIndicators();
		renderActiveIndicator(placerIndex1, placerIndex2, globalRandomIndex);

	}

	public void createTouchBoundary() {
		shapeRenderer.begin(ShapeType.Line);

		shapeRenderer.setColor(0, 0, 0, 1);

		shapeRenderer.rect(touchBoundary.x, touchBoundary.y,
				touchBoundary.width, touchBoundary.height);

		shapeRenderer.end();

	}

	public void renderFingerPlacers() {

		shapeRenderer.begin(ShapeType.Filled);
		for (int i = 0; i < world.getFingerPlacer().getPlacerCoords().length; i++) {
			Vector3 touchVector = new Vector3(world.getFingerPlacer()
					.getPlacerCoords()[i].x, world.getFingerPlacer()
					.getPlacerCoords()[i].y, 0);
			camera.unproject(touchVector);

			shapeRenderer.setColor(0, 0, 0, 1);

			shapeRenderer.circle(touchVector.x, touchVector.y, 25);
		}

		shapeRenderer.end();
	}

	public void renderIndicators() {
/* shape renderer is set to filled to fill the colour of each indicator to be equivalent to the background colour.
 * Bear in mind the indicator coordinates are fetched and they are unprojected so that they are mapped onto the game world
 * and the indicators are set as small rectangles.*/
		shapeRenderer.begin(ShapeType.Filled);

		for (int i = 0; i < world.getIndicator().getIndicatorCoords().length; i++) {
			Vector3 touchVector = new Vector3(world.getIndicator()
					.getIndicatorCoords()[i].x, world.getIndicator()
					.getIndicatorCoords()[i].y, 0);
			camera.unproject(touchVector);
			;
			shapeRenderer.setColor(255 / 255f, 255 / 255f, 204 / 255f, 1f);
			shapeRenderer.rect(touchVector.x, touchVector.y, 20, 10);

		}
		shapeRenderer.end();
	}

	public void renderActiveIndicator(int placerIndex1, int placerIndex2,
			int globalRandomIndex) {
		/*depending on the game world running one or two indicators are rendered to a light grey colour to represent them
		 * as active indicators */
 
		if (world.isHardModeRunning()) {
			shapeRenderer.begin(ShapeType.Filled);

			shapeRenderer.setColor(59 / 144f, 58 / 144f, 58 / 144f, 1f);

			indicatorVector1 = new Vector3(world.getIndicator()
					.getIndicatorCoords()[placerIndex1].x, world.getIndicator()
					.getIndicatorCoords()[placerIndex1].y, 0);

			camera.unproject(indicatorVector1);

			shapeRenderer.rect(indicatorVector1.x, indicatorVector1.y, 20, 10);

			/*if the globalRandomIndex variable is > 5 then the placerIndex2 value equivalent to an indicator's index position is 
			 * rendered to light grey also. This means two indicators are active at the same time. */
			if (globalRandomIndex > 5) {
				indicatorVector2 = new Vector3(world.getIndicator()
						.getIndicatorCoords()[placerIndex2].x, world
						.getIndicator().getIndicatorCoords()[placerIndex2].y, 0);

				camera.unproject(indicatorVector2);

				shapeRenderer.rect(indicatorVector2.x, indicatorVector2.y, 20,
						10);
			}

			shapeRenderer.end();

		} else if (world.isEasyModeRunning()) {
			/*if game is set to easy mode then only a single indicator is set active  */
			shapeRenderer.begin(ShapeType.Filled);

			shapeRenderer.setColor(59 / 144f, 58 / 144f, 58 / 144f, 1f);

			indicatorVector1 = new Vector3(world.getIndicator()
					.getIndicatorCoords()[placerIndex1].x, world.getIndicator()
					.getIndicatorCoords()[placerIndex1].y, 0);

			camera.unproject(indicatorVector1);

			shapeRenderer.rect(indicatorVector1.x, indicatorVector1.y, 20, 10);

			shapeRenderer.end();
		} else if (world.isMediumModeRunning()) {
			/*if game is set to medium mode then exactly two indicators is set active */
			shapeRenderer.begin(ShapeType.Filled);

			shapeRenderer.setColor(59 / 144f, 58 / 144f, 58 / 144f, 1f);

			indicatorVector1 = new Vector3(world.getIndicator()
					.getIndicatorCoords()[placerIndex1].x, world.getIndicator()
					.getIndicatorCoords()[placerIndex1].y, 0);

			camera.unproject(indicatorVector1);

			shapeRenderer.rect(indicatorVector1.x, indicatorVector1.y, 20, 10);

			indicatorVector2 = new Vector3(world.getIndicator()
					.getIndicatorCoords()[placerIndex2].x, world.getIndicator()
					.getIndicatorCoords()[placerIndex2].y, 0);

			camera.unproject(indicatorVector2);

			shapeRenderer.rect(indicatorVector2.x, indicatorVector2.y, 20, 10);

			shapeRenderer.end();
		}
	}

	public void drawInGameStats(float timer) {
		//the float value is rounded to its whole number when counting down the timer 
		int time = Math.round(timer);
		AssetLoader.blackFont.setScale(0.50f, 0.90f);
		if (!world.isSpeechModeRunning()) {
			if (!world.isGameOverDexterity()) {
				/* game timer is shown if it's greater than 0 as long as the dexterity game mode is running and it's not game over 
				 * state for dexterity mode*/	
				if (time > 0) {	 
			
					AssetLoader.blackFont.draw(batcher, "" + (time), 234, 253);
					
					//if combo counter is 0 then the font is displayed in black, otherwise it's displayed in green with x next to it
					if(world.getComboCount() == 0){
						AssetLoader.blackFont.draw(batcher, ""+world.getComboCount(), 20, 230);
					} else{
						AssetLoader.greenFont.setScale(0.50f, 0.90f);
						AssetLoader.greenFont.draw(batcher, world.getComboCount()+" x", 20, 230);
					}
				}
			} else {
				// if the state of the game is game over dexterity then total score is shown instead of combo counter and timer.
				AssetLoader.blackFont.draw(batcher, "" + world.getTotalScore(),
						234, 253);
			}
			
			//scores for each individual finger is shown regardless of dexterity gameplay in game over or running state.

			AssetLoader.blackFont.draw(batcher, "" + world.getF1Score(), 90,
					253);
			AssetLoader.blackFont.draw(batcher, "" + world.getF2Score(), 185,
					220);
			AssetLoader.blackFont.draw(batcher, "" + world.getF3Score(), 280,
					220);
			AssetLoader.blackFont.draw(batcher, "" + world.getF4Score(), 375,
					253);
		}

		if (world.isSpeechModeRunning()) {
			//if speech mode is running then we show attempts left, score and phrase player must speak
			AssetLoader.blackFont.setScale(0.23f, 0.73f);

			AssetLoader.blackFont.draw(batcher, "Attempts Left", 185, 267);

			AssetLoader.blackFont.draw(batcher, "Score", 260, 267);

			AssetLoader.blackFont.setScale(0.30f, 0.73f);

			AssetLoader.blackFont.draw(batcher, "Say the phrase: ", 50, 245);

			AssetLoader.blackFont.setScale(0.50f, 0.90f);

			AssetLoader.blackFont.draw(batcher, "" + world.getSpeechAttempts(),
					205, 243);

			AssetLoader.blackFont.draw(batcher, "" + world.getSpeechScore(),
					265, 243);

			AssetLoader.blackFont.draw(batcher,
					"" + world.getPhrasesLst()[world.getRandomPhrase()], 50, 222);
		}

	}

	public void drawFonts(float timer) {
		batcher.begin();

		if (world.isSpeechModeRunning()) {
			drawInGameStats(timer);
		}

		if (world.isSpeechModeOver()) {
			AssetLoader.blackFont.setScale(0.35f, 0.40f);

			AssetLoader.blackFont.draw(batcher, "Score", 223, 267);

			AssetLoader.blackFont.setScale(0.50f, 0.90f);

			AssetLoader.blackFont.draw(batcher, "" + world.getSpeechScore(),
					234, 253);

		}

		if (world.isSpeechHs()) {

			AssetLoader.blackFont.setScale(0.65f, 0.65f);
			AssetLoader.greenFont.setScale(0.65f, 0.65f);

			boolean isNewScoreRendered = false;

			for (int i = 0; i < world.getSpeechHsLst().length; i++) {

				if (i < world.getDexterityHsLst().length - 1) {
					if ((world.getNewSpeechHs() == world.getSpeechHsLst()[i])
							&& !isNewScoreRendered) {
						AssetLoader.greenFont.draw(batcher,
								i + 1 + ". " + world.getSpeechHsLst()[i], 210,
								210 - (i * 18));

						isNewScoreRendered = true;
					} else {
						AssetLoader.blackFont.draw(batcher, i + 1 + ". "
								+ world.getSpeechHsLst()[i], 210,
								210 - (i * 18));

					}
				} else {
					if ((world.getNewSpeechHs() == world.getSpeechHsLst()[i])
							&& !isNewScoreRendered) {
						AssetLoader.greenFont.draw(batcher,
								i + 1 + ". " + world.getSpeechHsLst()[i], 196.7f,
								210 - (i * 18));
						
						isNewScoreRendered = true;
					} else {
						AssetLoader.blackFont.draw(batcher, i + 1 + ". "
								+ world.getSpeechHsLst()[i], 196.7f,
								210 - (i * 18));
					}
				}

			}
		}

		if ((world.getFingerPlacer().arePlacersFilled() && (world
				.isHardModeRunning() || world.isEasyModeRunning() || world
					.isMediumModeRunning())) || world.isReadyDexterity()) {

			drawInGameStats(timer);
		}
		if (world.isGameOverSplash()) {
			AssetLoader.blackFont.setScale(0.70f, 0.83f);
			AssetLoader.blackFont.draw(batcher, "Game Over!", 170, 170);
		}

		if (world.isGameOverDexterity()) {
			if(world.isHighscoreUpdated()){
			drawInGameStats(timer);


			AssetLoader.blackFont.setScale(0.35f, 0.40f);
			AssetLoader.blackFont.draw(batcher, "Total Score", 204f, 265);
			
			// Score Labels
			AssetLoader.blackFont.setScale(0.20f, 0.33f);
			AssetLoader.blackFont.draw(batcher, "Finger1 Errors", 35, 265);
			AssetLoader.blackFont.draw(batcher, "Finger2 Errors", 130, 232);
			AssetLoader.blackFont.draw(batcher, "Finger3 Errors", 298, 232);
			AssetLoader.blackFont.draw(batcher, "Finger4 Errors", 393, 265);

			AssetLoader.blackFont.setScale(0.33f, 0.45f);

			AssetLoader.blackFont.draw(batcher, "" + world.getF1Errors(), 56,
					255);
			AssetLoader.blackFont.draw(batcher, "" + world.getF2Errors(), 151,
					222);
			AssetLoader.blackFont.draw(batcher, "" + world.getF3Errors(), 319,
					222);
			AssetLoader.blackFont.draw(batcher, "" + world.getF4Errors(), 414,
					255);
			}

		}
		if (world.isDexterityHs()) {

			AssetLoader.blackFont.setScale(0.30f, 0.30f);

			AssetLoader.blackFont.draw(batcher, "Total Score", 39 + (30),
					205 + (40));
			AssetLoader.blackFont.draw(batcher, "Total Errors", 113 + (20),
					205 + (40)); 

			AssetLoader.blackFont.draw(batcher, "F1 Score", 176 + (40),
					205 + (40)); 
			AssetLoader.blackFont.draw(batcher, "F2 Score", 227 + (40),
					205 + (40)); 
			AssetLoader.blackFont.draw(batcher, "F3 Score", 278 + (40),
					205 + (40)); 
			AssetLoader.blackFont.draw(batcher, "F4 Score", 327 + (40),
					205 + (40)); 

			AssetLoader.blackFont.setScale(0.60f, 0.45f);
			AssetLoader.greenFont.setScale(0.60f, 0.45f);

			boolean isNewScoreRendered = false;

			for (int i = 0; i < world.getDexterityHsLst().length; i++) {
				/* if none of the existing high scores are in the high scores list then 
				 * the new high score is rendered. Also, it is rendered if it hasn't already been, 
				 * preventing it from rendering again*/
				if (!world.hasLstFoundNewScore(i) || isNewScoreRendered) {
					//if the high score number is 10 then the font is lowered slightly on the x-axis to align with other numbers
					if (i >= world.getDexterityHsLst().length - 1) {
						AssetLoader.blackFont.draw(batcher, i + 1 + ".", 28,
								170 + (50) - (i * 16));

					} else {
						//otherwise this number is not the 10th high score and is below the 10th
						AssetLoader.blackFont.draw(batcher, i + 1 + ".", 40,
								170 + (50) - (i * 16));
					}

					//all the statistics are rendered in black font 
					AssetLoader.blackFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][0], 90,
							170 + (50) - (i * 16));

					AssetLoader.blackFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][1], 90 + 140,
							170 + (50) - (i * 16));
					AssetLoader.blackFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][2], 90 + 190,
							170 + (50) - (i * 16)); 
					AssetLoader.blackFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][3], 90 + 240,
							170 + (50) - (i * 16)); 
					AssetLoader.blackFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][4], 90 + 290,
							170 + (50) - (i * 16)); 
					AssetLoader.blackFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][5], 90 + 60,
							170 + (50) - (i * 16)); 

			/*otherwise render the highscore in green font if all its statistics aren't the same as any other record row */
				} else if (world.hasLstFoundNewScore(i) && !isNewScoreRendered) {
					if (i >= world.getDexterityHsLst().length - 1) {
						AssetLoader.greenFont.draw(batcher, i + 1 + ".", 28,
								170 + (50) - (i * 16));

					} else if (world.hasLstFoundNewScore(i)
							&& !isNewScoreRendered) {
						AssetLoader.greenFont.draw(batcher, i + 1 + ".", 40,
								170 + (50) - (i * 16));
					}

					AssetLoader.greenFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][0], 90,
							170 + (50) - (i * 16));
					
					AssetLoader.greenFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][1], 90 + 140,
							170 + (50) - (i * 16)); 
					AssetLoader.greenFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][2], 90 + 190,
							170 + (50) - (i * 16)); 
					AssetLoader.greenFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][3], 90 + 240,
							170 + (50) - (i * 16)); 
					AssetLoader.greenFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][4], 90 + 290,
							170 + (50) - (i * 16)); 
					AssetLoader.greenFont.draw(batcher,
							" " + world.getDexterityHsLst()[i][5], 90 + 60,
							170 + (50) - (i * 16)); 

					isNewScoreRendered = true;
				}
			}

			int total = 0;
			int count = 0;

			for (int i = 0; i < world.getDexterityHsLst().length; i++) {

				if (world.getDexterityHsLst()[i][0] != 0) {
					total += world.getDexterityHsLst()[i][0];
					count++;
				}
			}
			float avgScore = 0;

			if (total != 0 && count != 0) {
				avgScore = total / count;
			}
			
			AssetLoader.blackFont.setScale(0.35f, 0.35f);
			AssetLoader.blackFont.draw(batcher, "Average Score: " + avgScore,
					40, 40);
			
			total = 0;
			count = 0;
			
			for (int i = 0; i < world.getDexterityHsLst().length; i++) {

				if (world.getDexterityHsLst()[i][0] != 0) {
					total += world.getDexterityHsLst()[i][5];
					count++;
				}
			}
			avgScore = 0;

			if (total != 0 && count != 0) {
				avgScore = total / count;
			}
			
			AssetLoader.blackFont.draw(batcher, "Average Errors: " + avgScore,
					220, 40);

		}

		batcher.end();

	}
}
