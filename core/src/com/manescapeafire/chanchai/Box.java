package com.manescapeafire.chanchai;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Box {
	private Vector2 pos;
	private Texture img;
	public static final int WIDTH = 75;
	public static final int HEIGH = 40;
	private float upper;
	private char statePlayer;//l = lower , h = higher ,o = on the floor//Player is lower than box. 
	private WorldGame world;
	public Box(WorldGame world, float x, float y) {
		this.world = world;
		this.statePlayer = 'l';
		pos = new Vector2(x,y);
		img = new Texture("ground.png");
		upper = y+HEIGH;
	}
	public void render() {
		world.game.batch.draw(img, pos.x, pos.y);
	}
	public void screenScroll(float speed) {
		pos.y -= speed;
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
	public float getY() {
		return pos.y;
	}
}