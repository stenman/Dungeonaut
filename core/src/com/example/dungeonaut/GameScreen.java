package com.example.dungeonaut;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	private final Dungeonaut game;

	private BitmapFont font;

	private Music music_classical;
	private Sound sound_drop;

	private OrthographicCamera camera;

	private Texture heroImage;
	private Rectangle hero;
	private Sprite heroSprite;

	private Texture floorTile;
	private Texture rock_1;
	private Texture tree_1;
	private Array<Rectangle> floorTiles;
	private Array<Rectangle> rocks;
	private Array<Rectangle> trees;

	private Vector3 touchPos;

	private static final int screenWidth = 800;
	private static final int screenHeight = 480;

	public GameScreen(final Dungeonaut game) {
		this.game = game;

		font = new BitmapFont();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);

		music_classical = Gdx.audio.newMusic(Gdx.files.internal("Delibes-Notturno.mp3"));
		music_classical.setLooping(true);

		sound_drop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

		floorTile = new Texture(Gdx.files.internal("floor_gravel_1.png"));
		rock_1 = new Texture(Gdx.files.internal("rock_1.png"));
		tree_1 = new Texture(Gdx.files.internal("tree_1.png"));

		hero = new Rectangle();
		heroImage = new Texture(Gdx.files.internal("bluebox.png"));
		heroImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		heroSprite = new Sprite(heroImage);

		hero.width = 64;
		hero.height = 64;
		hero.x = screenWidth / 2 - hero.width / 2;
		hero.y = 20;

		floorTiles = new Array<Rectangle>();
		rocks = new Array<Rectangle>();
		trees = new Array<Rectangle>();

		// TODO: Spawn stuff here?
		// spawnFloorTiles();
		// spawnRocks();
		// spawnTrees();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		// SPRITEBATCH BEGIN------------------------------------------------------------------
		game.batch.begin();
		// SPRITEBATCH BEGIN------------------------------------------------------------------

		game.batch.draw(heroSprite, hero.x, hero.y, hero.width, hero.height);

		printOnScreenInfo();

		// Generate dungeon
		for (Rectangle raindrop : floorTiles) {
			game.batch.draw(floorTile, raindrop.x, raindrop.y);
		}

		// SPRITEBATCH END--------------------------------------------------------------------
		game.batch.end();
		// SPRITEBATCH END--------------------------------------------------------------------

		// Move bucket
		if (Gdx.input.isTouched()) {
			touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			hero.x = touchPos.x - hero.width / 2;
		}

		// Key input control
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			hero.x -= 300 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			hero.x += 300 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			hero.setSize(hero.width + 3, hero.height + 3);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			hero.setSize(hero.width - 3, hero.height - 3);
		}

		// Screen edge checks
		if (hero.x < 0) {
			hero.x = 0;
		}
		if (hero.x > screenWidth - hero.width) {
			hero.x = screenWidth - hero.width;
		}

		// Collision check
		Iterator<Rectangle> iter = floorTiles.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) {
				iter.remove();
			}
			if (raindrop.overlaps(hero)) {
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
		music_classical.play();
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
		floorTile.dispose();
		heroImage.dispose();
		sound_drop.dispose();
		music_classical.dispose();
	}

	// DEBUG
	private void printOnScreenInfo() {
		font.draw(game.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, screenHeight - 20);
		font.draw(game.batch, "bucket.width: " + hero.width, 20, screenHeight - 40);
		font.draw(game.batch, "bucket.height: " + hero.height, 20, screenHeight - 60);
		font.draw(game.batch, "bucketSprite.width: " + heroSprite.getWidth(), 20, screenHeight - 80);
		font.draw(game.batch, "bucketSprite.height: " + heroSprite.getHeight(), 20, screenHeight - 100);
	}
}
