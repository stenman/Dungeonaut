package com.example.dungeonaut.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.example.dungeonaut.Dungeonaut;

public class HtmlLauncher extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig() {
		return new GwtApplicationConfiguration(800, 480);
	}

	@Override
	public ApplicationListener getApplicationListener() {
		return new Dungeonaut();
	}
}