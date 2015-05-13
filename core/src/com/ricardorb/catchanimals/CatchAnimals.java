package com.ricardorb.catchanimals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ricardorb.controllers.ControllerOption;
import com.ricardorb.screens.MainMenuScreen;

public class CatchAnimals extends Game {

	public SpriteBatch batch;
	

	public void create() {
		Assets.load();
		ControllerOption.load();
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		Assets.dispose();
	}

}