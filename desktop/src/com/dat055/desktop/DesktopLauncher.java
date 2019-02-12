package com.dat055.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dat055.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Shadow World";
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = true;
		//config.fullscreen = true;
		new LwjglApplication(new Game(), config);
	}
}
