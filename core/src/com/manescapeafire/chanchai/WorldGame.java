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
		box[2][5] = new Box(game, Box.WIDTH*5, Box.HEIGH*2);
		box[3][3] = new Box(game, Box.WIDTH*3, Box.HEIGH*3);
		box[4][4] = new Box(game, Box.WIDTH*4, Box.HEIGH*4);
		box[5][6] = new Box(game, Box.WIDTH*6, Box.HEIGH*5);
		box[6][7] = new Box(game, Box.WIDTH*7, Box.HEIGH*6);
		box[7][4] = new Box(game, Box.WIDTH*4, Box.HEIGH*7);
		box[8][6] = new Box(game, Box.WIDTH*6, Box.HEIGH*8);
		box[9][9] = new Box(game, Box.WIDTH*9, Box.HEIGH*9);
		box[10][3] = new Box(game, Box.WIDTH*3, Box.HEIGH*10);
		box[11][1] = new Box(game, Box.WIDTH*1, Box.HEIGH*11);
	}
	public void update() {
		player.update();
	}
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		for(int i = 0 ;i<24 ;i++) {
			for(int j = 0; j < 10;j++) {
				if(box[i][j] != null) {
					box[i][j].render();
				}
			}
		}
		player.render();
	}	
}