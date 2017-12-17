package com.manescapeafire.chanchai;

import com.badlogic.gdx.graphics.Texture;

public class Fire extends StaticModel{
	public Fire(WorldGame world, float x, float y) {
		super(world, x, y);
		img = new Texture("fire.png");
	}
}