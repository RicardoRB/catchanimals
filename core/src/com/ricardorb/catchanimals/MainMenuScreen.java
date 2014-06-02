package com.ricardorb.catchanimals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MainMenuScreen implements Screen {

	private final CatchAnimals GAME;
	private OrthographicCamera camera;
	private final String FIRSTSTRING = "Welcome to Drop!!!";
	private final String SECONDSTRING = "Tap anywhere to begin!";
//	Stage stage;

	public MainMenuScreen(final CatchAnimals gam) {
		GAME = gam;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GAME.WINDOWX, GAME.WINDOWY);

	    Table table = new Table();
	    table.setFillParent(true);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        GAME.batch.setProjectionMatrix(camera.combined);

        GAME.batch.begin();
        GAME.font.draw(GAME.batch, FIRSTSTRING, (GAME.WINDOWX/2) - FIRSTSTRING.length(), GAME.WINDOWY/2);
        GAME.font.draw(GAME.batch, SECONDSTRING, 100, 100);
        GAME.batch.end();

        if (Gdx.input.isTouched()) {
            GAME.setScreen(new GameScreen(GAME));
            dispose();
        }
	}

	@Override
	public void resize(int width, int height) {
//		stage.getViewport().update(width, height, true);
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
	}

}
