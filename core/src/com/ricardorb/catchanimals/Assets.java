package com.ricardorb.catchanimals;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static TextureAtlas atlas;
	public static TextureRegion basket;
	public static TextureRegion cow, bear, cat, elephant, elk, frog;
	public static TextureRegion gnu, hand, kangaroo, landscape, leopard, monkey;
	public static TextureRegion owl, pig, sheep, squirrel, arrow;
	public static TextureRegion housefly, caterpillar;
	public static ArrayList<TextureRegion> animalsList;
	public static ArrayList<TextureRegion> bugList;
	public static Skin skin;
	public static String effects;
	public static String music;
	public static String data;
	public static String images;

	public static void load() {
		effects = "sounds/effects/";
		music = "sounds/music/";
		data = "data/";
		images = "images/";
		atlas = new TextureAtlas(Gdx.files.internal(images + "imageAtlas.atlas"));
		basket = atlas.findRegion("basket");
		cow = atlas.findRegion("cow");
		bear = atlas.findRegion("bear");
		cat = atlas.findRegion("cat");
		elephant = atlas.findRegion("elephant");
		elk = atlas.findRegion("elk");
		frog = atlas.findRegion("frog");
		gnu = atlas.findRegion("gnu");
		hand = atlas.findRegion("hand");
		kangaroo = atlas.findRegion("kangaroo");
		landscape = atlas.findRegion("landscape");
		leopard = atlas.findRegion("leopard");
		monkey = atlas.findRegion("monkey");
		owl = atlas.findRegion("owl");
		pig = atlas.findRegion("pig");
		sheep = atlas.findRegion("sheep");
		squirrel = atlas.findRegion("squirrel");
		housefly = atlas.findRegion("housefly");
		caterpillar = atlas.findRegion("caterpillar");
		arrow = atlas.findRegion("arrow");
		skin = new Skin(Gdx.files.internal(data + "uiskin.json"));
		
		animalsList = new ArrayList<TextureRegion>();
		animalsList.add(cow);
		animalsList.add(bear);
		animalsList.add(cat);
		animalsList.add(elephant);
		animalsList.add(elk);
		animalsList.add(frog);
		animalsList.add(gnu);
		animalsList.add(kangaroo);
		animalsList.add(leopard);
		animalsList.add(monkey);
		animalsList.add(owl);
		animalsList.add(pig);
		animalsList.add(sheep);
		animalsList.add(squirrel);
		
		bugList = new ArrayList<TextureRegion>();
		bugList.add(housefly);
		bugList.add(caterpillar);
		
	}

	public static void dispose() {
		atlas.dispose();
		skin.dispose();
	}
}
