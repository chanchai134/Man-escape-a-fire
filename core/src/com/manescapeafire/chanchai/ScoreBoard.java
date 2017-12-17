package com.manescapeafire.chanchai;

import com.badlogic.gdx.graphics.Texture;

public class ScoreBoard extends StaticModel{
	public ScoreBoard(WorldGame world, float x, float y) {
		super(world, x, y);
		img = new Texture("score.png");
	}
}