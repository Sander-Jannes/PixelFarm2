package com.sj.pixelfarm.core.utils;

import com.sj.pixelfarm.Settings;
import com.sj.pixelfarm.core.item.Item;


public class Logic {
    /* Very important to store this because `.values()` returns a copy, so calling it in a loop = memory leak */
    private static final Item.Quality[] QUALITIES = Item.Quality.values();

    /* Converts Item.Quality to tile quality (Float) */
    public static float itemQualityToTileQuality(Item.Quality quality) {
        return quality.ordinal() * 25 + 12.5f;
    }

    /* Converts tile quality (float) to Item.Quality */
    public static Item.Quality tileQualityToItemQuality(float quality) {
        return QUALITIES[(int) quality / 25];
    }

    /* Converts itemStack value range to popup range */
    public static float itemRangeToPopupRange(float value) {
        return ((value / 100) * (230 - 30));
    }

    /* Converts itemStack quality (float) to the quality level (int) */
    public static int getQualityLevel(float n) {
        return (int) (n / 25);
    }

    public static float calculateUnits(float units, float seconds) {
        return units / seconds / Settings.FPS;
    }
}
