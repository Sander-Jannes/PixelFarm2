package com.sj.pixelfarm.core.utils;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.item.actions.ActionProps;
import com.sj.pixelfarm.world.TileSetNames;

import java.util.function.Consumer;


public class TileHelper {

    private static final Tile helperTile = new Tile();

    public static void processTile(TiledMapTile target, Consumer<Tile> consumer) {
        if (target != null) {
            helperTile.tile = target;
            consumer.accept(helperTile);
        }
    }

    public static Tile getHelperTile(TiledMapTile tile) {
        helperTile.tile = tile;
        return helperTile;
    }

    public static class Tile {

        public TiledMapTile tile;

        public Tile() { }

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

        public boolean isHarvestable() {
            if (equals("group", TileSetNames.CROPS)) {
                return equals("level", 4);
            }
            return false;
        }

        public void updateValueByN(String key, Float n, float min, float max) {
            float newValue = getProperty(key, n.getClass()) + n;
            if (newValue + n >= min && newValue + n <= max) put(key, newValue + n);
        }

        public void updateValueByN(String key, Float n, float min) {
            float newValue = getProperty(key, n.getClass()) + n;
            if (newValue + n >= min) put(key, newValue + n);
        }

        public Item.Quality getItemQuality() {
            return Logic.tileQualityToItemQuality(getProperty("quality", Float.class));
        }

        public void transferProps(TiledMapTile source) {
            transfer(source,"water");
            transfer(source,"fertilizer");
            transfer(source, "quality");
            transfer(source, "item");
            transfer(source, "props");
            ActionProps.Plant plantProperties = getProperty("props", ActionProps.Plant.class);
            put("timeTillRotten", plantProperties.timeTillRotten());
            put("growTime", plantProperties.growTimePerLevel());
            put("waterLevel", plantProperties.waterLevel());
            put("fertilizerLevel", plantProperties.fertilizerLevel());
        }

        public void init(ActionProps.Plant props) {
            put("water", 0f);
            put("fertilizer", 0f);
            put("quality", Logic.itemQualityToTileQuality(Item.Quality.GOOD));
            put("props", props);
            put("item", props.item());
            put("timeTillRotten", props.timeTillRotten());
            put("growTime", props.growTimePerLevel());
            put("waterLevel", props.waterLevel());
            put("fertilizerLevel", props.fertilizerLevel());
        }
    }
}
