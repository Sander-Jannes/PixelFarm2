package com.sj.pixelfarm.core.utils;

import com.badlogic.gdx.maps.tiled.TiledMapTile;


public class Tile {

    public TiledMapTile tile;

    public Tile() { }

    public Tile(TiledMapTile tile) {
        this.tile = tile;
    }

    public void put(String key, Object value) {
        tile.getProperties().put(key, value);
    }

    public void transfer(TiledMapTile source, String key) {
        put(key, getProperty(source, key));
    }

    public <T> T getProperty(String value, Class<T> clazz) { return tile.getProperties().get(value, clazz); }

    public Object getProperty(TiledMapTile tile, String value) {
        return tile.getProperties().get(value);
    }

    public boolean equals(String key, Object value) {
        return tile != null && tile.getProperties().get(key).equals(value);
    }

    public void updateValueByN(String key, Float n, float min, float max) {
        float newValue = getProperty(key, n.getClass()) + n;
        if (newValue + n >= min && newValue + n <= max) put(key, newValue + n);
    }

    public void updateValueByN(String key, Float n, float min) {
        float newValue = getProperty(key, n.getClass()) + n;
        if (newValue + n >= min) put(key, newValue + n);
    }
}
