package com.ricardorb.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.ricardorb.catchanimals.Assets;
import com.ricardorb.catchanimals.CatchAnimals;
import com.ricardorb.controllers.ControllerBasket;
import com.ricardorb.inputs.InputBasket;
import com.ricardorb.sprites.Animal;
import com.ricardorb.sprites.Basket;

public class GameScreen implements Screen {
	
	//Game states used in render
	public enum GameState{
		RUN,
		PAUSE,
		RESUME,
		STOPPED,
		GAMEOVER,
		QUIT
	}
	
	private float animalsForSeconds;
	private Basket basket;
	private CatchAnimals GAME;
	private InputBasket inpBasket;
	private ControllerBasket conBasket;
	private Music rainMusic;
	private Array<Animal> rainAnimals;
	private float lastAnimalTime;
	private int animalsGathered;
	private Sound animalCatch;
	private Stage stage;
	private TextButton btnX;
	private Table leftTable;
	private Table rightTable;
	private Table centerTable;
	private InputMultiplexer inputMulti;
	private Label labDropsColeccted;
	private Label labTime;
	private GameState gameState;
	private boolean showDialog;
	private OrthographicCamera camera;
	private Sprite background;
	private float timeCounter;
	private static final int MAXANIMALSLOST = 5;
	private int countAnimalLost;
	

	public GameScreen(CatchAnimals game) {
		GAME = game;
		animalsForSeconds = 1;
		countAnimalLost = 0;
		lastAnimalTime = 0;
		conBasket = new ControllerBasket();
		inpBasket = new InputBasket(conBasket, GAME);
		basket = new Basket(GAME, conBasket);
		rainAnimals = new Array<Animal>();
		stage = new Stage();
		btnX = new TextButton("X", Assets.skin);
		leftTable = new Table(Assets.skin);
		rightTable = new Table(Assets.skin);
		centerTable = new Table(Assets.skin);
		inputMulti = new InputMultiplexer();
		labDropsColeccted = new Label("Score: ", Assets.skin);
		labTime = new Label("Time: ", Assets.skin);
		camera = new OrthographicCamera();
		background = new Sprite(Assets.landscape);
		animalCatch = Gdx.audio.newSound(Gdx.files.internal(Assets.effects + "cow.ogg"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal(Assets.music + "rain.mp3"));
		
		rainMusic.setLooping(true);
		camera.setToOrtho(false,GAME.WINDOWX, GAME.WINDOWY);
		//With this boolean it wont draw more than 1 dialog
		showDialog = false;
		gameState = GameState.RUN;
		leftTable.setFillParent(true);
		rightTable.setFillParent(true);
		centerTable.setFillParent(true);
		leftTable.top().left();
		leftTable.add(labDropsColeccted);
		rightTable.add(btnX).size(40f, 40f);
		rightTable.top().right();
		centerTable.add(labTime);
		centerTable.top();
		
		stage.addActor(leftTable);
		stage.addActor(rightTable);
		stage.addActor(centerTable);
		
		btnX.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameState = GameState.STOPPED;
			}
		});
		
		//Adding every input in multiplexer
		inputMulti.addProcessor(stage);
		inputMulti.addProcessor(inpBasket);
		
		Gdx.input.setInputProcessor(inputMulti);
		
		spawnAnimal();
	}

	@Override
	public void render(float delta) {
		
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0.529f, 0.807f, 0.921f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// tell the camera to update its matrices.
		camera.update();
		GAME.batch.setProjectionMatrix(camera.combined);
		// begin a new batch and draw the bucket and
		// all drops
		GAME.batch.begin();
		background.draw(GAME.batch);
		basket.draw(GAME.batch);
		for (Animal raindrop : rainAnimals) {
			raindrop.draw(GAME.batch);
		}
		GAME.batch.end();

		stage.act(Math.min(delta, 1 / 30f));
		stage.draw();
		
		switch (gameState) {
		case RUN:
			
			if(countAnimalLost >= MAXANIMALSLOST){
				gameState = GameState.GAMEOVER;
			}
			
			timeCounter += delta;
			
			basket.update();
			labDropsColeccted.setText("Score: " + animalsGathered);
			labTime.setText("Time: " + (int)timeCounter);
			// check if we need to create a new rainanimal
			if (timeCounter - lastAnimalTime >  animalsForSeconds / (timeCounter * 0.0325f)) {
				spawnAnimal();
			}

			// move the raindrops, remove any that are beneath the bottom edge
			// of
			// the screen or that hit the bucket. In the later case we play back
			// a sound effect as well.
			Iterator<Animal> iter = rainAnimals.iterator();
			while (iter.hasNext()) {
				Animal animal = iter.next();
				animal.update();
				if (animal.getY() + animal.getHeight() < 0) {
					iter.remove();
					countAnimalLost++;
				}

				if (animal.getRectangle().overlaps(basket.getRectangle())) {
					animalsGathered++;
					Gdx.input.vibrate(100);
					animalCatch.play();
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
							gameState = GameState.RESUME;
							showDialog = false;
						}
					}
				}.text("Resume game?").button("Resume", true).show(stage);
			}
			break;

		case RESUME:
			gameState = GameState.RUN;
			break;

		case STOPPED:
			if(!showDialog){
				showDialog = true;
				new Dialog("Pause", Assets.skin, "dialog") {
					protected void result(Object object) {

						boolean confirmation = (Boolean) object;
						showDialog = false;
						gameState = confirmation ? GameState.QUIT : GameState.RESUME;
					}
				}.text("Are you sure you want to quit?").button("Yes", true).button("No", false).show(stage);
			}
			break;

		case GAMEOVER:
			if(!showDialog){
				showDialog = true;
				new Dialog("Game Over", Assets.skin, "dialog") {
					protected void result(Object object) {

						boolean confirmation = (Boolean) object;
						showDialog = false;
						if(confirmation){
							resetGame();
						} else {
							GAME.setScreen(new MainMenuScreen(GAME));
						}
					}
				}.text("Your score " + animalsGathered + ". Retry?").button("Yes", true).button("Go to menu", false).show(stage);
			}
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
		gameState = GameState.PAUSE;
	}

	@Override
	public void pause() {
		rainMusic.stop();
		gameState = GameState.PAUSE;
	}

	@Override
	public void resume() {
		rainMusic.play();
	}

	@Override
	public void dispose() {
		rainMusic.dispose();
	}

	private void spawnAnimal() {
		Animal raindrop = new Animal(GAME);
		rainAnimals.add(raindrop);
		lastAnimalTime = timeCounter;
	}
	
	private void resetGame() {
		countAnimalLost = 0;
		timeCounter = 0;
		animalsGathered = 0;
		lastAnimalTime = 0;
		Iterator<Animal> iter = rainAnimals.iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		spawnAnimal();
		gameState = GameState.RUN;
	}

}
