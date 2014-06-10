package com.ricardorb.catchanimals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ricardorb.screens.MainMenuScreen;

public class CatchAnimals extends Game {

	public SpriteBatch batch;
	public final int WINDOWY = 480;
	public final int WINDOWX = 800;

	public void create() {
		Assets.load();
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
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