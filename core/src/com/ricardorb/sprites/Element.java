package com.ricardorb.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.ricardorb.catchanimals.CatchAnimals;

/**
 * Main class of every object drawed in the game
 * @author RicardoRB
 *
 */
public class Element extends Sprite {
	
	protected Rectangle rectangle;
	protected final CatchAnimals GAME;
	
	public Element(final Texture texture){
		super(texture);
		GAME = null;
		iniRectangle();
	}

	public Element(final Texture texture, final CatchAnimals game) {
		super(texture);
		this.GAME = game;
		iniRectangle();
	}
	
	public Element(final TextureRegion tRegion,final CatchAnimals game){
		super(tRegion);
		GAME = game;
		iniRectangle();
	}
	
	public void dispose(){
		getTexture().dispose();
	}
	
	public void update(){
		
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	private void iniRectangle() {
		rectangle = new Rectangle();
		rectangle.height = getHeight();
		rectangle.width = getWidth();
		rectangle.x = getX();
		rectangle.y = getY();
	}

}
