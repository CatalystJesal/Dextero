package com.mygdx.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.dexhelpers.NumberGenerator;

public class ScoreManager {

	private boolean justTouchedUp1, justTouchedUp2;
	private int placerIndex1, tempPlacerIndex1, placerIndex2, tempPlacerIndex2;

	private int globalRandomIndex;

	private boolean isScoreDeducted;

	private GameWorld world;
	private GameRenderer render;
	private NumberGenerator numberGen;

	private OrthographicCamera camera;

	public ScoreManager(GameWorld world, OrthographicCamera camera,
			GameRenderer render) {
		this.world = world;
		numberGen = world.getNumberGenerator();
		this.camera = camera;
		this.render = render;
		isScoreDeducted = world.getFingerPlacer().isScoreDeducted();

		globalRandomIndex = numberGen.randomModeChanger(globalRandomIndex);

		placerIndex1 = numberGen.randomFingerPicker(placerIndex1);

		if (placerIndex2 == placerIndex1) {
			placerIndex2 = world.getNumberGenerator().randomFingerPicker(
					placerIndex2);
		}

		tempPlacerIndex1 = 0;
		tempPlacerIndex2 = 0;

	}

	public void update() {

		if (world.getFingerPlacer().arePlacersFilled()
				&& (world.isHardModeRunning() || world.isEasyModeRunning() || world
						.isMediumModeRunning())) {
			render.renderGamePlay(placerIndex1, placerIndex2, globalRandomIndex);
		}

		if (world.isHardModeRunning()) {
			hardModeScoreHandler();
			hardIndicatorChanger();
		} else if (world.isEasyModeRunning()) {
			easyModeScoreHandler();
			easyIndicatorChanger();
		} else if (world.isMediumModeRunning()) {
			mediumModeScoreHandler();
			mediumIndicatorChanger();
		}

		if (world.isSpeechModeRunning()) {
			if (world.isSpeechCorrect()) {
				world.incrementSpeechScore(1);
				world.setIsSpeechCorrect(-1);
			}
		}
	}

	public void hardModeScoreHandler() {

		justTouchedUp1 = Gdx.input
				.getInputProcessor()
				.touchUp(
						(int) world.getFingerPlacer().getPlacerCoords()[placerIndex1].x,
						(int) world.getFingerPlacer().getPlacerCoords()[placerIndex1].y,
						placerIndex1, 0);

		justTouchedUp2 = Gdx.input
				.getInputProcessor()
				.touchUp(
						(int) world.getFingerPlacer().getPlacerCoords()[placerIndex2].x,
						(int) world.getFingerPlacer().getPlacerCoords()[placerIndex2].y,
						placerIndex2, 0);

		//if globalRandomIndex is <= 5 then justTouchedUp1 is only checked to see if the player scored a point
		if (globalRandomIndex <= 5) {
			if (world.getFingerPlacer().isPlacer1Scored() == false
					&& world.getFingerPlacer().getActiveTouch() >= 3) {

				if (justTouchedUp1) {
					if (placerIndex1 == 0) {
						world.incrementF1Score(1);
						world.getFingerPlacer().setIsPlacer1Scored(true);
						world.incrementComboCount(1);
					} else if (placerIndex1 == 1) {
						world.incrementF2Score(1);
						world.getFingerPlacer().setIsPlacer1Scored(true);
						world.incrementComboCount(1);
					} else if (placerIndex1 == 2) {
						world.incrementF3Score(1);
						world.getFingerPlacer().setIsPlacer1Scored(true);
						world.incrementComboCount(1);
					} else if (placerIndex1 == 3) {
						world.incrementF4Score(1);
						world.getFingerPlacer().setIsPlacer1Scored(true);
						world.incrementComboCount(1);
					}
				}
			}
		}
		/*however if it's > 5 then justTouchedUp1 and justTouchedUp2 are both checked, meaning two placers are active and checks
		 * on both must happen to see if both were successfully lifted up by the player */
		if (globalRandomIndex > 5) {

			if ((world.getFingerPlacer().isPlacer1Scored() == false || world
					.getFingerPlacer().isPlacer2Scored() == false)) {

				if (justTouchedUp1
						&& world.getFingerPlacer().isPlacer1Scored() == false
						&& world.getFingerPlacer().getActiveTouch() >= 2) {
					if (placerIndex1 == 0) {
						world.incrementF1Score(1);
						world.getFingerPlacer().setIsPlacer1Scored(true);

					} else if (placerIndex1 == 1) {
						world.incrementF2Score(1);
						world.getFingerPlacer().setIsPlacer1Scored(true);

					} else if (placerIndex1 == 2) {
						world.incrementF3Score(1);
						world.getFingerPlacer().setIsPlacer1Scored(true);

					} else if (placerIndex1 == 3) {
						world.incrementF4Score(1);
						world.getFingerPlacer().setIsPlacer1Scored(true);

					}
				}

				if (justTouchedUp2
						&& world.getFingerPlacer().isPlacer2Scored() == false
						&& world.getFingerPlacer().getActiveTouch() >= 2) {
					if (placerIndex2 == 0) {
						world.incrementF1Score(1);
						world.getFingerPlacer().setIsPlacer2Scored(true);

					} else if (placerIndex2 == 1) {
						world.incrementF2Score(1);
						world.getFingerPlacer().setIsPlacer2Scored(true);

					} else if (placerIndex2 == 2) {
						world.incrementF3Score(1);
						world.getFingerPlacer().setIsPlacer2Scored(true);

					} else if (placerIndex2 == 3) {
						world.incrementF4Score(1);
						world.getFingerPlacer().setIsPlacer2Scored(true);

					}
				}

				//if both placers have been scored and 2 or more fingers are back touching the screen then combo counter is incremented.
				if ((world.getFingerPlacer().isPlacer1Scored() == true && world
						.getFingerPlacer().isPlacer2Scored() == true)
						&& world.getFingerPlacer().getActiveTouch() >= 2) {
					world.incrementComboCount(1);
				}

			}
		}

		/*if globalRandomIndex is <= to 5 then if any fingers are touched up from the screen and isScoreDeducted is false
		 * then the easyScoreDeductor method is invoked as there is only a single active placer/indicator.
		 */
		if (globalRandomIndex <= 5) {
			if (isTouchedUp() && isScoreDeducted == false) {

				easyScoreDeductor(placerIndex1);

				isScoreDeducted = true;
			}
			// otherwise two placers/indicators are active which means that the mediumScoreDeductor method is invoked. 
		} else if (globalRandomIndex > 5) {
			if (isTouchedUp() && isScoreDeducted == false) {

				mediumScoreDeductor(placerIndex1, placerIndex2);

				isScoreDeducted = true;
			}
		}
	}

