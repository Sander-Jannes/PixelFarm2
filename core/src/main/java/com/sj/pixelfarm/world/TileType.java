package com.sj.pixelfarm.world;

public enum TileType {

    FIELD("tiles", 3),
    WATER_ANIMATION("tiles", 61),
    FERTILIZER_ANIMATION("tiles", 71),
    LETTUCE("crops", 1),
    CARROT("crops", 5),
    TOMATO("crops", 9),
    CAULIFLOWER("crops", 13),
    BROCCOLI("crops", 17),
    PUMPKIN("crops", 21),
    CUCUMBER("crops", 25),
    ONION("crops", 29),
    EGGPLANT("crops", 33);

    public final String tilesetName;
    public final int id;

    TileType(String tilesetName, int tileId) {
        this.tilesetName = tilesetName;
        this.id = tileId;
    }
}
