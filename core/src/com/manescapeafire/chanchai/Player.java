package com.manescapeafire.chanchai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private GameFireMan game;
	private Vector2 pos;
	private Texture img;
	public Player(GameFireMan game) {
		this.game = game;
		this.pos = new Vector2(0,0);
		this.img = new Texture("player_stand.png");
	}
	public void update() {
	}
	public void render() {
		game.batch.draw(img, pos.x, pos.y);
	}
}
