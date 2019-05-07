package com.mygdx.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteAccessor implements TweenAccessor<Sprite> {
	
	/* Some code was followed to achieve the Splash screen: 
	 * http://www.kilobolt.com/day-11-supporting-iosandroid--splashscreen-menus-and-tweening.html 
	 * 
	 * */
	
	public static final int ALPHA = 1;
	
	
/*This method gets alpha variable and sends it over to the tween engine
 * for manipulation*/
	@Override
	public int getValues(Sprite arg0, int arg1, float[] arg2) {
		switch (arg1) {
        case ALPHA:
            arg2[0] = arg0.getColor().a;
            return 1;
        default:
            return 0;
        }
	}

	/*new alpha value returned inside this method which modifies the appearance
	 * of the sprite gradually */
	@Override
	public void setValues(Sprite arg0, int arg1, float[] arg2) {
		switch (arg1) {
		case ALPHA:
			arg0.setColor(1, 1, 1, arg2[0]);
			break;
		}
		
	}

}
