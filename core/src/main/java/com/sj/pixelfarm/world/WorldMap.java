package com.sj.pixelfarm.world;

import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Null;
import com.sj.pixelfarm.Settings;
import com.sj.pixelfarm.core.utils.TileHelper;

import java.util.HashMap;


public final class WorldMap implements Disposable {

    private final TiledMap map;
    private final HashMap<String, Integer> offsets = new HashMap<>();

    public WorldMap() {
        map = new TmxMapLoader().load(Settings.WORLD_MAP);

        int start = 0;
        for (TiledMapTileSet tileSet : map.getTileSets()) {
            System.out.println(tileSet.getName() + " " + tileSet.size());
            offsets.put(tileSet.getName(), start);
            start += tileSet.size();
        }
    }

    public TiledMap getMap() {
        return map;
    }

    public void removeCell(GridPoint2 pos, int z) {
        TiledMapTileLayer.Cell cell = getCell(pos, z);
        cell.setTile(null);
    }

    public void upgradeCrop(GridPoint2 pos, int z, TiledMapTile tile) {
        TiledMapTileLayer.Cell cell = getCell(pos, z);

        /* +1 for the next level */
        StaticTiledMapTile newTile = getNewTile(TileSetNames.CROPS, (tile.getId() - offsets.get(TileSetNames.CROPS)) + 1);
        TileHelper.processTile(newTile, t -> t.transferProps(tile));
        cell.setTile(newTile);
    }

    public @Null TiledMapTile getTile(int row, int col, int z) {
        TiledMapTileLayer.Cell cell = getLayer(z).getCell(row, col);

        if (cell != null) {
            return cell.getTile();
        }
        return null;
    }

    public @Null TiledMapTile getTile(GridPoint2 point, int z) {
      return getTile(point.x, point.y, z);
    }

    public TiledMapTileLayer.Cell setCell(GridPoint2 pos, int z, TileType tile) {
        TiledMapTileLayer.Cell cell = getCell(pos, z);
        if (cell == null) {
            cell = createCell(pos, z);
        }
        StaticTiledMapTile newTile = getNewTile(tile);
        cell.setTile(newTile);
        return cell;
    }

    private StaticTiledMapTile getNewTile(TileType tile) {
        return getNewTile(tile.tilesetName, tile.id);
    }

    private StaticTiledMapTile getNewTile(String tilesetName, int id) {
        TiledMapTileSet tileSet = map.getTileSets().getTileSet(tilesetName);

        if (tileSet == null) {
            throw new RuntimeException("No such tileset: " + tilesetName);
        }

        StaticTiledMapTile newTile = (StaticTiledMapTile) tileSet.getTile(id + offsets.get(tilesetName));

        if (newTile == null) {
            throw new RuntimeException("No such tile with id " + (id + offsets.get(tilesetName)));
        }
        return new StaticTiledMapTile(newTile);
    }

    private TiledMapTileLayer.Cell createCell(GridPoint2 pos, int z) {
        TiledMapTileLayer layer = getLayer(z);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        layer.setCell(pos.x, pos.y, cell);
        return cell;
    }

    private TiledMapTileLayer.Cell getCell(GridPoint2 pos, int z) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(z);
        return layer.getCell(pos.x, pos.y);
    }

    public TiledMapTileLayer getLayer(int z) {
        return (TiledMapTileLayer) map.getLayers().get(z);
    }

    @Override
    public void dispose() {
        map.dispose();
    }
}
