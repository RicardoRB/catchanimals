package com.ricardorb.catchanimals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static TextureAtlas atlas;
	public static TextureRegion bucket;
	public static TextureRegion droplet;

	public static void load() {
		atlas = new TextureAtlas(Gdx.files.internal("imageAtlas.atlas"));
		bucket = atlas.findRegion("bucket");
		droplet = atlas.findRegion("droplet");
	}

	public static void dispose() {
		atlas.dispose();
	}
}
