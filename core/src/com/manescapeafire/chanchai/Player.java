package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private GameFireMan game;
	private Vector2 pos;
	private Texture []img = new Texture[6];//0standR 1stanL 2walR 3walkL 4jumpR 5jumpL
	private int status;
	private final int LEFT = -1;
	private final int RIGHT = 1;
	private final float SPEED = 5f;
	private final float GRAVITY = -1;
	private final float JUMPFORCE = 5; //U set on jump
	private float Upresent;
	private float lower;
	private float uppder;
	public Player(GameFireMan game, float x, float y) {
		this.game = game;
		setStatus(0);
		setUpresent(0);
		pos = new Vector2(x, y);
		img[0] = new Texture("player_stand_R.png");
		img[1] = new Texture("player_stand_L.png");
		img[4] = new Texture("player_jump_R.png");
		img[5] = new Texture("player_jump_L.png");
	}
	public void update() {
		//if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			setStatus(0);
			walk(RIGHT);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			setStatus(1);
			walk(LEFT);
		}
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			setStatus(4);
			jump();
		}
		 ////////////auto update
		if(pos.x > (GameFireMan.WIDTH-Box.WIDTH))
		{
			pos.x = (GameFireMan.WIDTH-Box.WIDTH);
		}
		if(pos.x < 0)
		{
			pos.x = 0;
		}
		//pos.y += getUpresent();
		setUpresent(getUpresent()+GRAVITY);
	}
	public void render() {
		game.batch.draw(img[getStatus()], pos.x, pos.y);
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
	public float getLower() {
		return lower;
	}
	public void setLower(float lower) {
		this.lower = lower;
	}
	public float getUppder() {
		return uppder;
	}
	public void setUppder(float uppder) {
		this.uppder = uppder;
	}

}
