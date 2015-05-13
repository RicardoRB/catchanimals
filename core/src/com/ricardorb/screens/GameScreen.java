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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
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
import com.ricardorb.catchanimals.Constants;
import com.ricardorb.controllers.ControllerBasket;
import com.ricardorb.controllers.ControllerOption;
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
		OPTIONS,
		QUIT
	}
	
	private float animalsForSeconds;
	private Basket basket;
	private final CatchAnimals mainGame;
	private InputBasket inpBasket;
	private ControllerBasket conBasket;
	private Music rainMusic;
	private Array<Animal> rainAnimals;
	private float lastAnimalTime;
	private float lastBugTime;
	private int animalsGathered;
	private Sound animalCatchSound;
	private Sound bugCatchSound;
	private Stage stage;
	private TextButton btnX;
	private TextButton btnOptions;
	private Table mainTable;
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
	private int animalNum;
	private float elapseTimeAnimal;
	private ShapeRenderer recFinger;
	

	public GameScreen(CatchAnimals game, int animalCount) {
		mainGame = game;
		animalsForSeconds = 1;
		animalNum = animalCount;
		countAnimalLost = 0;
		lastAnimalTime = 0;
		elapseTimeAnimal = 0.0325f;
		conBasket = new ControllerBasket();
		inpBasket = new InputBasket(conBasket, mainGame);
		basket = new Basket(mainGame, conBasket);
		rainAnimals = new Array<Animal>();
		stage = new Stage();
		btnX = new TextButton(Constants.BTNEXITGAME, Assets.skin);
		btnOptions = new TextButton(Constants.BTNOPTIONS, Assets.skin);
		mainTable = new Table(Assets.skin);
		leftTable = new Table(Assets.skin);
		rightTable = new Table(Assets.skin);
		centerTable = new Table(Assets.skin);
		inputMulti = new InputMultiplexer();
		labDropsColeccted = new Label(Constants.TXTSCORE, Assets.skin);
		labTime = new Label(Constants.TXTTIME, Assets.skin);
		camera = new OrthographicCamera();
		background = new Sprite(Assets.landscape);
		animalCatchSound = Gdx.audio.newSound(Gdx.files.internal(Assets.effects + "cow.ogg"));
		bugCatchSound = Gdx.audio.newSound(Gdx.files.internal(Assets.effects + "bug.ogg"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal(Assets.music + "rain.mp3"));
		recFinger = new ShapeRenderer();
		
		rainMusic.setLooping(true);
		camera.setToOrtho(false, Constants.WINDOWX, Constants.WINDOWY);
		//With this boolean it wont draw more than 1 dialog
		showDialog = false;
		gameState = GameState.RUN;
		
		mainTable.setFillParent(true);
		
		leftTable.add(labDropsColeccted);
		mainTable.add(leftTable).expandY().expandX().top().left();
		//leftTable.top().left();
		
		mainTable.add(centerTable).expandY().top();
		centerTable.add(labTime);
		
		mainTable.add(rightTable).expand().top().right();
		rightTable.add(btnOptions).size(40f, 40f);
		rightTable.add(btnX).size(40f, 40f);
		
		stage.addActor(mainTable);
		
		btnX.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameState = GameState.STOPPED;
			}
		});
		
		btnOptions.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameState = GameState.OPTIONS;
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
		mainGame.batch.setProjectionMatrix(camera.combined);
		// begin a new batch and draw the bucket and
		// all drops
		mainGame.batch.begin();
		background.draw(mainGame.batch);
		
		basket.draw(mainGame.batch);
		for (Animal animaldown : rainAnimals) {
			animaldown.draw(mainGame.batch);
		}
		mainGame.batch.end();
		
		if(ControllerOption.isBarFinger()){
			recFinger.setProjectionMatrix(camera.combined);
			recFinger.begin(ShapeType.Filled);
			recFinger.setColor(0.529f, 0.807f, 0.921f, 1f);
			recFinger.rectLine(new Vector2(0, basket.getHeight()/2), new Vector2(Constants.WINDOWX, basket.getHeight()/2), basket.getHeight());
			recFinger.end();
		}
		
		stage.act(Math.min(delta, 1 / 30f));
		stage.draw();
		
		switch (gameState) {
		case RUN:
			runGame(delta);
			break;

		case PAUSE:
			if(!showDialog){
				pauseGame();
			}
			break;

		case RESUME:
			gameState = GameState.RUN;
			break;

		case STOPPED:
			if(!showDialog){
				showDialog = true;
				new Dialog(Constants.TXTPAUSE, Assets.skin, "dialog") {
					protected void result(Object object) {

						boolean confirmation = (Boolean) object;
						showDialog = false;
						gameState = confirmation ? GameState.QUIT : GameState.RESUME;
					}
				}.text(Constants.TXTEXITASK).button(Constants.TXTYES, true).button(Constants.TXTNO, false).show(stage);
			}
			break;

		case GAMEOVER:
			if(!showDialog){
				showDialog = true;
				new Dialog(Constants.TXTGAMEOVER, Assets.skin, "dialog") {
					protected void result(Object object) {

						boolean confirmation = (Boolean) object;
						showDialog = false;
						if(confirmation){
							resetGame();
						} else {
							mainGame.setScreen(new MainMenuScreen(mainGame));
						}
					}
				}.text(Constants.TXTYOURSCORE + animalsGathered + Constants.TXTRETRY).button(Constants.TXTYES, true).button(Constants.TXTBACKTOMENU, false).show(stage);
			}
			break;
			
		case OPTIONS:
			mainGame.setScreen(new OptionScreen(mainGame, this));
			break;
			
		case QUIT:
			mainGame.setScreen(new MainMenuScreen(mainGame));
			this.dispose();
			break;

		default:
			break;
		}

		
	}

	private void pauseGame() {
		showDialog = true;
		new Dialog(Constants.TXTPAUSE, Assets.skin, "dialog") {
			protected void result(Object object) {
				boolean confirmation = (Boolean) object;
				if (confirmation) {
					gameState = GameState.RESUME;
					showDialog = false;
				}
			}
		}.text(Constants.TXTRESUMEASK).button(Constants.TXTRESUME, true).show(stage);
	}

	private void runGame(float delta) {
		if(countAnimalLost >= MAXANIMALSLOST){
			gameState = GameState.GAMEOVER;
		}
		
		timeCounter += delta;
		
		basket.update();
		labDropsColeccted.setText(Constants.TXTSCORE + animalsGathered);
		labTime.setText(Constants.TXTTIME + (int)timeCounter);
		// check if we need to create a new rainanimals
		if (timeCounter - lastAnimalTime >  animalsForSeconds / (timeCounter * elapseTimeAnimal)) {
			spawnAnimal();
		}
		
		if(timeCounter - lastBugTime > animalsForSeconds / (timeCounter * 0.01625f)){
			spawnBug();
		}

		// move the animal, remove any that are beneath the bottom edge
		// of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
		Iterator<Animal> iter = rainAnimals.iterator();
		while (iter.hasNext()) {
			Animal animal = iter.next();
			animal.update();
			
			if (animal.getY() + animal.getHeight() < 0) {
				iter.remove();
				if(!animal.isBug()){
					countAnimalLost++;
				}
			}

			if (animal.getRectangle().overlaps(basket.getRectangle())) {
				if (animal.isBug()) {
					gameState = GameState.GAMEOVER;
					ControllerOption.playSound(bugCatchSound);
				} else {
					animalsGathered++;
					Gdx.input.vibrate(100);
					ControllerOption.playSound(animalCatchSound);
					iter.remove();
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		if(ControllerOption.isMusicOn()){
			rainMusic.play();
		}
		barFingerChange();
		Gdx.input.setInputProcessor(inputMulti);
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
		if(ControllerOption.isMusicOn()){
			rainMusic.play();
		}
	}

	@Override
	public void dispose() {
		rainMusic.dispose();
		bugCatchSound.dispose();
		animalCatchSound.dispose();
	}

	private void spawnAnimal() {
		Animal rainAnimal = new Animal(mainGame, Assets.animalsList.get(animalNum), false);
		rainAnimals.add(rainAnimal);
		lastAnimalTime = timeCounter;
	}
	
	private void spawnBug(){
		int bugNum = (int) (Math.random()*Assets.bugList.size());
		Animal rainBug = new Animal(mainGame, Assets.bugList.get(bugNum), true);
		rainAnimals.add(rainBug);
		lastBugTime = timeCounter;
	}
	
	private void resetGame() {
		countAnimalLost = 0;
		timeCounter = 0;
		animalsGathered = 0;
		lastAnimalTime = 0;
		lastBugTime = 0;
		Iterator<Animal> iter = rainAnimals.iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		spawnAnimal();
		gameState = GameState.RUN;
	}
	
	private void barFingerChange(){
		if(ControllerOption.isBarFinger()){
			basket.setPosition(basket.getX(), basket.getY() + basket.getHeight());
			background.setSize(background.getWidth(), background.getHeight() - basket.getHeight());
			background.setPosition(0, basket.getHeight());
		} else {
			basket.setPosition(basket.getX(), 0);
			background.setSize(background.getWidth(), background.getHeight() + basket.getHeight());
			background.setPosition(0, 0);
			
		}
	}

}