	public void easyModeScoreHandler() {

		justTouchedUp1 = Gdx.input
				.getInputProcessor()
				.touchUp(
						(int) world.getFingerPlacer().getPlacerCoords()[placerIndex1].x,
						(int) world.getFingerPlacer().getPlacerCoords()[placerIndex1].y,
						placerIndex1, 0);


		if (world.getFingerPlacer().isPlacer1Scored() == false
				&& world.getFingerPlacer().getActiveTouch() >= 3) {

			if (justTouchedUp1) {
				if (placerIndex1 == 0) {
					world.incrementF1Score(1);
					world.getFingerPlacer().setIsPlacer1Scored(true);
					world.incrementComboCount(1);
				} else if (placerIndex1 == 1) {
					world.incrementF2Score(1);
					world.getFingerPlacer().setIsPlacer1Scored(true);
					world.incrementComboCount(1);
				} else if (placerIndex1 == 2) {
					world.incrementF3Score(1);
					world.getFingerPlacer().setIsPlacer1Scored(true);
					world.incrementComboCount(1);
				} else if (placerIndex1 == 3) {
					world.incrementF4Score(1);
					world.getFingerPlacer().setIsPlacer1Scored(true);
					world.incrementComboCount(1);
				}
			}
		}

		if (isTouchedUp() && isScoreDeducted == false) {
	
			easyScoreDeductor(placerIndex1);

			isScoreDeducted = true;
		}

	}

