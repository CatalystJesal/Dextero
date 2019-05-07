package com.mygdx.Dextero.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mygdx.Dextero.Dextero;
import com.mygdx.androidinterface.ActionResolver;

public class HtmlLauncher extends GwtApplication {

	private static ActionResolver actionResolver;
        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new Dextero(actionResolver);
        }
}