package com.mygdx.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Button {


	private TextureAtlas texAtlas;
	private TextButtonStyle buttonStyle;

	private TextButton dexterityMenu, dexInstructions,  dexInstrToMenu,hardDexMode, easyDexMode, mediumDexMode, gameOverDexMenu, btnHighscores, btnPlayAgain,
	readyToDexMenu, mainMenu, hsToGameOver, speechMenu, speechInstructions, speechInstrToMenu, speechPlay, speak, speechHs, speechPlayAgain, gameOverSpeechMenu, forfeitSpeechGame;

	private Skin skin;
	
	private BitmapFont font;
	

	public Button() {
		
		font = new BitmapFont(Gdx.files.internal("text.fnt"));
		font.setScale(0.40f,0.50f);
		skin = new Skin();
		texAtlas = new TextureAtlas("button.pack");
		skin.addRegions(texAtlas);
		
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button.up");
		buttonStyle.over = skin.getDrawable("button.down");
		buttonStyle.down = skin.getDrawable("button.down");
		buttonStyle.font = font;
		
		dexterityMenu = new TextButton("Dexterity Menu", buttonStyle);
		dexterityMenu.setBounds(176, 173, 134, 36);
		
		easyDexMode = new TextButton("Easy", buttonStyle);
		easyDexMode.setBounds(195, 130, 80, 30); 

		mediumDexMode = new TextButton("Medium", buttonStyle);
		mediumDexMode.setBounds(195, 80, 80, 30); 

		hardDexMode = new TextButton("Hard", buttonStyle);
		hardDexMode.setBounds(195, 30, 80, 30); 
		
		dexInstructions = new TextButton("How to Play?", buttonStyle);
		dexInstructions.setBounds(187f, 180, 97, 33); 
		
		gameOverDexMenu = new TextButton("Dexterity Menu", buttonStyle);
		gameOverDexMenu.setBounds(185, 90, 134, 36);
		
		btnHighscores = new TextButton("Highscores", buttonStyle);
		btnHighscores.setBounds(185, 140, 100, 40);

		btnPlayAgain = new TextButton("Play Again", buttonStyle);
		btnPlayAgain.setBounds(185, 40, 100, 30);
		
		readyToDexMenu = new TextButton("Back", buttonStyle);
		readyToDexMenu.setBounds(402, 160, 67, 28);
		
		mainMenu = new TextButton("Back", buttonStyle);
		mainMenu.setBounds(419, 5, 48,30);
		
		dexInstrToMenu = new TextButton("Back", buttonStyle);
		dexInstrToMenu.setBounds(419, 5, 48,30);
		
		hsToGameOver = new TextButton("Back", buttonStyle);
		hsToGameOver.setBounds(419, 5, 48,30);
		
		speechMenu = new TextButton("Speech Mode", buttonStyle);
		speechMenu.setBounds(176, 123, 134, 36);
		
		speechPlay = new TextButton("Play", buttonStyle);
		speechPlay.setBounds(195,80,80,30);
		
		speechInstructions = new TextButton("How to Play?", buttonStyle);
		speechInstructions.setBounds(187f,130, 97, 33);
		
		speechInstrToMenu = new TextButton("Back", buttonStyle);
		speechInstrToMenu.setBounds(419, 5, 48,30);
		
		forfeitSpeechGame = new TextButton("Forfeit", buttonStyle);
		forfeitSpeechGame.setBounds(419, 5, 48,30);
		
		speak = new TextButton("Speak", buttonStyle);
		speak.setBounds(205,100,70,30);
		
		speechHs = new TextButton("Highscores", buttonStyle);
		speechHs.setBounds(185,140,120,30);

		speechPlayAgain = new TextButton("Play Again", buttonStyle);
		speechPlayAgain.setBounds(185,40, 120, 30);
		
		gameOverSpeechMenu = new TextButton("Speech Menu", buttonStyle);
		gameOverSpeechMenu.setBounds(185, 90, 120, 30);
		
		dexterityMenu.setVisible(false);
		hardDexMode.setVisible(false);
		easyDexMode.setVisible(false);
		mediumDexMode.setVisible(false);
		dexInstructions.setVisible(false);
		dexInstrToMenu.setVisible(false);
		gameOverDexMenu.setVisible(false);
		btnHighscores.setVisible(false);
		btnPlayAgain.setVisible(false);
		readyToDexMenu.setVisible(false);
		mainMenu.setVisible(false);
		hsToGameOver.setVisible(false);
		speechMenu.setVisible(false);
		speechInstructions.setVisible(false);
		speechInstrToMenu.setVisible(false);
		speechPlay.setVisible(false);
		forfeitSpeechGame.setVisible(false);
		speak.setVisible(false);
		speechHs.setVisible(false);
		speechPlayAgain.setVisible(false);
		gameOverSpeechMenu.setVisible(false);

	}
 

	public TextButton getBtnDexterityMenu() {
		return dexterityMenu;
	}
	
	public TextButton getBtnDexInstructions() {
		return dexInstructions;
	}
	
	public TextButton getBtnDexInstrToMenu() {
		return dexInstrToMenu;
	}

	public TextButton getBtnHardDexMode() {
		return hardDexMode;
	}

	public TextButton getBtnEasyDexMode() {
		return easyDexMode;
	}

	public TextButton getBtnMediumDexMode() {
		return mediumDexMode;
	}
	
	public TextButton getBtnGOToDexMenu() {
		return gameOverDexMenu;
	}

	public TextButton getBtnHighscores() {
		return btnHighscores;
	}

	public TextButton getBtnPlayAgain() {
		return btnPlayAgain;
	}

	public TextButton getBtnReadyToDexMenu() {
		return readyToDexMenu;
	}

	public TextButton getBtnMainMenu() {
		return mainMenu;
	}

	public TextButton getBtnHsToGO() {
		return hsToGameOver;
	}

	public TextButton getBtnSpeechMenu() {
		return speechMenu;
	}

	public TextButton getBtnSpeechInstr() {
		return speechInstructions;
	}
	
	public TextButton getBtnSpeechInstrToMenu() {
		return speechInstrToMenu;
	}

	public TextButton getBtnSpeechPlay() {
		return speechPlay;
	}
	
	public TextButton getBtnEndSpeechPlay(){
		return forfeitSpeechGame;
	}

	public TextButton getBtnSpeak() {
		return speak;
	}

	public TextButton getBtnSpeechHs() {
		return speechHs;
	}

	public TextButton getBtnSpeechPlayAgain() {
		return speechPlayAgain;
	}

	public TextButton getBtnGOToSpeechMenu() {
		return gameOverSpeechMenu;
	}

    
}
