package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.Test;

import  static org.junit.jupiter.api.Assertions.assertTrue;

public class AssetTests extends AbstractHeadlessGdxTest {
    // have to use direct file addresses as original group input data into methods and classes directly instead of using variables
    @Test
    public void buildingAssetTest() {
        assertTrue(Gdx.files.internal("buildings/greggs.png").exists(),
            "Greggs texture missing");
        assertTrue(Gdx.files.internal("buildings/nandos.png").exists(),
            "Nandos texture missing");
        assertTrue(Gdx.files.internal("buildings/library.png").exists(),
            "Library texture missing");
        assertTrue(Gdx.files.internal("buildings/gym.png").exists(),
            "Gym texture missing");
        assertTrue(Gdx.files.internal("buildings/halls.png").exists(),
            "Halls texture missing");
        assertTrue(Gdx.files.internal("buildings/bike_storage.png").exists(),
            "Bike storage texture missing");
        assertTrue(Gdx.files.internal("buildings/classrooms.png").exists(),
            "Classrooms texture missing");
        assertTrue(Gdx.files.internal("buildings/dining_hall.png").exists(),
            "Dining hall texture missing");
        assertTrue(Gdx.files.internal("buildings/football_field.png").exists(),
            "Football field texture missing");
        assertTrue(Gdx.files.internal("buildings/kebab_van.png").exists(),
            "Kebab van texture missing");
        assertTrue(Gdx.files.internal("buildings/lecture_theatre.png").exists(),
            "Lecture theatre texture missing");
        assertTrue(Gdx.files.internal("buildings/luxury_flats.png").exists(),
            "Luxury flats texture missing");
        assertTrue(Gdx.files.internal("buildings/park.png").exists(),
            "Park texture missing");
        assertTrue(Gdx.files.internal("buildings/research_centre.png").exists(),
            "Research centre texture missing");
        assertTrue(Gdx.files.internal("buildings/shared_dorm.png").exists(),
            "Shared dorm texture missing");
        assertTrue(Gdx.files.internal("buildings/sports_bar.png").exists(),
            "Sports bar texture missing");
        assertTrue(Gdx.files.internal("buildings/student_union.png").exists(),
            "Student union texture missing");
        assertTrue(Gdx.files.internal("buildings/supermarket.png").exists(),
            "Supermarket texture missing");
        assertTrue(Gdx.files.internal("buildings/swimming_pool.png").exists(),
            "Swimming pool texture missing");
    }

    @Test
    public void roadAssetTest() {
        assertTrue(Gdx.files.internal("buildings/roads/3-way.png").exists(),
            "3-way road texture missing");
        assertTrue(Gdx.files.internal("buildings/roads/4-way.png").exists(),
            "4-way road texture missing");
        assertTrue(Gdx.files.internal("buildings/roads/corner.png").exists(),
            "Corner road texture missing");
        assertTrue(Gdx.files.internal("buildings/roads/straight.png").exists(),
            "Straight road texture missing");
    }

    @Test
    public void musicAssetTest() {
        assertTrue(Gdx.files.internal("audio/music/A_New_Days_Hurry_cover.png").exists(),
            "A New Day's Hurry cover texture missing");
        assertTrue(Gdx.files.internal("audio/music/Reflective_District_cover.png").exists(),
            "Reflective District cover texture missing");
        assertTrue(Gdx.files.internal("audio/music/One Man Symphony - A New Day's Hurry.mp3").exists(),
            "A New Day's Hurry mp3 missing");
        assertTrue(Gdx.files.internal("audio/music/One Man Symphony - A New Day Begins.mp3").exists(),
            "A New Day Begins mp3 missing");
        assertTrue(Gdx.files.internal("audio/music/One Man Symphony - Serene.mp3").exists(),
            "Serene mp3 missing");
    }

