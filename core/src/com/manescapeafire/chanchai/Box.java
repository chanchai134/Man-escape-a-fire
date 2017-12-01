package com.manescapeafire.chanchai;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Box {
	private GameFireMan game;
	private Vector2 pos;
	private Texture img;
	public static final int WIDTH = 75;
	public static final int HEIGH = 40;
	private float upper;
	private char statePlayer;//l = lower , h = higher ,o = on the floor//Player is lower than box. 
	public Box(GameFireMan game, float x, float y) {
		this.game = game;
		this.statePlayer = 'l';
		pos = new Vector2(x,y);
		img = new Texture("ground.png");
		upper = y+HEIGH;
	}
	public void render() {
		game.batch.draw(img, pos.x, pos.y);
	}
	public boolean xInRange(Vector2 position) {
		if((pos.x-WIDTH/2) <= position.x && position.x < (pos.x+WIDTH/2)) {
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