package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private GameFireMan game;
	private Vector2 pos;
	private Texture img;
	private final int SPEED = 5;
	public Player(GameFireMan game) {
		this.game = game;
		pos = new Vector2(0,0);
		img = new Texture("player_stand.png");
	}
	public void update() {
		 if(Gdx.input.isKeyPressed(Keys.RIGHT) && pos.x < GameFireMan.WIDTH) {
			 pos.x += SPEED;
		 }
		 if(Gdx.input.isKeyPressed(Keys.LEFT) && pos.x > 0) {
			 pos.x -= SPEED;
		 }
		 if(Gdx.input.isKeyPressed(Keys.UP)) {
			 jump();
		 }
	}
	public void render() {
		game.batch.draw(img, pos.x, pos.y);
	}
	public void jump() {
		pos.y += 5;
	}
}