	public void mediumModeScoreHandler() {

		justTouchedUp1 = Gdx.input
				.getInputProcessor()
				.touchUp(
						(int) world.getFingerPlacer().getPlacerCoords()[placerIndex1].x,
						(int) world.getFingerPlacer().getPlacerCoords()[placerIndex1].y,
						placerIndex1, 0);

		justTouchedUp2 = Gdx.input
				.getInputProcessor()
				.touchUp(
						(int) world.getFingerPlacer().getPlacerCoords()[placerIndex2].x,
						(int) world.getFingerPlacer().getPlacerCoords()[placerIndex2].y,
						placerIndex2, 0);

		if ((world.getFingerPlacer().isPlacer1Scored() == false || world
				.getFingerPlacer().isPlacer2Scored() == false)) {

			if (justTouchedUp1
					&& world.getFingerPlacer().isPlacer1Scored() == false
					&& world.getFingerPlacer().getActiveTouch() >= 2) {
				if (placerIndex1 == 0) {
					world.incrementF1Score(1);
					world.getFingerPlacer().setIsPlacer1Scored(true);
				} else if (placerIndex1 == 1) {
					world.incrementF2Score(1);
					world.getFingerPlacer().setIsPlacer1Scored(true);
				} else if (placerIndex1 == 2) {
					world.incrementF3Score(1);
					world.getFingerPlacer().setIsPlacer1Scored(true);
				} else if (placerIndex1 == 3) {
					world.incrementF4Score(1);
					world.getFingerPlacer().setIsPlacer1Scored(true);
				}
			}

			if (justTouchedUp2
					&& world.getFingerPlacer().isPlacer2Scored() == false
					&& world.getFingerPlacer().getActiveTouch() >= 2) {
				if (placerIndex2 == 0) {
					world.incrementF1Score(1);
					world.getFingerPlacer().setIsPlacer2Scored(true);
				} else if (placerIndex2 == 1) {
					world.incrementF2Score(1);
					world.getFingerPlacer().setIsPlacer2Scored(true);
				} else if (placerIndex2 == 2) {
					world.incrementF3Score(1);
					world.getFingerPlacer().setIsPlacer2Scored(true);
				} else if (placerIndex2 == 3) {
					world.incrementF4Score(1);
					world.getFingerPlacer().setIsPlacer2Scored(true);
				}
			}

			if ((world.getFingerPlacer().isPlacer1Scored() == true && world
					.getFingerPlacer().isPlacer2Scored() == true)
					&& world.getFingerPlacer().getActiveTouch() >= 2) {
				world.incrementComboCount(1);
			}

		}
		if (isTouchedUp() && isScoreDeducted == false) {

			mediumScoreDeductor(placerIndex1, placerIndex2);

			isScoreDeducted = true;
		}

	}

	public void hardIndicatorChanger() {
		//if there's only one active placer/indicator... 
		if (globalRandomIndex <= 5) {
			/*if the single placer/indicator has been scored and isScoreDeducted method has had a chance to be invoked to 
			 * check for any wrong finger lifts (so set to true)...
			 */
			if (world.getFingerPlacer().isPlacer1Scored() == true
					&& isScoreDeducted == true) {
/*the globalRandomIndex is randomly picked once again using its random number generator function to determine
 * whether one or two placers/indicators will be active next
 */
				globalRandomIndex = numberGen
						.randomModeChanger(globalRandomIndex);

				/*a temporary note is taken of the current placerIndex1 value to provide a different
				 * value for the placerIndex1 as opposed to the same
				 */
				tempPlacerIndex1 = placerIndex1;
				while (tempPlacerIndex1 == placerIndex1) {
					placerIndex1 = numberGen.randomFingerPicker(placerIndex1);

				}

				//the placerIndex2 is chosen a different value to placerIndex 1 if it's the same as that placer.
				while (placerIndex2 == placerIndex1) {
					placerIndex2 = numberGen.randomFingerPicker(placerIndex2);

				}
//all boolean variables are set back to false 
				world.getFingerPlacer().setIsPlacer1Scored(false);
				world.getFingerPlacer().setIsF1Decremented(false);
				world.getFingerPlacer().setIsF2Decremented(false);
				world.getFingerPlacer().setIsF3Decremented(false);
				world.getFingerPlacer().setIsF4Decremented(false);

//the system time is taken into account at this point in nano seconds 
			long startTime = System.nanoTime();
//while any fingers remain touched up the estimated time is taken which deducted the current system time and deducts the startTime
			while (isTouchedUp()) {
				long estimatedTime = System.nanoTime() - startTime;
/*if by the time either the estimated time becomes greater than 1.75 seconds or none of the fingers are touched off from the screen
 * then the function is returned as the game continues as usual. However bear in mind if the person doesn't have their scored fingers 
 * back down on the screen within the 1.75seconds then their those placers will count as score deductions when the game loop reiterates.
 * This prevents the player from leaving their fingers up for long and preventing the game to be frozen. 
 * 
 * In the project report, this if statement is the replacement for the failure to achieve asynchronous threads in android.
 */
				if (!isTouchedUp() || estimatedTime >= 1.75e+9) {
					return;
				}
			}
				
			}

		} else {

			if ((world.getFingerPlacer().isPlacer1Scored() == true && world
					.getFingerPlacer().isPlacer2Scored() == true)
					&& isScoreDeducted == true) {

				globalRandomIndex = numberGen
						.randomModeChanger(globalRandomIndex);

				tempPlacerIndex1 = placerIndex1;
				while (tempPlacerIndex1 == placerIndex1) {
					placerIndex1 = numberGen.randomFingerPicker(placerIndex1);

				}

				tempPlacerIndex2 = placerIndex2;
				while (tempPlacerIndex2 == placerIndex2
						&& placerIndex2 == placerIndex1) {
					placerIndex2 = numberGen.randomFingerPicker(placerIndex2);

				}

				world.getFingerPlacer().setIsPlacer1Scored(false);
				world.getFingerPlacer().setIsPlacer2Scored(false);

				world.getFingerPlacer().setIsF1Decremented(false);
				world.getFingerPlacer().setIsF2Decremented(false);
				world.getFingerPlacer().setIsF3Decremented(false);
				world.getFingerPlacer().setIsF4Decremented(false);

				long startTime = System.nanoTime();

				while (isTouchedUp()) {
					long estimatedTime = System.nanoTime() - startTime;
					System.out.println("estimatedTime " + estimatedTime);
					if (!isTouchedUp() || estimatedTime >= 1.75e+9) {
						return;
					}
				}
			}
		}

		world.setTotalScore();
		world.setTotalErrors();
		isScoreDeducted = false;

	}

