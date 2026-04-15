package com.ricardorb.catchanimals.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ricardorb.catchanimals.CatchAnimals;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Catch Animals");
        config.setWindowedMode(640, 480);
        config.useVsync(true);
        config.setForegroundFPS(60);

        new Lwjgl3Application(new CatchAnimals(), config);
    }
}
