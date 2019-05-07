package com.mygdx.Dextero.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.Dextero.Dextero;
import com.mygdx.androidinterface.ActionResolver;


public class DesktopLauncher {
	
	private static ActionResolver actionResolver;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Dextero";
		config.width = 960;
        config.height = 540;
        
		new LwjglApplication(new Dextero(actionResolver), config);
	
	}
}