	public void easyIndicatorChanger() {

		if (world.getFingerPlacer().isPlacer1Scored() == true) {

			tempPlacerIndex1 = placerIndex1;
			while (tempPlacerIndex1 == placerIndex1) {
				placerIndex1 = numberGen.randomFingerPicker(placerIndex1);

			}

			world.getFingerPlacer().setIsPlacer1Scored(false);

			world.getFingerPlacer().setIsF1Decremented(false);
			world.getFingerPlacer().setIsF2Decremented(false);
			world.getFingerPlacer().setIsF3Decremented(false);
			world.getFingerPlacer().setIsF4Decremented(false);

			long startTime = System.nanoTime();

			while (isTouchedUp()) {
				long estimatedTime = System.nanoTime() - startTime;

				if (!isTouchedUp() || estimatedTime >= 1.75e+9) {
					return;
				}
			}

		}

		world.setTotalScore();
		world.setTotalErrors();
		isScoreDeducted = false;
	}

	public void mediumIndicatorChanger() {

		if ((world.getFingerPlacer().isPlacer1Scored() == true && world
				.getFingerPlacer().isPlacer2Scored() == true)
				&& isScoreDeducted == true) {

			tempPlacerIndex1 = placerIndex1;
			while (tempPlacerIndex1 == placerIndex1) {
				placerIndex1 = numberGen.randomFingerPicker(placerIndex1);
			}

			tempPlacerIndex2 = placerIndex2;
			while (tempPlacerIndex2 == placerIndex2
					&& placerIndex2 == placerIndex1) {
				placerIndex2 = numberGen.randomFingerPicker(placerIndex2);

			}

			world.getFingerPlacer().setIsPlacer1Scored(false);
			world.getFingerPlacer().setIsPlacer2Scored(false);

			world.getFingerPlacer().setIsF1Decremented(false);
			world.getFingerPlacer().setIsF2Decremented(false);
			world.getFingerPlacer().setIsF3Decremented(false);
			world.getFingerPlacer().setIsF4Decremented(false);

			long startTime = System.nanoTime();

			while (isTouchedUp()) {
				long estimatedTime = System.nanoTime() - startTime;
				System.out.println("estimatedTime " + estimatedTime);
				if (!isTouchedUp() || estimatedTime >= 1.75e+9) {
					return;
				}
			}

		}

		world.setTotalScore();
		world.setTotalErrors();
		isScoreDeducted = false;
	}

