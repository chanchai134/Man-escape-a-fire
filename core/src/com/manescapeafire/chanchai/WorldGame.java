package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class WorldGame {
	private Player player;
	private Box [][]box = new Box[24][10];//[y][x]
	public WorldGame(GameFireMan game) {
		player = new Player(game, 0, Box.HEIGH, box);
		for(int i = 0 ;i<10 ;i++) {
			box[0][i] = new Box(game, Box.WIDTH*i, 0);
		}
	}
	public void update() {
		player.update();
	}
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		for(int i = 0 ;i<10 ;i++) {
			box[0][i].render();
		}
		player.render();
	}	
}