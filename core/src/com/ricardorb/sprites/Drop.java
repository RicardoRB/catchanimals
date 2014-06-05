package com.ricardorb.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.ricardorb.catchanimals.Assets;
import com.ricardorb.catchanimals.CatchAnimals;

/**
 * Every drop in the game that the user should catch
 * 
 * @author RicardoRB
 *
 */
public class Drop extends Element {
	
	private static final int DROPVELX = 300;
	
	public Drop(CatchAnimals game){
		super(Assets.droplet, game);
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
