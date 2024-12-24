package com.spacecomplexity.longboilife.game.globals;

import com.badlogic.gdx.Input;

/**
 * Stores the keybindings of different actions.
 */
public enum Keybindings {
    CAMERA_UP("Camera Up", Input.Keys.W, "Right Click (Drag)"),
    CAMERA_LEFT("Camera Left", Input.Keys.A, "Right Click (Drag)"),
    CAMERA_DOWN("Camera Down", Input.Keys.S, "Right Click (Drag)"),
    CAMERA_RIGHT("Camera Right", Input.Keys.D, "Right Click (Drag)"),
    CAMERA_ZOOM_IN("Camera Zoom In", Input.Keys.Q, "Scroll Up"),
    CAMERA_ZOOM_OUT("Camera Zoom Out", Input.Keys.E, "Scroll Down"),
    FULLSCREEN("Fullscreen", Input.Keys.F11, "<none>"),
    CANCEL("Cancel", Input.Keys.ESCAPE, "<none>"),
    PAUSE("Pause", Input.Keys.SPACE, "<none>"),
    KEEP_PLACING("Keep Placing (Hold)", Input.Keys.SHIFT_LEFT, "<none>"),
    SHOW_DETAIL("Show Satisfaction Detail", Input.Keys.SHIFT_LEFT, "<none>"),
    ;

    private final int key;
    private final String displayName;
    private final String mouse;

    /**
     * Initialises the required data to a keybind.
     *
     * @param displayName Describes what the key triggers.
     * @param key the key assigned to the enum attribute.
     * @param mouse the mouse button/movement assigned
     */
    Keybindings(String displayName, int key, String mouse) {
        this.displayName = displayName;
        this.key = key;
        this.mouse = mouse;
    }

    /**
     * Return the key code assigned to the enum attribute.
     *
     * @return the key code assigned.
     */
    public int getKey() {
        return key;
    }

    public String getDisplayName() { return displayName; }

    public String getMouse() { return mouse; }
}
