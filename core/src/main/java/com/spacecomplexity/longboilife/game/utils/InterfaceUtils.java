package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Class for utils relating to UI.
 */
public class InterfaceUtils {
    /**
     * Enable the actor, recursively enabling its parents so it can be used.
     *
     * @param actor the actor to enable.
     */
    static public void enableActor(Actor actor) {
        actorEnable(actor);

        Actor parent = actor.getParent();
        if (parent != null) {
            enableActor(parent);
        }
    }

    /**
     * Enable an actor and all its children.
     * @param actor the actor to enable.
     */
    static public void enableChildren(Actor actor) {
        actorEnable(actor);
        if (actor instanceof Group) {
            for (Actor child : ((Group) actor).getChildren()) {
                enableChildren(child);
            }
        }
    }

    /**
     * Enable all UI elements on a Stage.
     *
     * @param stage the stage to enable all UI elements on.
     */
    static public void enableAllActors(Stage stage) {
        for (Actor actor : stage.getActors()) {
            enableActorRec(actor);
        }
    }

    /**
     * Recursively enable the actor then enable all its children.
     *
     * @param actor the actor to start recursively enabling from.
     */
    static private void enableActorRec(Actor actor) {
        actorEnable(actor);

        if (actor instanceof Group) {
            for (Actor child : ((Group) actor).getChildren()) {
                enableActorRec(child);
            }
        }
    }

    /**
     * Disable all UI elements on a Stage.
     *
     * @param stage the stage to disable all UI elements on.
     */
    static public void disableAllActors(Stage stage) {
        for (Actor actor : stage.getActors()) {
            disableActorRec(actor);
        }
    }

    /**
     * Recursively disable the actor then disable all its children.
     *
     * @param actor the actor to start recursively disabling from.
     */
    static private void disableActorRec(Actor actor) {
        actorDisable(actor);

        if (actor instanceof Group) {
            for (Actor child : ((Group) actor).getChildren()) {
                disableActorRec(child);
            }
        }
    }

    /**
     * Enable a single actor by making it touchable.
     *
     * @param actor the actor to enable.
     */
    static private void actorEnable(Actor actor) {
        actor.setTouchable(Touchable.enabled);
        // Remove tint
        actor.setColor(Color.WHITE);
    }

    /**
     * Disable a single actor by making it untouchable.
     *
     * @param actor the actor to disable.
     */
    static private void actorDisable(Actor actor) {
        actor.setTouchable(Touchable.disabled);
        // Add tint to show it is disabled
        actor.setColor(Color.LIGHT_GRAY);
    }

    /**
     * Gets the mouse's current position within the viewport frame specified.
     * @param viewport  the viewport to be used.
     * @return          the mouse's current position.
     */
    public static Vector2 getMousePositionInViewport(Viewport viewport) {
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(mouse);
        return new Vector2(mouse.x, mouse.y);
    }

}
