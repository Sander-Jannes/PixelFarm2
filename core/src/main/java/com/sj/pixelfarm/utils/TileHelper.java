package com.sj.pixelfarm.utils;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.sj.pixelfarm.items.Item;
import com.sj.pixelfarm.items.actions.ActionProps;
import com.sj.pixelfarm.items.actions.ActionTarget;
import com.sj.pixelfarm.core.utils.Tile;
import com.sj.pixelfarm.world.types.TileSetNames;

import java.util.function.Consumer;


public final class TileHelper {

    private static final WorldTile helperTile = new WorldTile();

    public static void processTile(TiledMapTile target, Consumer<WorldTile> consumer) {
        if (target != null) {
            helperTile.tile = target;
            consumer.accept(helperTile);
        }
    }

    public static class WorldTile extends Tile {

        public WorldTile() {
            super();
        }

        public WorldTile(TiledMapTile tile) {
            super(tile);
        }

        public boolean evaluateActionTarget(ActionTarget target) {
            return equals(target.property, target.name);
        }

        public boolean isHarvestable() {
            if (equals("group", TileSetNames.CROPS)) {
                return equals("level", 4);
            }
            return false;
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
            put("growTime", props.growTimePerLevel());
            put("waterLevel", props.waterLevel());
            put("fertilizerLevel", props.fertilizerLevel());
        }
    }
}
