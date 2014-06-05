package com.ricardorb.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.ricardorb.catchanimals.CatchAnimals;
import com.ricardorb.controllers.ControllerBucket;
import com.ricardorb.inputs.InputBucket;
import com.ricardorb.sprites.Bucket;
import com.ricardorb.sprites.Drop;

public class GameScreen implements Screen {

	private static final long NANOMAXTIMEDROP = 1000000000;
	private Bucket bucket;
	private CatchAnimals GAME;
	private OrthographicCamera camera;
	private InputBucket inpBucket;
	private ControllerBucket conBucket;
	private Music rainMusic;
	private Array<Drop> raindrops;
	private long lastDropTime;
	private int dropsGathered;
	private Sound dropSound;

	public GameScreen(CatchAnimals game) {
		GAME = game;
		conBucket = new ControllerBucket();
		inpBucket = new InputBucket(conBucket, GAME);
		bucket = new Bucket(GAME, conBucket);
		camera = new OrthographicCamera();
		raindrops = new Array<Drop>();

		camera.setToOrtho(false, GAME.WINDOWX, GAME.WINDOWY);
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		rainMusic.setLooping(true);

		spawnRaindrop();
	}

	@Override
	public void render(float delta) {

		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();
		bucket.update();

		GAME.batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		GAME.batch.begin();
		GAME.font.draw(GAME.batch, "Drops Collected: " + dropsGathered, 0,
				GAME.WINDOWY);
		bucket.draw(GAME.batch);
		for (Drop raindrop : raindrops) {
			raindrop.draw(GAME.batch);
		}
		GAME.batch.end();

		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - lastDropTime > NANOMAXTIMEDROP) {
			spawnRaindrop();
		}

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
		Iterator<Drop> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Drop raindrop = iter.next();
			raindrop.update();
			if (raindrop.getY() + raindrop.getHeight() < 0) {
				iter.remove();
			}

			if (raindrop.getRectangle().overlaps(bucket.getRectangle())) {
				dropsGathered++;
				Gdx.input.vibrate(100);
				dropSound.play();
				iter.remove();
			}
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(inpBucket);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		rainMusic.dispose();
		bucket.dispose();

	}

	private void spawnRaindrop() {
		Drop raindrop = new Drop(GAME);
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

}
