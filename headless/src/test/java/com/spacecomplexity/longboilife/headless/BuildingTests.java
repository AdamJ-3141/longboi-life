package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class BuildingTests extends AbstractHeadlessGdxTest{
    @Test
    public void testSetPosition() {
        Vector2Int startVector = new Vector2Int(2, 3);
        Vector2Int endVector = new Vector2Int(5, 7);
        Building building = new Building(BuildingType.GREGGS, startVector);
        building.setPosition(endVector);
        assertSame(building.getPosition(), endVector, "The building's position should have changed correctly");
    }
}
