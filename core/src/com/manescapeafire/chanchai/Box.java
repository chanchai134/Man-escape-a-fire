package com.manescapeafire.chanchai;

import com.badlogic.gdx.graphics.Texture;

public class Box extends Wall{
	public static final int WIDTH = 75;
	public static final int HEIGH = 40;
	private float upper;
	private char statePlayer;//l = lower , h = higher ,o = on the floor//Player is lower than box. 
	public Box(WorldGame world, float x, float y) {
		super(world,x,y);
		this.statePlayer = 'l';
		img = new Texture("ground.png");
		upper = y+HEIGH;
	}
	@Override
	public void screenScroll(float speed) {
		super.screenScroll(speed);
		upper = pos.y+HEIGH;
	}
	public boolean xInRange(float x) {
		if(pos.x <= x && x < (pos.x+WIDTH)) {
			return true;
		}
		return false;
	}
	public float getUpper() {
		return upper;
	}
	public char getStatePlayer() {
		return statePlayer;
	}
	public void setStatePlayer(char statePlayer) {
		this.statePlayer = statePlayer;
	}
}