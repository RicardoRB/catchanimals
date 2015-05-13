package com.ricardorb.sprites;

import com.badlogic.gdx.Gdx;
import com.ricardorb.catchanimals.Assets;
import com.ricardorb.catchanimals.CatchAnimals;
import com.ricardorb.catchanimals.Constants;
import com.ricardorb.controllers.ControllerBasket;
/**
 * Bucket of the game, the user will move the bucket in the game
 * 
 * @author RicardoRB
 *
 */
public class Basket extends com.ricardorb.sprites.Element{
	
	private static final int BUCKETVELX = 200;
	private ControllerBasket conBucket;
	
	
	public Basket(final CatchAnimals game, ControllerBasket conBucket) {
		super(Assets.basket,game);
		setPosition(Constants.WINDOWX / 2 - getWidth() / 2, 20);
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
		
		if (getX() > Constants.WINDOWX - getWidth()) {
			setX(Constants.WINDOWX - getWidth());
		}
		rectangle.x = getX();
		rectangle.y = getY();
	}

	public static int getBucketvelx() {
		return BUCKETVELX;
	}
	
}
