package com.ricardorb.inputs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.ricardorb.catchanimals.CatchAnimals;
import com.ricardorb.catchanimals.Constants;
import com.ricardorb.controllers.ControllerBasket;

public class InputBasket extends InputAdapter {
	
	private ControllerBasket conBucket;
	private final CatchAnimals GAME;
	
	public InputBasket(ControllerBasket conBucket, CatchAnimals game){
		this.conBucket = conBucket;
		this.GAME = game;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		conBucket.setMoveL(false);
		conBucket.setMoveR(false);
		conBucket.setDrag(true);
		
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WINDOWX, Constants.WINDOWY);
		
		Vector3 touchPos = new Vector3();
		touchPos.set(screenX, screenY, 0);
		camera.unproject(touchPos);
		
		conBucket.setDragX(touchPos.x);
		conBucket.setDragY(touchPos.y);
		return true;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		conBucket.setDrag(false);
		switch (keycode) {
		case Input.Keys.RIGHT:
			if(!conBucket.isMoveL()){
				conBucket.setMoveR(true);
			}
			break;
		case Input.Keys.LEFT:
			if(!conBucket.isMoveR()){
				conBucket.setMoveL(true);
			}
			break;
		default:
			return false;
		}
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Input.Keys.RIGHT:
			conBucket.setMoveR(false);
			break;
		case Input.Keys.LEFT:
			conBucket.setMoveL(false);
			break;
		default:
			return false;
		}
		return true;
	}
}
