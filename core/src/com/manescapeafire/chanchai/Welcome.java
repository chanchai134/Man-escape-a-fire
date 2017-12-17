package com.manescapeafire.chanchai;

import com.badlogic.gdx.graphics.Texture;

public class Welcome extends StaticModel{
	public Welcome(WorldGame world, float x, float y) {
		super(world, x, y);
		img = new Texture("welcome.png");
	}
}