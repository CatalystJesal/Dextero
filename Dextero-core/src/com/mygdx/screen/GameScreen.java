package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Dextero.Dextero;
import com.mygdx.dexhelpers.InputHandler;
import com.mygdx.gameworld.GameRenderer;
import com.mygdx.gameworld.GameWorld;
import com.mygdx.gameworld.ScoreManager;
import com.mygdx.ui.Button;

public class GameScreen implements Screen {

	Dextero dextero;

	private GameWorld world;
	private GameRenderer render;
	private OrthographicCamera camera;
	private Viewport viewport;
	private Button button;
	private ScoreManager scoreManager;

	private Stage stage;

	private SpriteBatch batcher;
	
	private InputProcessor inputProcessorButtons, inputProcessorGameplay;
	private InputMultiplexer multiplexer;

	public GameScreen(final Dextero dextero, SpriteBatch batch) {
		this.dextero = dextero;
		this.batcher = batch;
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(480, 270, camera);
		viewport.apply();
		button = new Button();
		world = new GameWorld(button, this.dextero);
		render = new GameRenderer(world, camera, batcher);
		scoreManager = new ScoreManager(world, camera, render);
		
		stage = new Stage(viewport);
		
		stage.addActor(button.getBtnDexterityMenu());
		stage.addActor(button.getBtnHardDexMode());
		stage.addActor(button.getBtnEasyDexMode());
		stage.addActor(button.getBtnMediumDexMode());
		stage.addActor(button.getBtnDexInstructions());
		stage.addActor(button.getBtnDexInstrToMenu());
		stage.addActor(button.getBtnGOToDexMenu());
		stage.addActor(button.getBtnHighscores());
		stage.addActor(button.getBtnPlayAgain());
		stage.addActor(button.getBtnReadyToDexMenu());
		stage.addActor(button.getBtnMainMenu());
		stage.addActor(button.getBtnHsToGO());
		stage.addActor(button.getBtnSpeechMenu());
		stage.addActor(button.getBtnSpeechInstr());
		stage.addActor(button.getBtnSpeechInstrToMenu());
		stage.addActor(button.getBtnSpeechPlay());
		stage.addActor(button.getBtnEndSpeechPlay());
		stage.addActor(button.getBtnSpeak());
		stage.addActor(button.getBtnSpeechHs());
		stage.addActor(button.getBtnSpeechPlayAgain());
		stage.addActor(button.getBtnGOToSpeechMenu());
		
		inputProcessorButtons  = stage;
		inputProcessorGameplay = new InputHandler(world, camera);
		
		multiplexer = new InputMultiplexer();
		
		multiplexer.addProcessor(inputProcessorButtons);
		multiplexer.addProcessor(inputProcessorGameplay);
		Gdx.input.setInputProcessor(multiplexer);

	}

	@Override
	public void render(float delta) {

		world.update(delta);
		render.render();
		scoreManager.update();
	
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	


	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public GameWorld getWorld() {
		return world;
	}

}
