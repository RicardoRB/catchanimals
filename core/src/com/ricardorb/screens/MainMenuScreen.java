package com.ricardorb.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ricardorb.catchanimals.Assets;
import com.ricardorb.catchanimals.CatchAnimals;
import com.ricardorb.catchanimals.Constants;
import com.ricardorb.controllers.ControllerOption;

public class MainMenuScreen implements Screen {

	private final CatchAnimals GAME;
	private Stage stage;
	private TextButton btnStartGame;
	private TextButton btnExit;
	private TextButton btnOptions;
	private TextButton btnMusic;
	private TextButton btnEffets;
	private Table buttonsTable;
	private Table selectTable;
	private Table soundsTable;
	private Table menuButton;
	private Image arrowLeft;
	private Image arrowRight;
	private ArrayList<TextureRegion> animalsList;
	private int animalCount;
	private Sound clickSound;

	public MainMenuScreen(final CatchAnimals gam) {
		//Initialize objects
		GAME = gam;
		stage = new Stage();
		btnStartGame = new TextButton(Constants.TXTSTARTGAME, Assets.skin);
		btnExit = new TextButton(Constants.TXTEXIT, Assets.skin);
		btnOptions = new TextButton(Constants.TXTOPTIONS, Assets.skin);
		btnMusic = new TextButton(Constants.TXTMUSIC, Assets.skin);
		btnEffets = new TextButton(Constants.TXTEFFECTS, Assets.skin);
		buttonsTable = new Table(Assets.skin);
		selectTable = new Table(Assets.skin);
		soundsTable = new Table(Assets.skin);
		menuButton = new Table(Assets.skin);
		arrowLeft = new Image(Assets.arrow);
		arrowRight = new Image(Assets.arrow);
		animalsList = Assets.animalsList;
		animalCount = 0;
		
		arrowRight.setOrigin(arrowRight.getWidth() /2 , arrowRight.getHeight() /2 );
		arrowRight.setRotation(180f);
		
		//Buttons structure
		buttonsTable.setFillParent(true);
		
		soundsTable.add(btnMusic);
		soundsTable.add(btnEffets);
		soundsTable.add(btnOptions);
		buttonsTable.add(soundsTable).expandX().expandY().right().top().row();
		
		selectTable.add(arrowLeft);
		Image auxAnimal = new Image(animalsList.get(animalCount));
		selectTable.add(auxAnimal);
		selectTable.add(arrowRight);
		buttonsTable.add(selectTable).expandY().center().row();
		
		menuButton.add(btnStartGame).size(100f, 50f).padRight(60f);
		menuButton.add(btnExit).size(100f, 50f);
		buttonsTable.add(menuButton);
		
		stage.addActor(buttonsTable);
		
		
		clickSound = Gdx.audio.newSound(Gdx.files.internal(Assets.effects + "click.ogg"));

		
		//Buttons events
		btnStartGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GAME.setScreen(new GameScreen(GAME, animalCount));
			}
		});
		
		btnExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		btnOptions.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GAME.setScreen(new OptionScreen(GAME, MainMenuScreen.this));
			}
		});
		
		arrowLeft.addListener(new ClickListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void clicked(InputEvent event, float x, float y) {
				arrowLeft.addAction(Actions.sequence(Actions.moveBy(-10f, 0, 0.1f),
						Actions.moveBy(8f, 0, 0.1f)));
				ControllerOption.playSound(clickSound);
				if(animalCount > 0){
					animalCount--;
				} else {
					animalCount = animalsList.size() - 1;
				}
					Image auxAnimal = new Image(animalsList.get(animalCount));
					selectTable.getCells().get(1).setActor(auxAnimal);
			}
		});
		
		arrowRight.addListener(new ClickListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void clicked(InputEvent event, float x, float y) {
				arrowRight.addAction(Actions.sequence(Actions.moveBy(10f, 0, 0.1f),
						Actions.moveBy(-8f, 0, 0.1f)));
				ControllerOption.playSound(clickSound);
				if(animalCount < animalsList.size() - 1){
					animalCount++;
				} else {
					animalCount = 0;
				}
					Image auxAnimal = new Image(animalsList.get(animalCount));
					selectTable.getCells().get(1).setActor(auxAnimal);
				
			}
		});

		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void render(final float delta) {
		//135R 206G 235B
		//135/255 = 0.529
		//206/255 = 0.807
		//235/255 = 0.921
		Gdx.gl.glClearColor(0.529f, 0.807f, 0.921f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(delta, 1 / 30f));
		stage.draw();
		
	}

	@Override
	public void resize(final int width,final int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
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
		stage.dispose();
		clickSound.dispose();
	}

}
