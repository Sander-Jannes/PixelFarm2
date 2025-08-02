package com.sj.pixelfarm.world;

public enum TileType {

    FIELD("tiles", 14),
    ROTTEN_FIELD("tiles", 1),
    LETTUCE("crops", 1);

    public final String tilesetName;
    public final int id;

    TileType(String tilesetName, int tileId) {
        this.tilesetName = tilesetName;
        this.id = tileId;
    }
}
