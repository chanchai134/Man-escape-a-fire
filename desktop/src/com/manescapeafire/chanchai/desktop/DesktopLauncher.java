package com.manescapeafire.chanchai.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.manescapeafire.chanchai.GameFireMan;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameFireMan.WIDTH;
        config.height = GameFireMan.HEIGHT;
		new LwjglApplication((ApplicationListener) new GameFireMan(), config);
	}
}
