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
		if (keyCode == Keys.UP) {
			hero.setMoveN(true);
		} else if (keyCode == Keys.DOWN) {
			hero.setMoveS(true);
		} else if (keyCode == Keys.RIGHT) {
			hero.setMoveE(true);
		} else if (keyCode == Keys.LEFT) {
			hero.setMoveW(true);
		}
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
			hero.setMoveToTouchPosition(true);
		}
		if (button == Buttons.RIGHT) {
			hero.setMoveToTouchPosition(false);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
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
