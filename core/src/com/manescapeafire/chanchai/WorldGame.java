package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class WorldGame {
	private Player player;
	public WorldGame(GameFireMan game) {
		this.player = new Player(game);
	}
	public void update() {
	}
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.render();
	}
	
}