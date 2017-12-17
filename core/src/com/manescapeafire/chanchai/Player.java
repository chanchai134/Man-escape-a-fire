package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player {
	private Vector2 pos;
	private Texture []img = new Texture[10];//0standR 1stanL 234walR 567walkL 8jumpR 9jumpL
	private int status;
	private static final int WIDTH = 72;
	private final int LEFT = -1;
	private final int RIGHT = 1;
	private float SPEED = 5;
	private final float GRAVITY = -1;
	private float JUMPFORCE = 15; //U set on jump
	private float Upresent;
	private float Ubefore;
	private boolean startJump;
	private boolean isOnAir;
	private WorldGame world;
	private boolean isGameEnd = false;
	private char keepKey = '\0';
	private final Animation runningAnimationR;
	private final Animation runningAnimationL;
	private float elapsed_time = 0f;
	private float FRAME_DURATION = 0.15f;
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
		img[2] = new Texture("player_jump_R.png");
		img[3] = new Texture("player_jump_L.png");
		TextureAtlas runningR = new TextureAtlas(Gdx.files.internal("walkR.atlas"));
        Array<TextureAtlas.AtlasRegion> runningFramesR = runningR.findRegions("walkR");
        runningAnimationR = new Animation(FRAME_DURATION, runningFramesR, Animation.PlayMode.LOOP);
        TextureAtlas runningL = new TextureAtlas(Gdx.files.internal("walkL.atlas"));
        Array<TextureAtlas.AtlasRegion> runningFramesL = runningL.findRegions("walkL");
        runningAnimationL = new Animation(FRAME_DURATION, runningFramesL, Animation.PlayMode.LOOP);	
	}
	public void update() {
		updateWithKeyboard();
		autoUpdate();
	}
	public void render() {
		elapsed_time += Gdx.graphics.getDeltaTime();
        TextureRegion runningR = (TextureRegion)runningAnimationR.getKeyFrame(elapsed_time);
        TextureRegion runningL = (TextureRegion)runningAnimationL.getKeyFrame(elapsed_time);
		if(getStatus() == 4) {
			world.game.batch.draw(runningR, pos.x, pos.y);
		}
		else if(getStatus() == 5) {
			world.game.batch.draw(runningL, pos.x, pos.y);
		}
		else {
			world.game.batch.draw(img[getStatus()], pos.x, pos.y);
		}		
	}
	public void screenScroll(float speed) {
		pos.y -= speed;
	}
	private void updateWithKeyboard() {
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if(!isOnAir && keepKey == 'r') {
				setStatus(4);
			}
			else {
				setStatus(2);
			}
			move(RIGHT);
			keepKey = 'r';
		}
		else {
			if(keepKey == 'r') {
				setStatus(0);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			if(!isOnAir) {
				setStatus(5);
			}
			else {
				setStatus(3);
			}
			move(LEFT);
			keepKey = 'l';
		}
		else {
			if(keepKey == 'l') {
				setStatus(1);
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.UP) && !isOnAir && !isGameEnd) {
			//System.out.println("start");
			if(getStatus() == 0 || getStatus() == 4) {
				setStatus(2);
			}
			else if(getStatus() == 1 || getStatus() == 5) {
				setStatus(3);
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
						//System.out.println("end");
					}
					if(pos.y < world.getBox()[i][j].getUpper()) {
						if(world.getBox()[i][j].getUpper() - pos.y <= 0.001 && !startJump && checkXInRange) {
							pos.y = world.getBox()[i][j].getUpper();
							isOnAir = false;
						}
						else {
							world.getBox()[i][j].setStatePlayer('l');
						}
					}
					else if(pos.y == world.getBox()[i][j].getUpper() && !startJump && checkXInRange) {
						world.getBox()[i][j].setStatePlayer('o');
						isOnAir = false;
						if(status == 2) {
							setStatus(0);
						}
						if(status == 3) {
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
	public void move(int direction) {
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
	public boolean isGameOver() {
		if(pos.y < 40) {
			isGameEnd = true;
			return true;
		}
		return false;
	}
	public float getSPEED() {
		return SPEED;
	}
	public void setSPEED(float sPEED) {
		SPEED = sPEED;
	}
	public float getJUMPFORCE() {
		return JUMPFORCE;
	}
	public void setJUMPFORCE(float jUMPFORCE) {
		JUMPFORCE = jUMPFORCE;
	}
}