package com.ricardorb.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.ricardorb.catchanimals.CatchAnimals;
import com.ricardorb.catchanimals.Constants;

/**
 * Every drop in the game that the user should catch
 * 
 * @author RicardoRB
 *
 */
public class Animal extends Element {
	
	private static final int DROPVELX = 300;
	private boolean bug;
	
	public Animal(CatchAnimals game, TextureRegion textureRegion, boolean bug){
		super(textureRegion, game);
		setX(MathUtils.random(0, Constants.WINDOWX - getWidth()));
		setY(Constants.WINDOWY);
		this.bug = bug;
	}

	public void update(){
		setY(getY() - DROPVELX * Gdx.graphics.getDeltaTime());
		rectangle.x = getX();
		rectangle.y = getY();
	}

	public static int getDropvelx() {
		return DROPVELX;
	}

	public boolean isBug() {
		return bug;
	}

}
