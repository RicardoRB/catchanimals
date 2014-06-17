package com.ricardorb.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.ricardorb.catchanimals.Assets;
import com.ricardorb.catchanimals.CatchAnimals;
import com.ricardorb.controllers.ControllerBucket;
import com.ricardorb.inputs.InputBucket;
import com.ricardorb.sprites.Bucket;
import com.ricardorb.sprites.Drop;

public class GameScreen implements Screen {
	
	//Game states used in render
	public enum State{
		RUN,
		PAUSE,
		RESUME,
		STOPPED,
		GAMEOVER,
		QUIT
	}
	
	private static final long NANOMAXTIMEDROP = 1000000000;
	private Bucket bucket;
	private CatchAnimals GAME;
	private InputBucket inpBucket;
	private ControllerBucket conBucket;
	private Music rainMusic;
	private Array<Drop> raindrops;
	private long lastDropTime;
	private int dropsGathered;
	private Sound dropSound;
	private Stage stage;
	private TextButton btnX;
	private Table leftTable;
	private Table rightTable;
	private InputMultiplexer inputMulti;
	private Label labDropsColeccted;
	private State gameState;
	private boolean showDialog;
	OrthographicCamera camera;
	

	public GameScreen(CatchAnimals game) {
		GAME = game;
		conBucket = new ControllerBucket();
		inpBucket = new InputBucket(conBucket, GAME);
		bucket = new Bucket(GAME, conBucket);
		raindrops = new Array<Drop>();
		stage = new Stage();
		btnX = new TextButton("X", Assets.skin);
		leftTable = new Table(Assets.skin);
		rightTable = new Table(Assets.skin);
		inputMulti = new InputMultiplexer();
		labDropsColeccted = new Label("Drops Collected: ", Assets.skin);
		camera = new OrthographicCamera();
		camera.setToOrtho(false,GAME.WINDOWX, GAME.WINDOWY);
		//With this boolean it wont draw more than 1 dialog
		showDialog = false;
		gameState = State.RUN;
		
		leftTable.setFillParent(true);
		rightTable.setFillParent(true);
		leftTable.top().left();
		leftTable.add(labDropsColeccted);
		rightTable.add(btnX).size(40f, 40f);
		rightTable.top().right();
		
		stage.addActor(leftTable);
		stage.addActor(rightTable);
		
		btnX.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameState = State.STOPPED;
			}
		});
		
		//Adding every input in multiplexer
		inputMulti.addProcessor(stage);
		inputMulti.addProcessor(inpBucket);
		
		Gdx.input.setInputProcessor(inputMulti);
		
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
		GAME.batch.setProjectionMatrix(camera.combined);
		// begin a new batch and draw the bucket and
		// all drops
		GAME.batch.begin();
		bucket.draw(GAME.batch);
		for (Drop raindrop : raindrops) {
			raindrop.draw(GAME.batch);
		}
		GAME.batch.end();

		stage.act(Math.min(delta, 1 / 30f));
		stage.draw();

		switch (gameState) {
		case RUN:
			Table.drawDebug(stage);

			bucket.update();

			labDropsColeccted.setText("Drops Collected: " + dropsGathered);

			// check if we need to create a new raindrop
			if (TimeUtils.nanoTime() - lastDropTime > NANOMAXTIMEDROP) {
				spawnRaindrop();
			}

			// move the raindrops, remove any that are beneath the bottom edge
			// of
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
			break;

		case PAUSE:
			if(!showDialog){
				showDialog = true;
				new Dialog("Pause", Assets.skin, "dialog") {
					protected void result(Object object) {
						boolean confirmation = (Boolean) object;
						if (confirmation) {
							gameState = State.RESUME;
							showDialog = false;
						}
					}
				}.text("Resume game?").button("Resume", true).show(stage);
			}
			break;

		case RESUME:
			gameState = State.RUN;
			break;

		case STOPPED:
			if(!showDialog){
				showDialog = true;
				new Dialog("Pause", Assets.skin, "dialog") {
					protected void result(Object object) {

						boolean confirmation = (Boolean) object;
						showDialog = false;
						gameState = confirmation ? State.QUIT : State.RESUME;
					}
				}.text("Are you sure you want to quit?").button("Yes", true).button("No", false).show(stage);
			}
			break;

		case GAMEOVER:
			
			break;
		case QUIT:
			GAME.setScreen(new MainMenuScreen(GAME));
			break;

		default:
			break;
		}

		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		rainMusic.play();
	}

	@Override
	public void hide() {
		rainMusic.pause();
		gameState = State.PAUSE;
	}

	@Override
	public void pause() {
		rainMusic.stop();
		gameState = State.PAUSE;
	}

	@Override
	public void resume() {
		rainMusic.play();
	}

	@Override
	public void dispose() {
		rainMusic.dispose();
	}

	private void spawnRaindrop() {
		Drop raindrop = new Drop(GAME);
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

}
