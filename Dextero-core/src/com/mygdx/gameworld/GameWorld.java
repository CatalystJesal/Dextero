package com.mygdx.gameworld;

import java.util.Arrays;

import com.mygdx.Dextero.Dextero;
import com.mygdx.dexhelpers.Algorithm;
import com.mygdx.dexhelpers.NumberGenerator;
import com.mygdx.gameobjects.FingerPlacer;
import com.mygdx.gameobjects.Indicator;

import com.mygdx.ui.Button;

public class GameWorld {

	private FingerPlacer fingerPlacer;
	private Indicator indicator;
	private NumberGenerator numberGen;
	private Algorithm sortAlg;

	private Button button;
	private int comboCount = 0, comboCap = 2;

	private int totalScore, totalErrors, f1Score, f2Score, f3Score, f4Score;

	private int f1Errors, f2Errors, f3Errors, f4Errors;

	private float timer;

	private String[] phrases = new String[11];

	private int randomPhrase;

	private int speechScore = 0;

	private int isSpeechCorrect = -1;

	private int speechAttempts = 2;

	private boolean isDexModeActive = false;

	private boolean isHighscoreUpdated = false;

	private int[][] dexterityHsLst = new int[10][6];

	private int[] newDexterityHs = new int[6];

	private int[] speechHsLst = new int[10];

	private int newSpeechHs = 0;

	private Dextero dextero;

	private boolean isEasyDexMode, isMediumDexMode,
			isHardDexMode = false;

	private enum GameState {
		MAIN_MENU, DEXTERY_MENU, READY_DEXTERITY, HARDMODE_DEX_RUNNING, EASYMODE_DEX_RUNNING, MEDIUMMODE_DEX_RUNNING, GAMEOVER_DEXTERITY, GAMEOVER_SPLASH, DEX_HIGHSCORES, SPEECH_MENU, SPEECH_MODE_RUNNING, GAMEOVER_SPEECH, SPEECH_HIGHSCORES,
		INSTRUCTIONS_DEXTERITY, INSTRUCTIONS_SPEECH
	}

	private GameState currentState = GameState.MAIN_MENU;

	public GameWorld(Button button, Dextero dextero) {

		this.dextero = dextero;
		fingerPlacer = new FingerPlacer();
		indicator = new Indicator(fingerPlacer);
		numberGen = new NumberGenerator();
		sortAlg = new Algorithm();

		this.button = button;
		timer = 60.0f;

		setRandomPhrase();

		phrases[0] = "hello";
		phrases[1] = "apartments";
		phrases[2] = "creativity";
		phrases[3] = "mountains";
		phrases[4] = "fruits";
		phrases[5] = "education";
		phrases[6] = "perception";
		phrases[7] = "decision";
		phrases[8] = "inference";
		phrases[9] = "denial";
		phrases[10] = "flight";

	}

	public void update(float delta) {

		if (currentState == GameState.MAIN_MENU) {

			updateMainMenu();
		}
		
		if (currentState == GameState.DEXTERY_MENU) {

			updateDexterityMenu();
		}
		
		if(currentState == GameState.INSTRUCTIONS_DEXTERITY){
			updateDexInstructions();
			
		}

		if (currentState == GameState.READY_DEXTERITY) {
			updateReadyDexterity();
		}

		if (currentState == GameState.HARDMODE_DEX_RUNNING) {
			updateHardDexRunning(delta);
		}

		if (currentState == GameState.EASYMODE_DEX_RUNNING) {
			updateEasyDexRunning(delta);
		}

		if (currentState == GameState.MEDIUMMODE_DEX_RUNNING) {
			updateMediumDexRunning(delta);
		}

		if (currentState == GameState.GAMEOVER_SPLASH) {
			updateGOSplash(delta);
		}

		if (currentState == GameState.GAMEOVER_DEXTERITY) {

			updateGODexterity();
		}

		if (currentState == GameState.DEX_HIGHSCORES) {

			updateDexHs();
		}

		if (currentState == GameState.SPEECH_MENU) {

			updateSpeechMenu();
		}
		
		if(currentState == GameState.INSTRUCTIONS_SPEECH){
			updateSpeechInstructions();
		}

		if (currentState == GameState.SPEECH_MODE_RUNNING) {
			updateSpeechPlaying();
		}

		if (currentState == GameState.GAMEOVER_SPEECH) {
			updateGOSpeech();
		}

		if (currentState == GameState.SPEECH_HIGHSCORES) {
			updateSpeechHs();
		}
	}

	public float getTimer() {
		return timer;
	}

