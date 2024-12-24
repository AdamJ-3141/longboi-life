package com.spacecomplexity.longboilife.game.utils;

/**
 * A class to create a satisfaction modifier, whether
 * global or for a specific accommodation.
 * Relative modifiers will be multiplied by the satisfaction score.
 * Absolute modifiers will be added.
 * Satisfaction is still clamped to 0 < s < 1
 */
public class SatisfactionModifier {

    public float relative;
    public float absolute;

    /**
     * Constructs satisfaction modifier, defaults to
     * relative = 1,
     * absolute = 0.
     */
    public SatisfactionModifier() {
        this.relative = 1f;
        this.absolute = 0f;
    }

    /**
     * Constructs satisfaction modifier with inputted values.
     * @param relativeModifier the relative modifier multiplied.
     * @param absoluteModifier the absolute modifier added.
     */
    public SatisfactionModifier(float relativeModifier, float absoluteModifier) {
        this.relative = relativeModifier;
        this.absolute = absoluteModifier;
    }

    /**
     * Constructs satisfaction modifier with one inputted value.
     * @param modifier the modifier value.
     * @param absolute boolean indicating true=absolute, false=relative.
     */
    public SatisfactionModifier(float modifier, boolean absolute) {
        if (absolute) {
            this.absolute = modifier;
            this.relative = 1f;
        } else {
            this.relative = modifier;
            this.absolute = 0.0f;
        }
    }
}
