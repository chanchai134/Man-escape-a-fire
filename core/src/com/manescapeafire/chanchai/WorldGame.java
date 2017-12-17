package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class WorldGame {
	private Player player;
	private Box [][]box = new Box[13][10];//[y][x]
	private GameFireMan game;
	public WorldGame(GameFireMan game) {
		this.game = game;
		player = new Player(game, (GameFireMan.WIDTH-Player.getWidth())/2, Box.HEIGH*5, box);
		/////////////////////////////////////////
		for(int j = 0; j<10; j++) {
			box[0][j] = new Box(game, Box.WIDTH*j, 0);
		}
		for(int i = 1; i<13; i++) {
			box[i] = generateBox(box[i-1]);
		}
		box[2][4] = new Box(game, Box.WIDTH*4, Box.HEIGH*2*2);
		box[2][5] = new Box(game, Box.WIDTH*5, Box.HEIGH*2*2);
		/////////////////////////////////////////////
	}
	public void update() {
		player.update();
	}
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		for(int i = 0 ;i<13 ;i++) {
			for(int j = 0; j < 10;j++) {
				if(box[i][j] != null) {
					box[i][j].render();
				}
			}
		}
		player.render();
	}
	public Box[] generateBox(Box[] down) {
		Box[] next = new Box[10];
		boolean haveOne = false;
		for(int i = 0; i<10; i++) {
			if(down[i] != null) {
				float y = down[i].getY();
				int temp = i-2;
				for(int j = 0; j<5; j++) {
					if(temp+j >= 0 && temp+j <= 9 && Math.random() < 0.2) {
						next[temp+j] = new Box(game, Box.WIDTH*(temp+j), y+Box.HEIGH*2);
						haveOne = true;
					}
				}
				while(!haveOne) {
					for(int j = 0; j<5; j++) {
						if(temp+j >= 0 && temp+j <= 9 && Math.random() < 0.2) {
							next[temp+j] = new Box(game, Box.WIDTH*(temp+j), y+Box.HEIGH*2);
							haveOne = true;
						}
					}
				}
			}
		}
		return next;
	}
}