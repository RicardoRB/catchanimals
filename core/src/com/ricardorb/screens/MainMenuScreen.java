package com.ricardorb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ricardorb.catchanimals.Assets;
import com.ricardorb.catchanimals.CatchAnimals;

public class MainMenuScreen implements Screen {

	private final CatchAnimals GAME;
	private OrthographicCamera camera;
	private Stage stage;
	private TextButton btnStartGame;
	private TextButton btnOptions;
	private TextButton btnExit;
	private Table buttonsTable;

	public MainMenuScreen(final CatchAnimals gam) {
		//Initialize objects
		GAME = gam;
		camera = new OrthographicCamera();
		stage = new Stage();
		btnStartGame = new TextButton("Start Game", Assets.skin);
		btnOptions = new TextButton("Options", Assets.skin);
		btnExit = new TextButton("Exit", Assets.skin);
		buttonsTable = new Table(Assets.skin);
		
		//Buttons structure
		buttonsTable.setFillParent(true);
		buttonsTable.center();
		buttonsTable.add(btnStartGame);
		buttonsTable.row();
		buttonsTable.add(btnOptions).space(10f);
		buttonsTable.row();
		buttonsTable.add(btnExit);
		stage.addActor(buttonsTable);
		
		//Buttons events
		btnStartGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GAME.setScreen(new GameScreen(GAME));
			}
		});
		
		btnExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		Gdx.input.setInputProcessor(stage);
		camera.setToOrtho(false, GAME.WINDOWX, GAME.WINDOWY);

	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		GAME.batch.setProjectionMatrix(camera.combined);

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
