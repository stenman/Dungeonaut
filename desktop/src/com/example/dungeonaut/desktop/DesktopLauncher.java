package com.example.dungeonaut.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.example.dungeonaut.Dungeonaut;

public class DesktopLauncher {

	// TODO: Make hero move at a set speed towards a touchdown
	// TODO: Make hero move a set distance at a set speed towards a keyDown
	// TODO: Create a grid of 16x16 squares
	// TODO: Make hero move exactly x square(s) (8 or 16 pixels) at a keyDown or touchDown
	// TODO: Spawn floortiles, rocks and trees
	// TODO: Fix collision detection with rocks and trees
	// TODO: Create squares or hexagons for hero movement
	
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Dungeonaut";
		config.width = 800;
		config.height = 480;

		new LwjglApplication(new Dungeonaut(), config);
	}
}