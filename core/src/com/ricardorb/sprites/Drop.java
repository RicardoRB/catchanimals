package com.ricardorb.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.ricardorb.catchanimals.CatchAnimals;

public class Drop extends Element {
	
	private static final int DROPVELX = 300;
	
	
	public Drop() {
		super(new Texture(Gdx.files.internal("droplet.png")));
	}
	
	public Drop(CatchAnimals game){
		super(new Texture(Gdx.files.internal("droplet.png")), game);
		setX(MathUtils.random(0, game.WINDOWX - getWidth()));
		setY(game.WINDOWY);
	}

	public void update(){
		setY(getY() - DROPVELX * Gdx.graphics.getDeltaTime());
		rectangle.x = getX();
		rectangle.y = getY();
	}

	public static int getDropvelx() {
		return DROPVELX;
	}

}
