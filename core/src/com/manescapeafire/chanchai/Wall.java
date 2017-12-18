package com.manescapeafire.chanchai;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Wall {
	protected Vector2 pos;
	protected Texture img;
	public static final int WIDTH = 225;
	public static final int HEIGH = 225;
	private WorldGame world;
	public Wall(WorldGame world, float x, float y) {
		this.world = world;
		pos = new Vector2(x,y);
		img = new Texture("wall.png");
	}
	public void render() {
		world.game.batch.draw(img, pos.x, pos.y);
	}
	public void screenScroll(float speed) {
		pos.y -= speed;
	}
	public float getY() {
		return pos.y;
	}
	public float getX() {
		return pos.x;
	}
	public void setY(float y) {
		pos.y = y;
	}
}