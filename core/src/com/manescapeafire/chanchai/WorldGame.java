package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class WorldGame {
	private Player player;
	private Box box;
	public WorldGame(GameFireMan game) {
		player = new Player(game);
		box = new Box(game, 0, 0);
	}
	public void update() {
		player.update();
	}
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		box.render();
		player.render();
	}
	
}