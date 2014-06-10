package com.ricardorb.catchanimals.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ricardorb.catchanimals.CatchAnimals;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 240;
		config.width = 320;
		new LwjglApplication(new CatchAnimals(), config);
	}
}
