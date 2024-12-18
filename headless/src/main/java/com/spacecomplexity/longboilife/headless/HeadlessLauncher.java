package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.spacecomplexity.longboilife.Main;

/** Launches the headless application. Can be converted into a utilities project or a server application. */
public class HeadlessLauncher {

    public static void main (String[] arg) {
        initialize();
    }

    private static Main main;
    private static HeadlessApplication application;

    public static Main initialize() {
        if (application == null) {
            main = new Main();
            application = new HeadlessApplication(main, getDefaultConfiguration());
        }
        return main;
    }

    private static HeadlessApplicationConfiguration getDefaultConfiguration() {
        HeadlessApplicationConfiguration configuration = new HeadlessApplicationConfiguration();
        configuration.updatesPerSecond = -1; // When this value is negative, Main#render() is never called.
        //// If the above line doesn't compile, it is probably because the project libGDX version is older.
        //// In that case, uncomment and use the below line.
        //configuration.renderInterval = -1f; // When this value is negative, Main#render() is never called.
        return configuration;
    }
}
