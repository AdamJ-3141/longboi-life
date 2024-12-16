package com.spacecomplexity.longboilife.game.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;

import java.util.stream.Stream;

/**
 * Contains a list of all buildings, including there default data.
 */
public enum BuildingType {
    GREGGS("Greggs", new Texture("buildings/greggs.png"), new Vector2Int(2, 2), BuildingCategory.FOOD, 40000),
    NANDOS("Nandos", new Texture("buildings/nandos.png"), new Vector2Int(3, 2), BuildingCategory.FOOD, 100000),
    DININGHALL("Dining Hall", new Texture("buildings/dining_hall.png"), new Vector2Int(3, 3), BuildingCategory.FOOD, 10000),
    SUPERMARKET("Supermarket", new Texture("buildings/supermarket.png"), new Vector2Int(4, 2), BuildingCategory.FOOD, 60000),
    KEBABVAN("Kebab Van", new Texture("buildings/kebab_van.png"), new Vector2Int(1, 1), BuildingCategory.FOOD, 5000),

    LIBRARY("Library", new Texture("buildings/library.png"), new Vector2Int(4, 4), BuildingCategory.EDUCATIONAL, 100000),
    LECTURETHEATRE("Lecture Theatre", new Texture("buildings/lecture_theatre.png"), new Vector2Int(3, 3), BuildingCategory.EDUCATIONAL, 80000),
    RESEARCHCENTRE("Research Centre", new Texture("buildings/research_centre.png"), new Vector2Int(2, 2), BuildingCategory.EDUCATIONAL, 50000),
    CLASSROOMS("Classrooms", new Texture("buildings/classrooms.png"), new Vector2Int(3, 2), BuildingCategory.EDUCATIONAL, 20000),

    GYM("Gym", new Texture("buildings/gym.png"), new Vector2Int(4, 3), BuildingCategory.RECREATIONAL, 100000),
    POOL("Swimming Pool", new Texture("buildings/swimming_pool.png"), new Vector2Int(3, 2), BuildingCategory.RECREATIONAL, 50000),
    SPORTSBAR("Sports Bar", new Texture("buildings/sports_bar.png"), new Vector2Int(2, 2), BuildingCategory.RECREATIONAL, 30000),
    STUDENTUNION("Student Union", new Texture("buildings/student_union.png"), new Vector2Int(3, 2), BuildingCategory.RECREATIONAL, 40000),
    PARK("Park", new Texture("buildings/park.png"), new Vector2Int(5, 5), BuildingCategory.RECREATIONAL, 10000),
    FOOTBALLFIELD("Football Field", new Texture("buildings/football_field.png"), new Vector2Int(2, 4), BuildingCategory.RECREATIONAL, 80000),
    BIKESTORE("Bike Storage", new Texture("buildings/bike_storage.png"), new Vector2Int(1, 1), BuildingCategory.RECREATIONAL, 5000),

    HALLS("Halls", new Texture("buildings/halls.png"), new Vector2Int(3, 3), BuildingCategory.ACCOMMODATION, 12000),
    SHAREDDORM("Shared Dorm", new Texture("buildings/shared_dorm.png"), new Vector2Int(3, 3), BuildingCategory.ACCOMMODATION, 8000),
    LUXURYFLATS("Luxury Flats", new Texture("buildings/luxury_flats.png"), new Vector2Int(3, 3), BuildingCategory.ACCOMMODATION, 20000),

    ROAD("Road", new Texture("buildings/roads/straight.png"), new Vector2Int(1, 1), BuildingCategory.PATHWAY, 100),
    ;

    private final String displayName;
    private final Texture texture;
    private final Vector2Int size;
    private final BuildingCategory category;
    private final float cost;

    /**
     * Create a {@link BuildingType} with specified attributes.
     *
     * @param displayName the name to display when selecting this building.
     * @param texture     the texture representing the building.
     * @param size        the size of the building (in tiles).
     * @param category    the category of the building.
     * @param cost        the cost to place the building.
     */
    BuildingType(String displayName, Texture texture, Vector2Int size, BuildingCategory category, float cost) {
        this.displayName = displayName;
        this.texture = texture;
        this.size = size;
        this.category = category;
        this.cost = cost;
    }


    public Texture getTexture() {
        return texture;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Vector2Int getSize() {
        return size;
    }

    public BuildingCategory getCategory() {
        return category;
    }

    public float getCost() {
        return cost;
    }

    public static BuildingType[] getBuildingsOfType(BuildingCategory category) {
        return Stream.of(BuildingType.values())
            .filter(buildingType -> buildingType.getCategory().equals(category))
            .toArray(BuildingType[]::new);

    }

    /**
     * Will dispose of the all loaded assets (like textures).
     * <p>
     * <strong>Warning:</strong> Once disposed of no attributes will be able to be reloaded, which could lead to undefined behaviour.
     */
    public void dispose() {
        texture.dispose();
    }
}
