package com.manescapeafire.chanchai;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ScoreBoard {
	private Vector2 pos;
	private Texture img;
	private WorldGame world;
	public ScoreBoard(WorldGame world, float x, float y) {
		this.world = world;
		pos = new Vector2(x,y);
		img = new Texture("score.png");
	}
	public void render() {
		world.game.batch.draw(img, pos.x, pos.y);
	}
}