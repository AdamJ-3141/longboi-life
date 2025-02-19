package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Screen;
import com.spacecomplexity.longboilife.Main;

import java.util.HashMap;

public class HeadlessMain extends Main {

    public HeadlessMain() {
        super();
    }

    public enum ScreenType {
        GAME(HeadlessGameScreen.class),
        ;

        private final Class<? extends Screen> screenClass;

        ScreenType(Class<? extends Screen> screenClass) {
            this.screenClass = screenClass;
        }

        public Class<? extends Screen> getScreenClass() {
            return screenClass;
        }
    }

    private final HashMap<ScreenType, Screen> screens = new HashMap<>();

    @Override
    public void create() {
        // Initially load the menu screen
        switchScreen(ScreenType.GAME);
    }

    public void switchScreen(ScreenType screen) {
        // Lazy loading
        if (!screens.containsKey(screen)) {
            try {
                Screen newScreen = screen.getScreenClass().getConstructor(Main.class).newInstance(this);
                screens.put(screen, newScreen);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create screen: " + screen.name(), e);
            }
        }

        // Switch to the screen
        setScreen(screens.get(screen));
    }
}
