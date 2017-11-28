package com.manescapeafire.chanchai;

import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter{
	private GameFireMan game;
	private WorldGame world;
	public GameScreen(GameFireMan game) {
		this.game = game;
		this.world = new WorldGame(game);
	}
	private void update(float delta) {
		world.update();
    }
    public void render(float delta) {
		game.batch.begin();
		world.render();
		game.batch.end();
    	update(delta);
    }
}