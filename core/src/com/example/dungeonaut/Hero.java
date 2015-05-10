package com.example.dungeonaut;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Hero extends Rectangle {

	private static final long serialVersionUID = 1L;
	private boolean moveT = false;

	private int speed = 200;

	private Vector3 touchPos = new Vector3();
	private Vector2 currentPos = new Vector2();
	private Vector2 direction = new Vector2();
	private Vector2 velocity;
	private Vector2 movement = new Vector2();

	public Hero(int speed, Vector2 position) {
		this.setPosition(position);
		this.speed = speed;
	}

	void moveNorthByKey() {
		y += speed * Gdx.graphics.getDeltaTime();
	}

	void moveSouthByKey() {
		y -= speed * Gdx.graphics.getDeltaTime();
	}

	void moveEastByKey() {
		x += speed * Gdx.graphics.getDeltaTime();
	}

	void moveWestByKey() {
		x -= speed * Gdx.graphics.getDeltaTime();
	}

	void updateMotion() {
		if (moveT) {
			movement.set(velocity).scl(Gdx.graphics.getDeltaTime());
			this.setPosition(currentPos.add(movement));

			// TODO: This check number needs to be dynamic (in case the speed is increased for instance)
			if (touchPos.dst2(currentPos.x, currentPos.y, 0) < 3) {
				currentPos.x = touchPos.x;
				currentPos.y = touchPos.y;
				this.setPosition(currentPos);
				moveT = false;
			}
		}
	}

	public void moveToTouchPosition(int screenX, int screenY, boolean moveT) {
		this.moveT = moveT;

		touchPos.set(screenX, screenY, 0);
		GameScreen.getCamera().unproject(touchPos);

		currentPos = new Vector2(this.x + (this.getWidth() / 2), this.y + (this.getHeight() / 2));
		direction.set(new Vector2(touchPos.x, touchPos.y).sub(currentPos).nor());
		velocity = new Vector2(direction).scl(speed);
	}

	public void stopMoveByTouch() {
		moveT = false;
	}

	@Override
	public Rectangle setPosition(Vector2 position) {
		this.x = position.x - (this.width / 2);
		this.y = position.y - (this.height / 2);

		return this;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Vector3 getTouchPos(){
		return touchPos;
	}
	
	public Vector2 getCurrentPosition() {
		return currentPos;
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
