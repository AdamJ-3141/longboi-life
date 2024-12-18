package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

/** Launches the headless application. Can be converted into a utilities project or a server application. */
public class HeadlessLauncher {

    public static void main (String[] arg) {
        initialize();
    }

    private static HeadlessMain main;
    private static HeadlessApplication application;

    public static HeadlessMain initialize() {
        if (application == null) {
            main = new HeadlessMain();
            application = new HeadlessApplication(main, getDefaultConfiguration());
        }
        return main;
    }

    private static HeadlessApplicationConfiguration getDefaultConfiguration() {
        HeadlessApplicationConfiguration configuration = new HeadlessApplicationConfiguration();
        configuration.updatesPerSecond = -1; // When this value is negative, Main#render() is never called.
        return configuration;
    }
}
