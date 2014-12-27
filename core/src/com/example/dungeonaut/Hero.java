package com.example.dungeonaut;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Hero extends Rectangle {
	private static final long serialVersionUID = 1L;
	private boolean moveN;
	private boolean moveS;
	private boolean moveE;
	private boolean moveW;

	public Hero() {
	}

	void updateMotion() {

		if (moveN) {
			y += 300 * Gdx.graphics.getDeltaTime();
		}
		if (moveS) {
			y -= 300 * Gdx.graphics.getDeltaTime();
		}
		if (moveE) {
			x += 300 * Gdx.graphics.getDeltaTime();
		}
		if (moveW) {
			x -= 300 * Gdx.graphics.getDeltaTime();
		}
	}

	public void setMoveN(boolean t) {
		if (moveN && t) {
			moveS = false;
		}
		moveN = true;
	}

	public void setMoveS(boolean t) {
		if (moveS && t) {
			moveN = false;
		}
		moveS = true;
	}

	public void setMoveE(boolean t) {
		if (moveE && t) {
			moveW = false;
		}
		moveE = true;
	}

	public void setMoveW(boolean t) {
		if (moveW && t) {
			moveE = false;
		}
		moveW = true;
	}
}
