package com.ricardorb.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.tablelayout.Cell;
import com.ricardorb.catchanimals.Assets;
import com.ricardorb.catchanimals.CatchAnimals;

public class MainMenuScreen implements Screen {

	private final CatchAnimals GAME;
	private Stage stage;
	private TextButton btnStartGame;
	private TextButton btnExit;
	private Table buttonsTable;
	private Image arrowLeft;
	private Image arrowRight;
	private ArrayList<TextureRegion> animalsList;
	private int animalCount;

	public MainMenuScreen(final CatchAnimals gam) {
		//Initialize objects
		GAME = gam;
		stage = new Stage();
		btnStartGame = new TextButton("Start Game", Assets.skin);
		btnExit = new TextButton("Exit", Assets.skin);
		buttonsTable = new Table(Assets.skin);
		arrowLeft = new Image(Assets.arrow);
		arrowRight = new Image(Assets.arrow);
		animalsList = Assets.animalsList;
		animalCount = 0;
		
		arrowRight.setOrigin(arrowRight.getWidth() /2 , arrowRight.getHeight() /2 );
		arrowRight.setRotation(180f);
		
		//Buttons structure
		buttonsTable.setFillParent(true);
		buttonsTable.center();
		buttonsTable.add(btnStartGame).size(100f, 50f);
		buttonsTable.row();
		buttonsTable.add(arrowLeft);
		Image auxAnimal = new Image(animalsList.get(animalCount));
		buttonsTable.add(auxAnimal);
		buttonsTable.add(arrowRight);
		buttonsTable.row();
		buttonsTable.add(btnExit).size(100f, 50f);
		
		stage.addActor(buttonsTable);

		
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
		
		arrowLeft.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(animalCount > 0){
					animalCount--;
				} else {
					animalCount = animalsList.size() - 1;
				}
					Image auxAnimal = new Image(animalsList.get(animalCount));
					buttonsTable.getCells().get(2).setWidget(auxAnimal);
			}
		});
		
		arrowRight.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(animalCount < animalsList.size() - 1){
					animalCount++;
				} else {
					animalCount = 0;
				}
					Image auxAnimal = new Image(animalsList.get(animalCount));
					buttonsTable.getCells().get(2).setWidget(auxAnimal);
				
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
		// TODO Auto-generated method stub

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
		stage.dispose();
	}

}
