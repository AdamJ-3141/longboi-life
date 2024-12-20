package com.spacecomplexity.longboilife.game.utils;

import com.spacecomplexity.longboilife.game.building.BuildingCategory;

import java.util.HashMap;

public class AccomSatisfactionDetail {

    public float qualityMultiplier;
    public HashMap<BuildingCategory, Float> CategoryScores;
    public float totalSatisfaction;

    public AccomSatisfactionDetail(float qualityMultiplier,
                                   HashMap<BuildingCategory, Float> categoryScores,
                                   float totalSatisfaction) {
        this.qualityMultiplier = qualityMultiplier;
        this.CategoryScores = categoryScores;
        this.totalSatisfaction = totalSatisfaction;
    }
}
