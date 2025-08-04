package com.sj.pixelfarm;

import com.sj.pixelfarm.core.utils.Logic;


public final class Settings {
    public static final int FPS = 60;

    // Config
    public static final String TILE_SETS_PATH = "config/tileSetsConfig.json";

    // World settings
    public static final String WORLD_MAP = "maps/world4.tmx";

    public static final float WORLD_OFFSET_X = 65f;
    public static final float WORLD_OFFSET_Y = 3.8f;

    public static final float MOVE_MARGIN_X = 20f;
    public static final float MOVE_MARGIN_Y = 20f;

    // Crop settings
    public static final float CROP_WATER_INTAKE = Logic.calculateUnits(25f, 120f);
    public static final float CROP_FERTILIZER_INTAKE = Logic.calculateUnits(25f, 120f);

    public static final float QUALITY_DEBUFF_WATER = Logic.calculateUnits(25f, 30f);
    public static final float QUALITY_DEBUFF_FERTILIZER = Logic.calculateUnits(25f, 45f);

    public static final float QUALITY_BUFF_WATER = Logic.calculateUnits(25f, 60f);
    public static final float QUALITY_BUFF_FERTILIZER =  Logic.calculateUnits(25f, 90f);
}