    @Test
    public void soundEffectAssetTests() {
        assertTrue(Gdx.files.internal("audio/sound_effects/build_building.wav").exists(),
            "Build building wav missing");
        assertTrue(Gdx.files.internal("audio/sound_effects/build_pathway.wav").exists(),
            "Build pathway wav missing");
        assertTrue(Gdx.files.internal("audio/sound_effects/button_click.wav").exists(),
            "Button click wav missing");
        assertTrue(Gdx.files.internal("audio/sound_effects/destroyed.wav").exists(),
            "Destroyed wav missing");
        assertTrue(Gdx.files.internal("audio/sound_effects/game_begin.wav").exists(),
            "Game begin wav missing");
        assertTrue(Gdx.files.internal("audio/sound_effects/game_end.wav").exists(),
            "Game end wav missing");
        assertTrue(Gdx.files.internal("audio/sound_effects/money_up.wav").exists(),
            "Money up wav missing");
        assertTrue(Gdx.files.internal("audio/sound_effects/satisfaction_down.wav").exists(),
            "Satisfaction down wav missing");
        assertTrue(Gdx.files.internal("audio/sound_effects/satisfaction_up.wav").exists(),
            "Satisfaction up wav missing");
    }

    @Test
    public void menuAssetTest() {
        assertTrue(Gdx.files.internal("menu/background.png").exists(),
            "Background texture missing");
    }

    @Test
    public void particlesAssetTest() {
        assertTrue(Gdx.files.internal("particles/effects/broken_heart.p").exists(),
            "Broken heart particles missing");
        assertTrue(Gdx.files.internal("particles/effects/heart.p").exists(),
            "Heart particles missing");
        assertTrue(Gdx.files.internal("particles/effects/money.p").exists(),
            "Money particles missing");
        assertTrue(Gdx.files.internal("particles/images/broken_heart.png").exists(),
            "Broken heart texture missing");
        assertTrue(Gdx.files.internal("particles/images/heart.png").exists(),
            "Heart texture missing");
        assertTrue(Gdx.files.internal("particles/images/money.png").exists(),
            "Money texture missing");
    }

    @Test
    public void tilesAssetTest() {
        assertTrue(Gdx.files.internal("tiles/grass.png").exists(),
            "Grass texture missing");
        assertTrue(Gdx.files.internal("tiles/water.png").exists(),
            "Water texture missing");
    }

    @Test
    public void uiAssetTest() {
        assertTrue(Gdx.files.internal("ui/buttons/pause.png").exists(),
            "Pause texture missing");
        assertTrue(Gdx.files.internal("ui/buttons/play.png").exists(),
            "Play texture missing");
        assertTrue(Gdx.files.internal("ui/buttons/settings.png").exists(),
            "Settings texture missing");
        assertTrue(Gdx.files.internal("ui/fonts/Roboto-Medium.ttf").exists(),
            "Roboto Medium ttf missing");
        assertTrue(Gdx.files.internal("ui/fonts/Roboto-Regular.ttf").exists(),
            "Roboto Regular ttf missing");
        assertTrue(Gdx.files.internal("ui/skin/font-button.fnt").exists(),
            "Font button fnt missing");
        assertTrue(Gdx.files.internal("ui/skin/font-label.fnt").exists(),
            "Font label fnt missing");
        assertTrue(Gdx.files.internal("ui/skin/font-title.fnt").exists(),
            "Font title fnt missing");
        assertTrue(Gdx.files.internal("ui/skin/uiskin.atlas").exists(),
            "uiskin atlas missing");
        assertTrue(Gdx.files.internal("ui/skin/uiskin.json").exists(),
            "uiskin json missing");
        assertTrue(Gdx.files.internal("ui/skin/uiskin.png").exists(),
            "uiskin texture missing");
    }

    @Test
    public void miscAssetTest() {
        assertTrue(Gdx.files.internal("assets.txt").exists(),
            "assets txt missing");
        assertTrue(Gdx.files.internal("error.png").exists(),
            "error texture missing");
        assertTrue(Gdx.files.internal("map.json").exists(),
            "map json missing");
        assertTrue(Gdx.files.internal("very_important_pixel.png").exists(),
            "Very important pixel texture missing");
    }
}
