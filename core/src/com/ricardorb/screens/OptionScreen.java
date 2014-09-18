package com.ricardorb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ricardorb.catchanimals.Assets;
import com.ricardorb.catchanimals.CatchAnimals;
import com.ricardorb.controllers.ControllerOption;

public class OptionScreen implements Screen {
	
	private final CatchAnimals GAME;
	private final Screen SCREEN;
	private Stage stage;
	private Table mainTable;
	private Label lblOptions;
	private CheckBox cbBarFinger;
	private CheckBox cbMusicOn;
	private CheckBox cbEffectsOn;
	private TextButton btnBack;
	
	public OptionScreen(final CatchAnimals gam, final Screen screen) {
		GAME = gam;
		SCREEN = screen;
		stage = new Stage();
		mainTable = new Table(Assets.skin);
		lblOptions = new Label("Options", Assets.skin);
		cbBarFinger = new CheckBox("Bottom bar", Assets.skin);
		cbMusicOn = new CheckBox("Turn music On/Off", Assets.skin);
		cbEffectsOn = new CheckBox("Turn effects On/Off", Assets.skin);
		btnBack = new TextButton("Back", Assets.skin);
		
		cbBarFinger.setChecked(ControllerOption.barFinger);
		cbMusicOn.setChecked(ControllerOption.musicOn);
		cbEffectsOn.setChecked(ControllerOption.effectsOn);
		
		mainTable.setFillParent(true);
		mainTable.add(lblOptions).top().row();
		mainTable.add(cbMusicOn).row();
		mainTable.add(cbEffectsOn).row();
		mainTable.add(cbBarFinger).row();
		mainTable.add(btnBack);
		
		stage.addActor(mainTable);
		
		btnBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GAME.setScreen(SCREEN);
			}
		});
		
		cbBarFinger.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ControllerOption.barFinger = cbBarFinger.isChecked();
			}
		});
		
		cbMusicOn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ControllerOption.musicOn = cbMusicOn.isChecked();
			}
		});
		
		cbEffectsOn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ControllerOption.effectsOn = cbEffectsOn.isChecked();
			}
		});
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
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
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
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
	}

}
