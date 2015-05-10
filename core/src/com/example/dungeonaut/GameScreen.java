package com.example.dungeonaut;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

// TODO: Make hero follow mousedown/touchhold
// TODO: Spawn floortiles, rocks and trees
// TODO: Fix collision detection with rocks, trees and edges of "game area"
// TODO: (Cosmetics) Make a little circle-animation on touchdown
// TODO: Place random floortiles, rock and trees images

public class GameScreen implements Screen {

	private final Dungeonaut game;

	private BitmapFont font;

	private Music music_classical;
	private Sound sound_drop;

	private static OrthographicCamera camera;

	private Texture heroImage;
	private Hero hero;
	private Sprite heroSprite;
	private ShapeRenderer sr;

	private static final int NUM_TREES = 5;
	private static final int NUM_ROCKS = 15;
	private Texture floorImage1;
	private Texture rockImage1;
	private Texture treeImage1;
	private Array<Rectangle> floorTiles;
	private Array<Rectangle> rocks;
	private Array<Rectangle> trees;

	private Vector3 touchPos;

	private InputProcessor inputProcessor;

	private static final int screenWidth = 800;
	private static final int screenHeight = 480;

	public GameScreen(final Dungeonaut game) {
		this.game = game;

		// FONTS
		font = new BitmapFont();

		// CAMERA
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);

		// MUSIC
		music_classical = Gdx.audio.newMusic(Gdx.files.internal("Delibes-Notturno.mp3"));
		music_classical.setLooping(true);

		// SOUNDS
		sound_drop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

		// HERO
		hero = new Hero(150, new Vector2((screenWidth / 2), 240));
		heroImage = new Texture(Gdx.files.internal("bluebox.png"));
		heroImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		heroSprite = new Sprite(heroImage);
		hero.width = 16;
		hero.height = 16;

		// ENVIRONMENT
		floorImage1 = new Texture(Gdx.files.internal("floor_gravel_1.png"));
		floorTiles = new Array<Rectangle>();
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 30; j++) {
				Rectangle floorTile = new Rectangle((i * 16), (j * 16), 16, 16);
				floorTiles.add(floorTile);
			}
		}
		rockImage1 = new Texture(Gdx.files.internal("rock_1.png"));
		rocks = new Array<Rectangle>();

		Random rng = new Random();

		for (int i = 0; i < NUM_ROCKS; i++) {

			boolean overlapped = true;
			Rectangle rock = null;

			while (overlapped) {
				overlapped = false;
				int xPos = rng.nextInt(screenWidth - 16);
				int yPos = rng.nextInt(screenHeight - 16);
				rock = new Rectangle(xPos, yPos, 16, 16);
				for (Rectangle r : rocks) {
					if (rock.overlaps(r)) {
						overlapped = true;
					}
				}
				if (rock.overlaps(hero)) {
					overlapped = true;
				}
			}
			rocks.add(rock);
		}

		trees = new Array<Rectangle>();

		treeImage1 = new Texture(Gdx.files.internal("tree_1.png"));

		// OTHER
		sr = new ShapeRenderer();
		inputProcessor = new HeroInputProcessor(hero);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(inputProcessor);
		Gdx.input.setInputProcessor(inputMultiplexer);

		// TODO: Spawn stuff here
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		// SHAPERENDERER BEGIN------------------------------------------------------------------

		// SHAPERENDERER END------------------------------------------------------------------

		// SPRITEBATCH BEGIN------------------------------------------------------------------
		game.batch.begin();

		// Generate dungeon
		for (Rectangle fTile : floorTiles) {
			game.batch.draw(floorImage1, fTile.x, fTile.y);
		}
		for (Rectangle rock : rocks) {
			game.batch.draw(rockImage1, rock.x, rock.y);
		}

		game.batch.draw(heroSprite, hero.x, hero.y, hero.width, hero.height);

		printOnScreenInfo();

		game.batch.end();
		// SPRITEBATCH END--------------------------------------------------------------------

		sr.begin(ShapeType.Line);
		sr.setColor(0, 0, 0, 0);
		if (hero.getTouchPos() != null) {
			sr.line(hero.getCurrentPosition().x, hero.getCurrentPosition().y, hero.getTouchPos().x, hero.getTouchPos().y);
		}
		sr.end();

		if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
			hero.stopMoveByTouch();
			hero.moveNorthByKey();
		}

		if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)) {
			hero.stopMoveByTouch();
			hero.moveSouthByKey();
		}

		if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			hero.stopMoveByTouch();
			hero.moveEastByKey();
		}

		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
			hero.stopMoveByTouch();
			hero.moveWestByKey();
		}

		// Update Hero movement
		hero.updateMotion();

		// Screen edge checks
		if (hero.x < 0) {
			hero.x = 0;
		}
		if (hero.x > screenWidth - hero.width) {
			hero.x = screenWidth - hero.width;
		}
		if (hero.y < 0) {
			hero.y = 0;
		}
		if (hero.y > screenHeight - hero.height) {
			hero.y = screenHeight - hero.height;
		}

		// Collision detection
		Iterator<Rectangle> iter = rocks.iterator();
		while (iter.hasNext()) {
			Rectangle rock = iter.next();
			if (rock.overlaps(hero)) {
				sound_drop.play();
				iter.remove();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// music_classical.play();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		sr.dispose();
		floorImage1.dispose();
		heroImage.dispose();
		sound_drop.dispose();
		music_classical.dispose();
	}

	public static OrthographicCamera getCamera() {
		return camera;
	}

	// DEBUG
	private void printOnScreenInfo() {
		font.draw(game.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, screenHeight - 20);
		font.draw(game.batch, "hero.touch: " + hero.getTouchPos(), 20, screenHeight - 40);
		font.draw(game.batch, "hero.currentPosition: " + hero.getCurrentPosition(), 20, screenHeight - 60);
		font.draw(game.batch, "hero.direction: " + hero.getDirection(), 20, screenHeight - 80);
		font.draw(game.batch, "hero.movement: " + hero.getMovement(), 20, screenHeight - 100);
		font.draw(game.batch, "hero.velocity: " + hero.getVelocity(), 20, screenHeight - 120);
	}
}
