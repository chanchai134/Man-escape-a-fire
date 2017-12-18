package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Intersector;

public class WorldGame {
	private Player player;
	private Box [][]box = new Box[13][10];//[y][x]
	private Coin [][]coin = new Coin[13][10];
	public GameFireMan game;
	private float SPEEDSCROLL = 1;
	private int nextIndexChangeBox = 0;
	private int nextIndexChangeWall = 0;
	private BitmapFont scoreText = new BitmapFont();
	private Fire fire;
	private float score = 0;
	private int numberOfCoin = 0;
	private Wall [][]wall = new Wall[6][4];
	private float delta;
	private int status = 0; //0start 1game 2score
	private ScoreBoard scoreBoard;
	private BitmapFont scoreBoardText1 = new BitmapFont();
	private BitmapFont scoreBoardText2 = new BitmapFont();
	private BitmapFont scoreBoardText3 = new BitmapFont();
	private Welcome welcome;
	public WorldGame(GameFireMan game) {
		this.game = game;
		player = new Player(this, (GameFireMan.WIDTH-Player.getWidth())/2, Box.HEIGH*9);
		fire = new Fire(this, 0, 0);
		scoreBoard = new ScoreBoard(this,150,184);
		welcome = new Welcome(this,145,88);
		generateBox();
		generateWall();
		generateCoin();
		scoreText.setColor(0, 0, 0, 1);
		scoreText.getData().setScale(1.5f);
		scoreBoardText1.setColor(0, 0, 0, 1);
		scoreBoardText1.getData().setScale(2.5f);
		scoreBoardText2.setColor(0, 0, 0, 1);
		scoreBoardText2.getData().setScale(2.5f);
		scoreBoardText3.setColor(0, 0, 0, 1);
		scoreBoardText3.getData().setScale(2.5f);
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
		autoUpdate();
	}
	private void autoUpdate() {
		clearScreenAndRegenerate();
		for(int i = 0 ;i<13 ;i++) {
			for(int j = 0; j < 10;j++) {
				if(coin[i][j] != null) {
					coin[i][j].update();
				}
			}
		}
		if(player.isGameOver()) {
			status = 2;
		}
		else {
			SPEEDSCROLL += delta/40;
			score+=(SPEEDSCROLL/2);
			player.setSpeed(player.getSpeed()+delta/40);
			player.setJUMPFORCE(player.getJUMPFORCE()+delta/10);
		}
		for(int i = 0 ;i<13 ;i++) {
			for(int j = 0; j < 10;j++) {
				if(coin[i][j] != null) {
					if(Intersector.overlaps(player.rectangle,coin[i][j].rectangle)) {
						coin[i][j] = null;
						numberOfCoin++;
					}
				}
			}
		}
	}
	public void render() {
		wallRender();
		boxRender();
		coinRender();
		player.render();
		fire.render();
		if(status == 0) {
			welcome.render();
		}
		if(status == 1) {
			scoreText.draw(game.batch, "Coin : "+ String.valueOf(numberOfCoin) +"     Score : "+String.format("%.0f", score), 495, 40);
		}
		if(status == 2) {
			scoreBoard.render();
			scoreBoardText1.draw(game.batch,String.format("%.0f", score), 420, 561);
			scoreBoardText2.draw(game.batch,String.valueOf(numberOfCoin)+" X 10", 420, 493);
			scoreBoardText3.draw(game.batch,String.format("%.0f", score+numberOfCoin*10), 420, 416);
		}
	}
	private void screenScroll() {
		scrollAllBox(SPEEDSCROLL);
		scrollAllWall(SPEEDSCROLL);
		scrollAllCoin(SPEEDSCROLL);
		player.screenScroll(SPEEDSCROLL);
	}
	private void scrollAllCoin(float speed) {
		for(int i = 0 ;i<13 ;i++) {
			for(int j = 0; j < 10;j++) {
				if(coin[i][j] != null) {
					coin[i][j].screenScroll(speed);
				}
			}
		}
	}
	private void scrollAllWall(float speed) {
		for(int i = 0; i < 6; i++) {
			for (int j = 0; j < 4 ; j++) {
				wall[i][j].screenScroll(speed);
			}
		}
	}
	private void scrollAllBox(float speed) {
		for(int i = 0 ;i<13 ;i++) {
			for(int j = 0; j < 10;j++) {
				if(box[i][j] != null) {
					box[i][j].screenScroll(speed);
				}
			}
		}
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
				generateCoinLine(12);
				nextIndexChangeBox += 1;
			}
			else {
				box[nextIndexChangeBox] = generateBoxLine(box[nextIndexChangeBox-1]);
				generateCoinLine(nextIndexChangeBox-1);
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
	private void coinRender() {
		for(int i = 0 ;i<13 ;i++) {
			for(int j = 0; j < 10;j++) {
				if(coin[i][j] != null) {
					coin[i][j].render();
				}
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
	private void generateWall() {
		for(int i = 0; i < 6; i++) {
			for (int j = 0; j < 4 ; j++) {
				wall[i][j] = new Wall(this, j*Wall.WIDTH, i*Wall.HEIGH);
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
	private void generateCoin() {
		for(int i = 0; i<13; i++) {
			generateCoinLine(i);
		}
	}
	private void generateCoinLine(int index) {
		for(int j = 0; j<10; j++) {
			if(box[index][j] != null && Math.random() > 0.5) {
					coin[index][j] = new Coin(this, box[index][j].getX()+(Box.WIDTH-Coin.WIDTH)/2, box[index][j].getY()+Box.HEIGH+1);
			}
		}
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