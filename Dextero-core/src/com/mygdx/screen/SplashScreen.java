package com.mygdx.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.Dextero.Dextero;
import com.mygdx.dexhelpers.AssetLoader;
import com.mygdx.tween.SpriteAccessor;

/* Some code was followed to achieve the Splash screen: 
 * http://www.kilobolt.com/day-11-supporting-iosandroid--splashscreen-menus-and-tweening.html 
 * 
 * */

public class SplashScreen implements Screen {
	
	private TweenManager tweenManager;
	private SpriteBatch batcher;
	private Sprite sprite;
	private Dextero dextero;
	private GameScreen gameScreen;
	private SpriteAccessor spriteAccessor;
	
	
	
	public SplashScreen(Dextero dextero){
		this.dextero = dextero;
		spriteAccessor = new SpriteAccessor();
		batcher = new SpriteBatch();
	}
	
	
	@Override
	public void render(float delta) {
		//updates the sprite's appearance constantly while it
		//is being tweened
	     	tweenManager.update(delta);
	        Gdx.gl.glClearColor(1, 1, 1, 1);
	        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        batcher.begin();
	        sprite.draw(batcher);
	        batcher.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	
	private void setupTween(){
		  Tween.registerAccessor(Sprite.class, spriteAccessor);
	        tweenManager = new TweenManager();

	        //gets the callback once tweening process has ended
	        TweenCallback cb = new TweenCallback() {

				@Override
				public void onEvent(int arg0, BaseTween<?> arg1) {
				//sets the game screen
				dextero.setScreen(gameScreen = new GameScreen(dextero, batcher));
				}
	        };
	        
	        //does the tweening of the sprite 
	        Tween.to(sprite, SpriteAccessor.ALPHA, .8f).target(1)
			.ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .4f)
			.setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
			.start(tweenManager);
	        
		
	}
	
	

	@Override
	public void show() {
		   sprite = new Sprite(AssetLoader.splashScreen);
	       sprite.setColor(1, 1, 1, 0);

	        float width = Gdx.graphics.getWidth();
	        float height = Gdx.graphics.getHeight();
	        float desiredWidth = width * .7f;
	        float scale = desiredWidth / sprite.getWidth();

	        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
	        sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height / 2)
	                - (sprite.getHeight() / 2));
	        setupTween();
	     
		
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
	
	public GameScreen getGameScreen(){
		return gameScreen;
	}

}
