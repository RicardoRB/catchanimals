package com.ricardorb.sprites;

import com.badlogic.gdx.Gdx;
import com.ricardorb.catchanimals.Assets;
import com.ricardorb.catchanimals.CatchAnimals;
import com.ricardorb.controllers.ControllerBucket;
/**
 * Bucket of the game, the user will move the bucket in the game
 * 
 * @author RicardoRB
 *
 */
public class Bucket extends com.ricardorb.sprites.Element{
	
	private static final int BUCKETVELX = 200;
	private ControllerBucket conBucket;
	
	
	public Bucket(final CatchAnimals game, ControllerBucket conBucket) {
		super(Assets.bucket,game);
		setPosition(game.WINDOWX / 2 - getWidth() / 2, 20);
		setCenter(getWidth() / 2, getHeight() / 2);
		this.conBucket = conBucket;
	}
	
	public void update(){
		
		//Move the bucket
		if(conBucket.isMoveR()){
			setX(getX() + BUCKETVELX * Gdx.graphics.getDeltaTime());
		}else if(conBucket.isMoveL()){
			setX(getX() - BUCKETVELX * Gdx.graphics.getDeltaTime());
		}else if(conBucket.isDrag()){
			setPosition(conBucket.getDragX(), getY());
		}
		
		//Bucket cant get out of the screen
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
