package com.manescapeafire.chanchai;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class StaticModel {
	private Vector2 pos;
	protected Texture img;
	private WorldGame world;
	public StaticModel(WorldGame world, float x, float y) {
		this.world = world;
		pos = new Vector2(x,y);
	}
	public void render() {
		world.game.batch.draw(img, pos.x, pos.y);
	}
}