	public Button getButton() {
		return button;
	}

	public FingerPlacer getFingerPlacer() {
		return fingerPlacer;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public NumberGenerator getNumberGenerator() {
		return numberGen;
	}

	public Algorithm getAlgorithm() {
		return sortAlg;
	}

	public int getComboCount() {
		return comboCount;
	}

	public int getComboCap() {
		return comboCap;
	}

	public void incrementF1Score(int n) {
		f1Score += n;
	}

	public void incrementF2Score(int n) {
		f2Score += n;
	}

	public void incrementF3Score(int n) {
		f3Score += n;
	}

	public void incrementF4Score(int n) {
		f4Score += n;
	}

	public void decrementF1Score(int n) {
		f1Score -= n;
	}

	public void decrementF2Score(int n) {
		f2Score -= n;
	}

	public void decrementF3Score(int n) {
		f3Score -= n;
	}

	public void decrementF4Score(int n) {
		f4Score -= n;
	}

	public int getF1Score() {
		return f1Score;
	}

	public int getF2Score() {
		return f2Score;
	}

	public int getF3Score() {
		return f3Score;
	}

	public int getF4Score() {
		return f4Score;
	}

	public int getF1Errors() {

		return f1Errors;
	}

	public int getF2Errors() {

		return f2Errors;
	}

	public int getF3Errors() {

		return f3Errors;
	}

	public int getF4Errors() {

		return f4Errors;
	}

	public void incrementF1Errors(int n) {

		f1Errors += n;
	}

	public void incrementF2Errors(int n) {

		f2Errors += n;
	}

	public void incrementF3Errors(int n) {

		f3Errors += n;
	}

	public void incrementF4Errors(int n) {

		f4Errors += n;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public int getTotalErrors() {
		return totalErrors;
	}

	public void setTotalScore() {
		totalScore = f1Score + f2Score + f3Score + f4Score;
	}

	public void setTotalErrors() {
		totalErrors = f1Errors + f2Errors + f3Errors + f4Errors;
	}

	public void setComboCount(int i) {
		comboCount = i;

	}

	public void incrementComboCount(int i) {
		comboCount += i;
	}

	public void setComboCap() {
		comboCap = comboCap * 2;
	}

	public void resetDexterityStats() {

		Arrays.fill(fingerPlacer.getPlacerCoords(), null);
		Arrays.fill(indicator.getIndicatorCoords(), null);
		comboCount = 0;
		comboCap = 2;
		f1Score = 0;
		f2Score = 0;
		f3Score = 0;
		f4Score = 0;

		totalScore = 0;

		f1Errors = 0;
		f2Errors = 0;
		f3Errors = 0;
		f4Errors = 0;

		totalErrors = 0;
		timer = 60.0f;

		if (currentState == GameState.DEXTERY_MENU
				|| currentState == GameState.MAIN_MENU
				|| currentState == GameState.SPEECH_MENU) {
			isEasyDexMode = false;
			isMediumDexMode = false;
			isHardDexMode = false;
		}

		getFingerPlacer().resetVariables();

	}

	public void updateMainMenu() {

		currentState = GameState.MAIN_MENU;

		button.getBtnDexterityMenu().setVisible(true);
		button.getBtnSpeechMenu().setVisible(true);

	
		button.getBtnEasyDexMode().setVisible(false);
		button.getBtnMediumDexMode().setVisible(false);
		button.getBtnHardDexMode().setVisible(false);
		button.getBtnMainMenu().setVisible(false);
		button.getBtnSpeechPlay().setVisible(false);
		button.getBtnDexInstructions().setVisible(false);
		button.getBtnSpeechInstr().setVisible(false);

		if (button.getBtnDexterityMenu().isPressed()) {
			updateDexterityMenu();
		} else if (button.getBtnSpeechMenu().isPressed()) {
			updateSpeechMenu();
		}

	}

	public void updateDexterityMenu() {

		currentState = GameState.DEXTERY_MENU;

		resetDexterityStats();

		button.getBtnDexInstrToMenu().setVisible(false);

		button.getBtnReadyToDexMenu().setVisible(false);
		button.getBtnDexterityMenu().setVisible(false);
		button.getBtnSpeechMenu().setVisible(false);
		
		button.getBtnHighscores().setVisible(false);
		button.getBtnGOToDexMenu().setVisible(false);
		button.getBtnPlayAgain().setVisible(false);

		button.getBtnEasyDexMode().setVisible(true);
		button.getBtnMediumDexMode().setVisible(true);
		button.getBtnHardDexMode().setVisible(true);
		button.getBtnMainMenu().setVisible(true);
		button.getBtnDexInstructions().setVisible(true);

		if (button.getBtnMainMenu().isPressed()) {
			updateMainMenu();
		} else if (button.getBtnEasyDexMode().isPressed()) {
			currentState = GameState.READY_DEXTERITY;
			isEasyDexMode = true;
			isMediumDexMode = false;
			isHardDexMode = false;
		} else if (button.getBtnMediumDexMode().isPressed()) {
			currentState = GameState.READY_DEXTERITY;
			isEasyDexMode = false;
			isMediumDexMode = true;
			isHardDexMode = false;

		} else if (button.getBtnHardDexMode().isPressed()) {
			currentState = GameState.READY_DEXTERITY;
			isEasyDexMode = false;
			isMediumDexMode = false;
			isHardDexMode = true;
		} else if(button.getBtnDexInstructions().isPressed()){
			currentState = GameState.INSTRUCTIONS_DEXTERITY;
			
		}

	}
	
	public void updateDexInstructions(){
		button.getBtnEasyDexMode().setVisible(false);
		button.getBtnMediumDexMode().setVisible(false);
		button.getBtnHardDexMode().setVisible(false);
		button.getBtnMainMenu().setVisible(false);
		button.getBtnDexInstructions().setVisible(false);
		
		button.getBtnDexInstrToMenu().setVisible(true);

		if(button.getBtnDexInstrToMenu().isPressed()){
			currentState = GameState.DEXTERY_MENU;
		}
		
	}
	
	
	public void updateReadyDexterity() {

		currentState = GameState.READY_DEXTERITY;

		button.getBtnEasyDexMode().setVisible(false);
		button.getBtnMediumDexMode().setVisible(false);
		button.getBtnHardDexMode().setVisible(false);
		button.getBtnMainMenu().setVisible(false);
		button.getBtnDexInstructions().setVisible(false);

		button.getBtnReadyToDexMenu().setVisible(true);

		button.getBtnHighscores().setVisible(false);
		button.getBtnGOToDexMenu().setVisible(false);
		button.getBtnPlayAgain().setVisible(false);

		isHighscoreUpdated = false;

		if (button.getBtnReadyToDexMenu().isPressed()) {
			updateDexterityMenu();
		}

		else if (fingerPlacer.arePlacersFilled()) {
			if (isEasyDexMode) {
				currentState = GameState.EASYMODE_DEX_RUNNING;
			} else if (isMediumDexMode) {
				currentState = GameState.MEDIUMMODE_DEX_RUNNING;
			} else if (isHardDexMode) {
				currentState = GameState.HARDMODE_DEX_RUNNING;
			}
		}
	}

	public void updateHardDexRunning(float delta) {
		this.timer -= delta;
		button.getBtnReadyToDexMenu().setVisible(false);

		isDexModeActive = true;

		if (getComboCount() == getComboCap()) {
			timer = timer + 5.00f;

			setComboCount(0);
			setComboCap();

		}

		if (timer <= 0.0f) {
			currentState = GameState.GAMEOVER_SPLASH;
		}
	}

	public void updateEasyDexRunning(float delta) {
		this.timer -= delta;
		button.getBtnReadyToDexMenu().setVisible(false);

		isDexModeActive = true;

		if (getComboCount() == getComboCap()) {
			timer = timer + 5.00f;

			setComboCount(0);
			setComboCap();

		}
		if (timer <= 0.0f) {
			currentState = GameState.GAMEOVER_SPLASH;

		}
	}

	public void updateMediumDexRunning(float delta) {
		this.timer -= delta;
		button.getBtnReadyToDexMenu().setVisible(false);

		isDexModeActive = true;
		if (getComboCount() == getComboCap()) {
			timer = timer + 5.00f;

			setComboCount(0);
			setComboCap();

		}
		if (timer <= 0.0f) {

			currentState = GameState.GAMEOVER_SPLASH;

		}
	}

	public void updateGOSplash(float delta) {
		currentState = GameState.GAMEOVER_SPLASH;

		timer -= delta;

		button.getBtnSpeak().setVisible(false);
		button.getBtnEndSpeechPlay().setVisible(false);

		if (timer <= -2.0) {
			if (isDexModeActive) {
				updateGODexterity();
			} else {
				updateGOSpeech();
			}

		}
	}

	public void updateGODexterity() {
		currentState = GameState.GAMEOVER_DEXTERITY;

		isDexModeActive = false;

		if (!isHighscoreUpdated) {
			dextero.actionResolver.insertHighscore();
			dexterityHsLst = dextero.actionResolver.getHighscoreLst();
		}

		button.getBtnHighscores().setVisible(true);
		button.getBtnGOToDexMenu().setVisible(true);
		button.getBtnPlayAgain().setVisible(true);

		button.getBtnHsToGO().setVisible(false);

		if (button.getBtnHighscores().isPressed()) {
			updateDexHs();

		} else if (button.getBtnGOToDexMenu().isPressed()) {
			updateDexterityMenu();

		} else if (button.getBtnPlayAgain().isPressed()) {
			resetDexterityStats();
			updateReadyDexterity();

		}

	}

	public void updateDexHs() {
		currentState = GameState.DEX_HIGHSCORES;

		button.getBtnHighscores().setVisible(false);
		button.getBtnGOToDexMenu().setVisible(false);
		button.getBtnPlayAgain().setVisible(false);

		button.getBtnHsToGO().setVisible(true);

		if (button.getBtnHsToGO().isPressed()) {
			updateGODexterity();
		}

	}

	public void updateSpeechMenu() {
		currentState = GameState.SPEECH_MENU;

		button.getBtnDexterityMenu().setVisible(false);
		button.getBtnSpeechMenu().setVisible(false);
		button.getBtnSpeechInstrToMenu().setVisible(false);
		
		button.getBtnSpeechPlay().setVisible(true);
		button.getBtnMainMenu().setVisible(true);
		button.getBtnSpeechInstr().setVisible(true);

		button.getBtnSpeechHs().setVisible(false);
		button.getBtnGOToSpeechMenu().setVisible(false);
		button.getBtnSpeechPlayAgain().setVisible(false);
		button.getBtnSpeak().setVisible(false);

		isHighscoreUpdated = false;

		if (button.getBtnSpeechPlay().isPressed()) {
			speechScore = 0;
			speechAttempts = 2;
			isSpeechCorrect = -1;
			setRandomPhrase();
			updateSpeechPlaying();
		} else if (button.getBtnMainMenu().isPressed()) {
			updateMainMenu();
		} else if(button.getBtnSpeechInstr().isPressed()){
			currentState = GameState.INSTRUCTIONS_SPEECH;
		}

	}
	
	
	public void updateSpeechInstructions(){
		button.getBtnSpeechPlay().setVisible(false);
		button.getBtnMainMenu().setVisible(false);
		button.getBtnSpeechInstr().setVisible(false);
		
		button.getBtnSpeechInstrToMenu().setVisible(true);

		if(button.getBtnSpeechInstrToMenu().isPressed()){
			currentState = GameState.SPEECH_MENU;
		}
		
	}


	public void updateSpeechPlaying() {
		currentState = GameState.SPEECH_MODE_RUNNING;

		button.getBtnSpeechPlay().setVisible(false);
		button.getBtnSpeak().setVisible(true);
		button.getBtnEndSpeechPlay().setVisible(true);

		button.getBtnSpeechHs().setVisible(false);
		button.getBtnGOToSpeechMenu().setVisible(false);
		button.getBtnSpeechPlayAgain().setVisible(false);
		button.getBtnMainMenu().setVisible(false);
		button.getBtnSpeechInstr().setVisible(false);

		isHighscoreUpdated = false;

		if (button.getBtnSpeak().isPressed()) {
			dextero.actionResolver.implementVoiceRecognizer();
		} else if (button.getBtnEndSpeechPlay().isPressed()) {
			timer = 0;
			currentState = GameState.GAMEOVER_SPLASH;
		}

		if (isSpeechCorrect == 0) {
			speechAttempts--;
			isSpeechCorrect = -1;
		}

		if (speechAttempts == 0) {
			timer = 0;
			currentState = GameState.GAMEOVER_SPLASH;
		}
	}

	public void updateGOSpeech() {
		currentState = GameState.GAMEOVER_SPEECH;

		if (!isHighscoreUpdated) {
			dextero.actionResolver.insertHighscore();
			speechHsLst = dextero.actionResolver.getSpeechHsLst();
		}

		button.getBtnSpeechHs().setVisible(true);
		button.getBtnGOToSpeechMenu().setVisible(true);
		button.getBtnSpeechPlayAgain().setVisible(true);

		button.getBtnHsToGO().setVisible(false);
		button.getBtnSpeak().setVisible(false);

		if (button.getBtnSpeechHs().isPressed()) {
			updateSpeechHs();
		} else if (button.getBtnGOToSpeechMenu().isPressed()) {
			updateSpeechMenu();
		} else if (button.getBtnSpeechPlayAgain().isPressed()) {
			speechScore = 0;
			speechAttempts = 2;
			isSpeechCorrect = -1;
			setRandomPhrase();
			updateSpeechPlaying();
		}

	}

	public void updateSpeechHs() {
		currentState = GameState.SPEECH_HIGHSCORES;

		button.getBtnSpeechHs().setVisible(false);
		button.getBtnGOToSpeechMenu().setVisible(false);
		button.getBtnSpeechPlayAgain().setVisible(false);

		button.getBtnHsToGO().setVisible(true);

		if (button.getBtnHsToGO().isPressed()) {
			updateGOSpeech();
		}
	}
	
	public boolean isDexInstructions(){
		return currentState == GameState.INSTRUCTIONS_DEXTERITY;
	}

	public boolean isReadyDexterity() {
		return currentState == GameState.READY_DEXTERITY;
	}

	public boolean isHardModeRunning() {
		return currentState == GameState.HARDMODE_DEX_RUNNING;
	}

	public boolean isEasyModeRunning() {
		return currentState == GameState.EASYMODE_DEX_RUNNING;
	}

	public boolean isMediumModeRunning() {
		return currentState == GameState.MEDIUMMODE_DEX_RUNNING;
	}

	public boolean isGameOverSplash() {
		return currentState == GameState.GAMEOVER_SPLASH;
	}

	public boolean isGameOverDexterity() {
		return currentState == GameState.GAMEOVER_DEXTERITY;
	}

	public boolean isDexterityHs() {
		return currentState == GameState.DEX_HIGHSCORES;
	}

	public boolean isSpeechMenu() {
		return currentState == GameState.SPEECH_MENU;
	}
	
	public boolean isSpeechInstructions(){
		return currentState == GameState.INSTRUCTIONS_SPEECH;
	}

	public boolean isSpeechModeRunning() {
		return currentState == GameState.SPEECH_MODE_RUNNING;
	}

	public boolean isSpeechModeOver() {
		return currentState == GameState.GAMEOVER_SPEECH;
	}

	public boolean isSpeechHs() {
		return currentState == GameState.SPEECH_HIGHSCORES;
	} 

	public String[] getPhrasesLst() {
		return phrases;
	}

	public int setRandomPhrase() {
		return randomPhrase = numberGen.randomPhrasePicker(randomPhrase);
	}

	public int getRandomPhrase() {
		return randomPhrase;
	}

	public void incrementSpeechScore(int n) {
		speechScore += n;
	}

	public int getSpeechScore() {
		return speechScore;
	}

	public void setIsSpeechCorrect(int n) {
		isSpeechCorrect = n;
	}

	public boolean isSpeechCorrect() {
		return isSpeechCorrect == 1;
	}

	public int getSpeechAttempts() {
		return speechAttempts;
	} 

	public boolean isEasyDexMode() {
		return isEasyDexMode;
	}

	public boolean isMediumDexMode() {
		return isMediumDexMode;
	}

	public boolean isHardDexMode() {
		return isHardDexMode;
	}

	public boolean isHighscoreUpdated() {
		return isHighscoreUpdated;
	}

	public void setHighscoreUpdated(boolean b) {
		isHighscoreUpdated = b;
	} 

	public int[][] getDexterityHsLst() {
		return dexterityHsLst;
	}

	public void setNewHighscore(int[] arr) {
		this.newDexterityHs = arr;
	}

	public void setDexterityHsLst(int[][] arr) {
		this.dexterityHsLst = arr;
	}

	public int getNewSpeechHs() {
		return newSpeechHs;
	}

	public int[] getSpeechHsLst() {
		return speechHsLst;
	}

	public void setNewSpeechHs(int n) {
		this.newSpeechHs = n;
	}

	public void setSpeechHsLst(int[] arr) {
		this.speechHsLst = arr;
	}

	public boolean hasLstFoundNewScore(int i) {
		/*this checks whether the arbitrary index value inside the row, column high scores has the exact same statistics as 
		  an existing high score*/
		if (dexterityHsLst[i][0] == newDexterityHs[0]
				&& dexterityHsLst[i][1] == newDexterityHs[1]
				&& dexterityHsLst[i][2] == newDexterityHs[2]
				&& dexterityHsLst[i][3] == newDexterityHs[3]
				&& dexterityHsLst[i][4] == newDexterityHs[4]
				&& dexterityHsLst[i][5] == newDexterityHs[5]) {
			return true;
		}
		return false;
	} 
}
