package com.mygdx.bungeeball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.bungeeball.BungeeBall;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "BungeeBall";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new BungeeBall(), config);
	}
}
