package com.ricardorb.controllers;

/**
 * I can abstract the controls with this class
 * 
 * @author RicardoRB
 *
 */
public class ControllerBasket {
	
	private boolean moveL;
	private boolean moveR;
	private boolean drag;
	private float dragX;
	private float dragY;
	
	public boolean isMoveL() {
		return moveL;
	}

	public void setMoveL(boolean moveL) {
		this.moveL = moveL;
	}

	public boolean isMoveR() {
		return moveR;
	}

	public void setMoveR(boolean moveR) {
		this.moveR = moveR;
	}

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	public float getDragX() {
		return dragX;
	}

	public void setDragX(float dragX) {
		this.dragX = dragX;
	}

	public float getDragY() {
		return dragY;
	}

	public void setDragY(float dragY) {
		this.dragY = dragY;
	}
	
}
