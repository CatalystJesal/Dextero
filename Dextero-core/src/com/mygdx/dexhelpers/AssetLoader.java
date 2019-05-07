package com.mygdx.dexhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class AssetLoader {


	public static BitmapFont blackFont, greenFont;
	public static Texture splashTex;
	public static TextureRegion splashScreen;
	
	
public static void load(){
/*assets are loaded into the application when it begins, these are used in other classes.
 * predominantly by the GameRenderer class
 */
	splashTex = new Texture(Gdx.files.internal("splash_screen.jpg"));
	splashScreen = new TextureRegion(splashTex, 0, 0, 336, 120);
	
	blackFont = new BitmapFont(Gdx.files.internal("text.fnt"));

	greenFont = new BitmapFont(Gdx.files.internal("newHighscore.fnt"));

	blackFont.setScale(0.50f,0.50f);
	greenFont.setScale(0.50f,0.50f);

	
}

//this function gets called when the application closes. Disposing the fonts.
public static void dispose(){
	
	blackFont.dispose();
	greenFont.dispose();
}

}
