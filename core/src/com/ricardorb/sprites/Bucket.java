package com.ricardorb.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.ricardorb.catchanimals.CatchAnimals;
import com.ricardorb.controllers.ControllerBucket;

public class Bucket extends com.ricardorb.sprites.Element{
	
	private static final int BUCKETVELX = 200;
	private ControllerBucket conBucket;
	
	
	public Bucket(final CatchAnimals game, ControllerBucket conBucket) {
		super(new Texture(Gdx.files.internal("bucket.png")),game);
		setPosition(game.WINDOWX / 2 - getWidth() / 2, 20);
		setCenter(getWidth() / 2, getHeight() / 2);
		this.conBucket = conBucket;
	}
	
	public void update(){
		
		if(conBucket.isMoveR()){
			setX(getX() + BUCKETVELX * Gdx.graphics.getDeltaTime());
		}else if(conBucket.isMoveL()){
			setX(getX() - BUCKETVELX * Gdx.graphics.getDeltaTime());
		}else if(conBucket.isDrag()){
			setPosition(conBucket.getDragX(), getY());
		}
		
		if (getX() < 0) {
			setX(0);
		}
		
		if (getX() > GAME.WINDOWX - getWidth()) {
			setX(GAME.WINDOWX - getWidth());
		}
		rectangle.x = getX();
		rectangle.y = getY();
	}

	public static int getBucketvelx() {
		return BUCKETVELX;
	}
	
}
