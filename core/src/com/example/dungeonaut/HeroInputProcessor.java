package com.example.dungeonaut;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class HeroInputProcessor implements InputProcessor {

	Hero hero;

	public HeroInputProcessor(Hero hero) {
		this.hero = hero;
	}

	@Override
	public boolean keyDown(int keyCode) {
		return false;
	}

	@Override
	public boolean keyUp(int keyCode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.LEFT) {
			hero.moveToTouchPosition(true);
		}
		if (button == Buttons.RIGHT) {
			hero.moveToTouchPosition(false);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		hero.moveToTouchPosition(true);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
