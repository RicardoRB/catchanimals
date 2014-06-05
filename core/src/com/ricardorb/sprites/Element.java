package com.ricardorb.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.ricardorb.catchanimals.CatchAnimals;

public class Element extends Sprite {
	
	protected Rectangle rectangle;
	protected final CatchAnimals GAME;
	
	public Element(Texture texture){
		super(texture);
		GAME = null;
		rectangle = new Rectangle();
		rectangle.height = getHeight();
		rectangle.width = getWidth();
		rectangle.x = getX();
		rectangle.y = getY();
	}

	public Element(Texture texture, CatchAnimals game) {
		super(texture);
		this.GAME = game;
		rectangle = new Rectangle();
		rectangle.height = getHeight();
		rectangle.width = getWidth();
		rectangle.x = getX();
		rectangle.y = getY();
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

}
