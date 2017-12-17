package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class WorldGame {
	private Player player;
	private Box [][]box = new Box[13][10];//[y][x]
	public GameFireMan game;
	private float SPEEDSCROLL = 1;
	private int nextIndexChange = 0;
	public WorldGame(GameFireMan game) {
		this.game = game;
		player = new Player(this, (GameFireMan.WIDTH-Player.getWidth())/2, Box.HEIGH*5);
		generateBox();
	}
	public void update() {
		player.update();
		screenScroll();
		clearBoxAndGenerate();
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
	private void screenScroll() {
		for(int i = 0 ;i<13 ;i++) {
			for(int j = 0; j < 10;j++) {
				if(box[i][j] != null) {
					box[i][j].screenScroll(SPEEDSCROLL);
				}
			}
		}
		player.screenScroll(SPEEDSCROLL);
	}
	private void clearBoxAndGenerate() {
		float yOfBox = 0;
		for(int i = 0; i < 10;i++) {
			if(box[nextIndexChange][i] != null) {
				yOfBox = box[nextIndexChange][i].getY();
				break;
			}
		}
		if(yOfBox+Box.WIDTH < 0) {
			if(nextIndexChange == 0) {
				box[nextIndexChange] = generateBoxLine(box[12]);
				nextIndexChange += 1;
			}
			else {
				box[nextIndexChange] = generateBoxLine(box[nextIndexChange-1]);
				if(nextIndexChange == 12) {
					nextIndexChange = 0;
				}
				else {
					nextIndexChange += 1;
				}
			}
		}
	}
	private void generateBox() {
		for(int j = 0; j<10; j++) {
			box[0][j] = new Box(this, Box.WIDTH*j, 0);
		}
		for(int i = 1; i<13; i++) {
			box[i] = generateBoxLine(box[i-1]);
		}
		box[2][4] = new Box(this, Box.WIDTH*4, Box.HEIGH*2*2); //staring place that player stand
		box[2][5] = new Box(this, Box.WIDTH*5, Box.HEIGH*2*2); //staring place that player stand
	}
	public Box[] generateBoxLine(Box[] down) {
		Box[] next = new Box[10];
		boolean haveOne = false;
		for(int i = 0; i<10; i++) {
			if(down[i] != null) {
				float y = down[i].getY();
				int temp = i-2;
				for(int j = 0; j<5; j++) {
					if(temp+j >= 0 && temp+j <= 9 && Math.random() < 0.2) {
						next[temp+j] = new Box(this, Box.WIDTH*(temp+j), y+Box.HEIGH*2);
						haveOne = true;
					}
				}
				while(!haveOne) {
					for(int j = 0; j<5; j++) {
						if(temp+j >= 0 && temp+j <= 9 && Math.random() < 0.2) {
							next[temp+j] = new Box(this, Box.WIDTH*(temp+j), y+Box.HEIGH*2);
							haveOne = true;
						}
					}
				}
			}
		}
		return next;
	}
	public Box[][] getBox() {
		return box;
	}
}