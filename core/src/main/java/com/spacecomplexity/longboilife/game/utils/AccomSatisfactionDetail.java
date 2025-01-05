package com.spacecomplexity.longboilife.game.utils;

import com.spacecomplexity.longboilife.game.building.BuildingCategory;

import java.util.HashMap;

/**
 * Helper class to collect all details about accommodation satisfaction.
 */
public class AccomSatisfactionDetail {

    public float qualityMultiplier;
    public HashMap<BuildingCategory, Float> categoryScores;
    public float totalSatisfaction;
    public SatisfactionModifier satisfactionModifier;

    public AccomSatisfactionDetail(float qualityMultiplier,
                                   HashMap<BuildingCategory, Float> categoryScores,
                                   float totalSatisfaction,
                                   SatisfactionModifier satisfactionModifier) {
        this.qualityMultiplier = qualityMultiplier;
        this.categoryScores = categoryScores;
        this.totalSatisfaction = totalSatisfaction;
        this.satisfactionModifier = satisfactionModifier;
    }
}
