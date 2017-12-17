package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private Vector2 pos;
	private Texture []img = new Texture[6];//0standR 1stanL 2walR 3walkL 4jumpR 5jumpL
	private int status;
	private static final int WIDTH = 72;
	private final int LEFT = -1;
	private final int RIGHT = 1;
	private final float SPEED = 5f;
	private final float GRAVITY = -1;
	private final float JUMPFORCE = 15.1f; //U set on jump
	private float Upresent;
	private float Ubefore;
	private boolean startJump;
	private boolean isOnAir;
	private WorldGame world;
	public Player(WorldGame world, float x, float y) {
		this.world = world;
		setStatus(0);
		setUpresent(0);
		setUbefore(0);
		isOnAir = false;
		startJump = false;
		pos = new Vector2(x, y);
		img[0] = new Texture("player_stand_R.png");
		img[1] = new Texture("player_stand_L.png");
		img[4] = new Texture("player_jump_R.png");
		img[5] = new Texture("player_jump_L.png");
	}
	public void update() {
		updateWithKeyboard();
		autoUpdate();
	}
	public void render() {
		world.game.batch.draw(img[getStatus()], pos.x, pos.y);
	}
	public void screenScroll(float speed) {
		pos.y -= speed;
	}
	private void updateWithKeyboard() {
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if(!isOnAir) {
				setStatus(0);
			}
			else {
				setStatus(4);
			}
			walk(RIGHT);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			if(!isOnAir) {
				setStatus(1);
			}
			else {
				setStatus(5);
			}
			walk(LEFT);
		}
		if(Gdx.input.isKeyJustPressed(Keys.UP) && !isOnAir) {
			System.out.println("start");
			if(getStatus() == 0) {
				setStatus(4);
			}
			else if(getStatus() == 1) {
				setStatus(5);
			}			
			jump();
			startJump = true;
		}
	}
	private void autoUpdate() {
		if(pos.x > (GameFireMan.WIDTH-Box.WIDTH)) {
			pos.x = (GameFireMan.WIDTH-Box.WIDTH);
		}
		if(pos.x < 0) {
			pos.x = 0;
		}
		isOnAir = true; //wait for checking hits a ground to turn False
		boolean checkXInRange;
		for(int i = 0; i < world.getBox().length; i++) {
			for(int j = 0; j < world.getBox()[i].length; j++) {
				if(world.getBox()[i][j] != null) {
					checkXInRange = world.getBox()[i][j].xInRange(pos.x+(WIDTH/2));
					if(world.getBox()[i][j].getStatePlayer() == 'h' && Ubefore < 0 && pos.y < world.getBox()[i][j].getUpper() && getUpresent() < 0 && checkXInRange) {
						pos.y = world.getBox()[i][j].getUpper();
						System.out.println("end");
					}
					if(pos.y < world.getBox()[i][j].getUpper()) {
						world.getBox()[i][j].setStatePlayer('l');
					}
					else if(pos.y == world.getBox()[i][j].getUpper() && !startJump && checkXInRange) {
						world.getBox()[i][j].setStatePlayer('o');
						isOnAir = false;
						if(status == 4) {
							setStatus(0);
						}
						if(status == 5) {
							setStatus(1);
						}
						setUpresent(0);
						setUbefore(0);
					}
					else {
						world.getBox()[i][j].setStatePlayer('h');
					}
				}
			}
		}
		if(isOnAir) {
			pos.y += getUpresent();
			setUbefore(getUpresent());
			setUpresent(getUpresent()+GRAVITY);
		}
		startJump = false;
		//System.out.println(isOnAir);
	}
	public void walk(int direction) {
		pos.x += SPEED*direction;
	}
	public void jump() {
		setUpresent(JUMPFORCE);
	}
	////////////////////////////////////////////////////
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getUpresent() {
		return Upresent;
	}
	public void setUpresent(float upresent) {
		Upresent = upresent;
	}
	public float getUbefore() {
		return Ubefore;
	}
	public void setUbefore(float ubefore) {
		Ubefore = ubefore;
	}
	public static int getWidth() {
		return WIDTH;
	}
}