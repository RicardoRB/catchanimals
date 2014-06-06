package com.ricardorb.catchanimals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static TextureAtlas atlas;
	public static TextureRegion bucket;
	public static TextureRegion droplet;
	public static Skin skin;

	public static void load() {
		atlas = new TextureAtlas(Gdx.files.internal("imageAtlas.atlas"));
		bucket = atlas.findRegion("bucket");
		droplet = atlas.findRegion("droplet");
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	}

	public static void dispose() {
		atlas.dispose();
		skin.dispose();
	}
}