	public void easyScoreDeductor(int currentPlacer1) {
//iterates through each finger placer index 
		for (int placerIndex = 0; placerIndex < world.getFingerPlacer()
				.getPlacerCoords().length; placerIndex++) {
			/*retrieves the placer x and y coordinates at the index i of the finger placer coordinates array
			 * and creates a new Vector3 instance which is used to map the coordinates onto the game world
			 */
			Vector3 placerVector1 = new Vector3(world.getFingerPlacer()
					.getPlacerCoords()[placerIndex].x, world.getFingerPlacer()
					.getPlacerCoords()[placerIndex].y, 0);
			camera.unproject(placerVector1);

			/*there is a check to see whether the placerIndex != currentPlacer1 in order to
			 * avoid the active placer/indicator from being checked and deducted for being lifted
			 * when it really should be scored.
			 */
			if (placerIndex != currentPlacer1) {

				//the rest of the checks are equivalent to how the touchUp() function works in the InputHandler class
				for (int i = 0; i < 4; i++) {
					boolean isCurrentTouched = Gdx.input.isTouched(i);

					Vector3 gdxPointer = new Vector3(Gdx.input.getX(i),
							Gdx.input.getY(i), 0);
					camera.unproject(gdxPointer);

					float distance = placerVector1.dst(gdxPointer.x,
							gdxPointer.y, 0);
					float radius = 25.0f;

					if ((distance <= radius) && !isCurrentTouched) {
						/*The placerIndex is identified, appropriate to deduct its individual score variable
						and increment it's error counter variable, the combo count is also set back to 0. */
						if (placerIndex == 0
								&& !world.getFingerPlacer().isF1Decremented()) {
							world.incrementF1Errors(1);
							if (world.getF1Score() > 0) {
								world.decrementF1Score(1);
								world.setComboCount(0);
							}
							world.getFingerPlacer().setIsF1Decremented(true);
						} else if (placerIndex == 1
								&& !world.getFingerPlacer().isF2Decremented()) {
							world.incrementF2Errors(1);
							if (world.getF2Score() > 0) {
								world.decrementF2Score(1);
								world.setComboCount(0);
							}
							world.getFingerPlacer().setIsF2Decremented(true);
						} else if (placerIndex == 2
								&& !world.getFingerPlacer().isF3Decremented()) {
							world.incrementF3Errors(1);
							if (world.getF3Score() > 0) {
								world.decrementF3Score(1);
								world.setComboCount(0);
							}
							world.getFingerPlacer().setIsF3Decremented(true);
						} else if (placerIndex == 3
								&& !world.getFingerPlacer().isF4Decremented()) {
							world.incrementF4Errors(1);
							if (world.getF4Score() > 0) {
								world.decrementF4Score(1);
								world.setComboCount(0);
							}
							world.getFingerPlacer().setIsF4Decremented(true);
						}
					}
				}
			}
		}
	}

	public void mediumScoreDeductor(int currentPlacer1, int currentPlacer2) {

		for (int placerIndex = 0; placerIndex < world.getFingerPlacer()
				.getPlacerCoords().length; placerIndex++) {
			Vector3 placerVector1 = new Vector3(world.getFingerPlacer()
					.getPlacerCoords()[placerIndex].x, world.getFingerPlacer()
					.getPlacerCoords()[placerIndex].y, 0);
			camera.unproject(placerVector1);

			if (placerIndex != currentPlacer1 && placerIndex != currentPlacer2) {
				for (int i = 0; i < 4; i++) {
					boolean isCurrentTouched = Gdx.input.isTouched(i);

					Vector3 gdxPointer = new Vector3(Gdx.input.getX(i),
							Gdx.input.getY(i), 0);
					camera.unproject(gdxPointer);

					float distance = placerVector1.dst(gdxPointer.x,
							gdxPointer.y, 0);

					float radius = 25.0f;

					if ((distance <= radius) && !isCurrentTouched) {
						if (placerIndex == 0
								&& !world.getFingerPlacer().isF1Decremented()) {
							world.incrementF1Errors(1);
							if (world.getF1Score() > 0) {
								world.decrementF1Score(1);
								world.setComboCount(0);
							}
							world.getFingerPlacer().setIsF1Decremented(true);
						} else if (placerIndex == 1
								&& !world.getFingerPlacer().isF2Decremented()) {
							world.incrementF2Errors(1);
							if (world.getF2Score() > 0) {
								world.decrementF2Score(1);
								world.setComboCount(0);
							}
							world.getFingerPlacer().setIsF2Decremented(true);
						} else if (placerIndex == 2
								&& !world.getFingerPlacer().isF3Decremented()) {
							world.incrementF3Errors(1);
							if (world.getF3Score() > 0) {
								world.decrementF3Score(1);
								world.setComboCount(0);
							}
							world.getFingerPlacer().setIsF3Decremented(true);
						} else if (placerIndex == 3
								&& !world.getFingerPlacer().isF4Decremented()) {
							world.incrementF4Errors(1);
							if (world.getF4Score() > 0) {
								world.decrementF4Score(1);
								world.setComboCount(0);
							}
							world.getFingerPlacer().setIsF4Decremented(true);
						}
					}
				}
			}
		}
	}

	public NumberGenerator getRandomInt() {
		return numberGen;
	}

	public boolean isTouchedUp() {

		for (int i = 0; i < 4; i++) {

			if (!Gdx.input.isTouched(i)) {
				return true;
			}
		}
		return false;
	}

}
