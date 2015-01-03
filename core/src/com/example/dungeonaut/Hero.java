package com.example.dungeonaut;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Hero extends Rectangle {

	private static final long serialVersionUID = 1L;
	private boolean moveN = false;
	private boolean moveS = false;
	private boolean moveE = false;
	private boolean moveW = false;
	private boolean moveT = false;

	private int speed = 200;

	private Vector3 touch = new Vector3();
	private Vector2 currentPosition = new Vector2();
	private Vector2 direction = new Vector2();
	private Vector2 velocity;
	private Vector2 movement = new Vector2();

	public Hero(int speed, Vector2 position) {
		this.setPosition(position);
		this.speed = speed;
	}

	void updateMotion() {
		if (moveN) {
			y += speed * Gdx.graphics.getDeltaTime();
		}
		if (moveS) {
			y -= speed * Gdx.graphics.getDeltaTime();
		}
		if (moveE) {
			x += speed * Gdx.graphics.getDeltaTime();
		}
		if (moveW) {
			x -= speed * Gdx.graphics.getDeltaTime();
		}
		if (moveT) {
			movement.set(velocity).scl(Gdx.graphics.getDeltaTime());
			this.setPosition(currentPosition.add(movement));

			// TODO: This is highly approximate and should not be used as a means to stop the Sprite.
			if (touch.dst(currentPosition.x, currentPosition.y, 0) < 4) {
				moveT = false;
			}
		}
	}

	public void setMoveToTouchPosition(boolean moveT) {
		this.moveN = false;
		this.moveS = false;
		this.moveE = false;
		this.moveW = false;
		this.moveT = moveT;

		this.touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		GameScreen.getCamera().unproject(touch);

		currentPosition = new Vector2(this.x, this.y);

		direction.set(new Vector2(touch.x, touch.y)).sub(currentPosition).nor();

		velocity = new Vector2(direction).scl(speed);
	}

	public void setMoveN(boolean t) {
		if (moveN && t) {
			moveS = false;
			moveT = false;
		}
		moveN = true;
	}

	public void setMoveS(boolean t) {
		if (moveS && t) {
			moveN = false;
			moveT = false;
		}
		moveS = true;
	}

	public void setMoveE(boolean t) {
		if (moveE && t) {
			moveW = false;
			moveT = false;
		}
		moveE = true;
	}

	public void setMoveW(boolean t) {
		if (moveW && t) {
			moveE = false;
			moveT = false;
		}
		moveW = true;
	}

	public void stop() {
		moveN = false;
		moveS = false;
		moveE = false;
		moveW = false;
		moveT = false;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Vector3 getTouch() {
		return touch;
	}

	public Vector2 getCurrentPosition() {
		return currentPosition;
	}

	public Vector2 getDirection() {
		return direction;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Vector2 getMovement() {
		return movement;
	}
}
