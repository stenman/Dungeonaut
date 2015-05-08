package com.example.dungeonaut.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.example.dungeonaut.Dungeonaut;

public class DesktopLauncher {

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Dungeonaut";
		config.width = 800;
		config.height = 480;

		new LwjglApplication(new Dungeonaut(), config);
	}
}