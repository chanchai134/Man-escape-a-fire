package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class WorldGame {
	private Player player;
	private Box [][]box = new Box[13][10];//[y][x]
	public GameFireMan game;
	private float SPEEDSCROLL = 1;
	private int nextIndexChangeBox = 0;
	private int nextIndexChangeWall = 0;
	private BitmapFont scoreText = new BitmapFont();
	private Fire fire;
	private float score = 0;
	private Wall [][]wall = new Wall[6][4];
	private float delta;
	private int status = 0; //0start 1game 2score
	private ScoreBoard scoreBoard;
	private BitmapFont scoreBoardText = new BitmapFont();
	private Welcome welcome;
	public WorldGame(GameFireMan game) {
		this.game = game;
		player = new Player(this, (GameFireMan.WIDTH-Player.getWidth())/2, Box.HEIGH*9);
		fire = new Fire(this, 0, 0);
		scoreBoard = new ScoreBoard(this,150,184);
		welcome = new Welcome(this,145,88);
		generateBox();
		generateWall();
		scoreText.setColor(0, 0, 0, 1);
		scoreText.getData().setScale(1.5f);
		scoreBoardText.setColor(0, 0, 0, 1);
		scoreBoardText.getData().setScale(5f);
	}
	public void update() {
		if(status == 1) {
			player.update();
			screenScroll();
		}
		if(status == 2) {
			if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				status = 0;
				player = new Player(this, (GameFireMan.WIDTH-Player.getWidth())/2, Box.HEIGH*9);
				scoreBoard = new ScoreBoard(this,150,184);
				generateBox();
				generateWall();
				SPEEDSCROLL = 1;
				nextIndexChangeBox = 0;
				nextIndexChangeWall = 0;
				score = 0;
			}
		}
		else if(status == 0) {
			if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				status = 1;
			}
		}
		clearScreenAndRegenerate();
		if(player.isGameOver()) {
			status = 2;
		}
		else {
			SPEEDSCROLL += delta/40;
			score+=(SPEEDSCROLL/2);
			player.setSPEED(player.getSPEED()+delta/40);
			player.setJUMPFORCE(player.getJUMPFORCE()+delta/10);
		}
	}
	public void render() {
		wallRender();
		boxRender();
		player.render();
		fire.render();
		if(status == 0) {
			welcome.render();
		}
		if(status == 1) {
			scoreText.draw(game.batch, "Score : "+String.format("%.0f", score), 595, 40);
		}
		if(status == 2) {
			scoreBoard.render();
			scoreBoardText.draw(game.batch,String.format("%.0f", score), 290, 500);
		}
	}
	private void screenScroll() {
		for(int i = 0 ;i<13 ;i++) {
			for(int j = 0; j < 10;j++) {
				if(box[i][j] != null) {
					box[i][j].screenScroll(SPEEDSCROLL);
				}
			}
		}
		for(int i = 0; i < 6; i++) {
			for (int j = 0; j < 4 ; j++) {
				wall[i][j].screenScroll(SPEEDSCROLL);
			}
		}
		player.screenScroll(SPEEDSCROLL);
	}
	private void clearScreenAndRegenerate() {
		float yOfBox = 0;
		for(int i = 0; i < 10;i++) {
			if(box[nextIndexChangeBox][i] != null) {
				yOfBox = box[nextIndexChangeBox][i].getY();
				break;
			}
		}
		if(yOfBox+Box.WIDTH < 0) {
			if(nextIndexChangeBox == 0) {
				box[nextIndexChangeBox] = generateBoxLine(box[12]);
				nextIndexChangeBox += 1;
			}
			else {
				box[nextIndexChangeBox] = generateBoxLine(box[nextIndexChangeBox-1]);
				if(nextIndexChangeBox == 12) {
					nextIndexChangeBox = 0;
				}
				else {
					nextIndexChangeBox += 1;
				}
			}
		}
		//////////////////////////////////////////////////
		float yOfWall = wall[nextIndexChangeWall][0].getY();
		if(yOfWall+Wall.WIDTH < 0) {
			if(nextIndexChangeWall == 0) {
				for(int i = 0; i < 4; i++) {
					wall[nextIndexChangeWall][i].setY(wall[5][i].getY()+Wall.HEIGH);
				}
				nextIndexChangeWall += 1;
			}
			else {
				for(int i = 0; i < 4; i++) {
					wall[nextIndexChangeWall][i].setY(wall[nextIndexChangeWall-1][i].getY()+Wall.HEIGH);
				}
				if(nextIndexChangeWall == 5) {
					nextIndexChangeWall = 0;
				}
				else {
					nextIndexChangeWall += 1;
				}
			}
		}
	}
	private void generateWall() {
		for(int i = 0; i < 6; i++) {
			for (int j = 0; j < 4 ; j++) {
				wall[i][j] = new Wall(this, j*Wall.WIDTH, i*Wall.HEIGH);
			}
		}
	}
	private void boxRender() {
		for(int i = 0 ;i<13 ;i++) {
			for(int j = 0; j < 10;j++) {
				if(box[i][j] != null) {
					box[i][j].render();
				}
			}
		}
	}
	private void wallRender() {
		for(int i = 0; i < 6; i++) {
			for (int j = 0; j < 4 ; j++) {
				wall[i][j].render();
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
		box[4][4] = new Box(this, Box.WIDTH*4, Box.HEIGH*2*4); //staring place that player stand
		box[4][5] = new Box(this, Box.WIDTH*5, Box.HEIGH*2*4); //staring place that player stand
	}
	public Box[] generateBoxLine(Box[] down) {
		Box[] next = new Box[10];
		boolean haveOne = false;
		if(Math.random() > 0.5) {
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
		}
		else {
			for(int i = 9; i>=0; i--) {
				if(down[i] != null) {
					float y = down[i].getY();
					int temp = i+2;
					for(int j = 0; j<5; j++) {
						if(temp-j >= 0 && temp-j <= 9 && Math.random() < 0.2) {
							next[temp-j] = new Box(this, Box.WIDTH*(temp-j), y+Box.HEIGH*2);
							haveOne = true;
						}
					}
					while(!haveOne) {
						for(int j = 0; j<5; j++) {
							if(temp-j >= 0 && temp-j <= 9 && Math.random() < 0.2) {
								next[temp-j] = new Box(this, Box.WIDTH*(temp-j), y+Box.HEIGH*2);
								haveOne = true;
							}
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
	public void setDelta(float delta) {
		this.delta = delta;
	}
}