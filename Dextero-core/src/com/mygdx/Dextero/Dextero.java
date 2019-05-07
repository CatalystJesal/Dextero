package com.mygdx.Dextero;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.androidinterface.ActionResolver;
import com.mygdx.dexhelpers.AssetLoader;
import com.mygdx.screen.SplashScreen;

public class Dextero extends Game {
	
	
	public ActionResolver actionResolver;
	private SplashScreen splashScreen;
	
	public Dextero(ActionResolver ar){
		actionResolver = ar;
	}

	@Override
	public void create() {
		 Gdx.app.log("Dextero", "created");
	  	 AssetLoader.load();
		 setScreen(splashScreen = new SplashScreen(this));
		
	}
	
	@Override
	public void dispose(){
		
		super.dispose();
		AssetLoader.dispose();
	}
	
	
	public SplashScreen getSplashScreen(){
		return splashScreen;
	}
	
}
