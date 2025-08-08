package com.sj.pixelfarm.tasks;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Timer;
import com.sj.pixelfarm.Settings;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.utils.Logic;
import com.sj.pixelfarm.core.utils.TileHelper;
import com.sj.pixelfarm.world.TileSetNames;
import com.sj.pixelfarm.world.TileType;
import com.sj.pixelfarm.world.World;


public class TaskManager {
    public static final float TICK_RATE = 1f / com.sj.pixelfarm.Settings.FPS;
    private static final Timer timer = new Timer();

    public static void initCropGrowTask(World world) {
        final GridPoint2 tmpGridPoint2 = new GridPoint2();
        TiledMapTileLayer layer = world.worldMap.getLayer(World.Layers.DECORATION, TiledMapTileLayer.class);

        Timer.Task growTask = new Timer.Task() {
            @Override
            public void run() {
                if (world.editMode.isActive()) return;

                for (int row = 0; row < layer.getWidth(); row++) {
                    for (int col = 0; col < layer.getHeight(); col++) {
                        TiledMapTileLayer.Cell cell = layer.getCell(row, col);

                        if (cell == null) continue;

                        TiledMapTile tile = cell.getTile();
                        int finalRow = row;
                        int finalCol = col;

                        TileHelper.processTile(tile, t -> {
                            if (!t.equals("group", TileSetNames.CROPS)) return;

                            float water = t.getProperty("water", Float.class);
                            float fertilizer = t.getProperty("fertilizer", Float.class);
                            int level = t.getProperty("level", Integer.class);

                            if (level == 4) return;

                            // Update water
                            if (water > Settings.CROP_WATER_INTAKE) {
                                t.updateValueByN("water", -Settings.CROP_WATER_INTAKE, 0f, 100f);
                            }

                            // Update fertilizer
                            if (fertilizer > Settings.CROP_FERTILIZER_INTAKE) {
                                t.updateValueByN("fertilizer", -Settings.CROP_FERTILIZER_INTAKE, 0f, 100f);
                            }

                            // Update quality with water
                            if (t.getProperty("waterLevel", Integer.class) == Logic.getQualityLevel(water)) {
                                t.updateValueByN("quality", Settings.QUALITY_BUFF_WATER, 0f, 100f);
                            } else {
                                t.updateValueByN("quality", -Settings.QUALITY_DEBUFF_WATER, 0f, 100f);
                            }

                            // Update quality with fertilizer
                            if (t.getProperty("fertilizerLevel", Integer.class) == Logic.getQualityLevel(fertilizer)) {
                                t.updateValueByN("quality", Settings.QUALITY_BUFF_FERTILIZER, 0f, 100f);

                            } else {
                                t.updateValueByN("quality", -Settings.QUALITY_DEBUFF_FERTILIZER, 0f, 100f);
                            }

                            /* Too long a BAD quality results in rotten plants... */
                            if (Logic.tileQualityToItemQuality(t.getProperty("quality", Float.class)) == Item.Quality.BAD) {
                                t.updateValueByN("timeTillRotten", -TICK_RATE, -TICK_RATE);

                                if (t.getProperty("timeTillRotten", Float.class) <= TICK_RATE) {
                                    tmpGridPoint2.set(finalRow, finalCol);
                                    world.worldMap.setCell(tmpGridPoint2, World.Layers.GROUND, TileType.ROTTEN_FIELD);
                                    world.worldMap.removeCell(tmpGridPoint2, World.Layers.DECORATION);
                                }
                            }

                            // let the growth time count down
                            t.updateValueByN("growTime", -TICK_RATE, -TICK_RATE);

                            if (t.getProperty("growTime", Float.class) <= TICK_RATE) {
                                tmpGridPoint2.set(finalRow, finalCol);
                                world.worldMap.upgradeCrop(tmpGridPoint2, World.Layers.DECORATION, tile);
                            }
                        });
                    }
                }
            }
        };
        timer.scheduleTask(growTask,0, TICK_RATE);
    }

    public static void stopTasks() {
        timer.stop();
        timer.clear();
    }
}
