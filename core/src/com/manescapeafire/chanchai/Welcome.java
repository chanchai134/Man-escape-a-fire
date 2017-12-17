package com.manescapeafire.chanchai;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Welcome {
	private Vector2 pos;
	private Texture img;
	private WorldGame world;
	public Welcome(WorldGame world, float x, float y) {
		this.world = world;
		pos = new Vector2(x,y);
		img = new Texture("welcome.png");
	}
	public void render() {
		world.game.batch.draw(img, pos.x, pos.y);
	}
}