package com.manescapeafire.chanchai;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Coin extends Wall{
	public static final int WIDTH = 35;
	public static final int HEIGH = 35;
	public Rectangle rectangle;
	public Coin(WorldGame world, float x, float y) {
		super(world,x,y);
		rectangle = new Rectangle(pos.x ,pos.y, WIDTH, HEIGH);
		img = new Texture("coin.png");
	}
	public void update() {
		rectangle.setPosition(pos.x, pos.y);
	}
}
