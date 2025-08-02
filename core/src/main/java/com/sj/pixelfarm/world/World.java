package com.sj.pixelfarm.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.sj.pixelfarm.*;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.input.Interactions;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.itemgrid.ItemStack;
import com.sj.pixelfarm.core.itemgrid.ItemStackSlot;
import com.sj.pixelfarm.core.mem.Assets;
import com.sj.pixelfarm.core.mem.PoolManager;
import com.sj.pixelfarm.core.ui.effects.UIEffects;
import com.sj.pixelfarm.core.utils.TileHelper;


public class World extends Actor implements Disposable {

    /** Tile scale in pixels */
    private final static float SCALE = 128f;
    /** Inverse scale */
    private final static float INV_SCALE = 1.f/SCALE;
    /** The amount of zoom per scroll */
    private final static float ZOOM = 0.1f;
    /** How far you can zoom out */
    private final static float MAX_ZOOM = 100f;
    /** How far you can zoom in, 0 is close to the map  */
    private final static float MIN_ZOOM = 0.5f;

    private final IsometricTiledMapRenderer renderer;
    public final ExtendViewport viewport;
    public OrthographicCamera worldCamera;
    public WorldMap worldMap = new WorldMap();

    /* Cursor */
    private static final TextureRegion cursorImage = Assets.getAtlasTexture("world/cursor");
    private final Vector2 cursorBlitPos = new Vector2();

    public float lastX, lastY;
    public boolean dragging;

    public World(ExtendViewport vp) {
        setName(Entities.WORLD);
        setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        viewport = vp;
        worldCamera = (OrthographicCamera) viewport.getCamera();
        renderer = new IsometricTiledMapRenderer(worldMap.getMap(), INV_SCALE);

        Events.on(EventType.DropItemOnWorld.class, e -> dropItem(e.itemStackSlot(), e.interaction()));
    }

    public void draw() {
        // Update de camera en stel de renderer in
        worldCamera.update();
        renderer.setView(worldCamera);
        renderer.render();

        // Render de cursor op de batch van de renderer
        renderCursor();
    }

    private void renderCursor() {
        GridPoint2 gridPosition = WorldUtils.getGridPosFromMouse(viewport);
        cursorBlitPos.set(WorldUtils.gridToVec(gridPosition));
        TiledMapTile selectedCell = worldMap.getTile(gridPosition, Layers.GROUND);

        TileHelper.processTile(selectedCell, tile -> {
            boolean selectable = tile.getProperty("selectable", Boolean.class);
            if (selectable) {
                renderer.getBatch().begin();
                renderer.getBatch().draw(cursorImage, cursorBlitPos.x, cursorBlitPos.y, 1f, 1f);
                renderer.getBatch().end();
            }
        });
    }

    public void dropItem(ItemStackSlot itemStackSlot, Interactions interaction) {
        if (itemStackSlot.isEmpty()) return;

        ItemStack itemStack = itemStackSlot.getObj();
        Item.ActionInfo info = itemStack.item.interactionMap.get(interaction);
        GridPoint2 pos = WorldUtils.getGridPosFromMouse(viewport);

        if (info == null) return;

        TiledMapTile tile = worldMap.getTile(pos, info.layer());

        TileHelper.processTile(tile, t -> {
            if (t.equals(info.targetProperty(), info.targetName())) {
                switch (info.action()) {
                    case HARVEST: {
                        if (t.isHarvestable()) {
                            Item harvestItem = t.getProperty("item", Item.class);
                            int amount = t.getProperty("props", Item.PlantProps.class).harvestQuantity();

                            Events.fire(new EventType.PutItemInGridEvent(
                                PoolManager.obtain(harvestItem, amount, t.getItemQuality()),
                                Entities.HOTBAR,
                                () -> {
                                    worldMap.removeCell(pos, info.layer());
                                    Events.fire(new EventType.ShowPopupObject(harvestItem.image, "+" + amount, UIEffects::applyFadeOutEffect));
                                }));
                        }

                        break;
                    }

                    case MAP_CHANGE: {
                        if (worldMap.getTile(pos, Layers.DECORATION) != null) return;

                        Item.MapChangeProps props = (Item.MapChangeProps) info.props();
                        worldMap.setCell(pos, Layers.GROUND, props.tile());
                        Events.fire(new EventType.ShowPopupObject(itemStack.item.image, "-1", UIEffects::applyFadeDownEffect));
                        itemStack.addAmount(-1);
                        break;
                    }

                    case PLANT: {
                        if (worldMap.getTile(pos, Layers.DECORATION) != null) return;

                        Item.PlantProps props = (Item.PlantProps) info.props();
                        TiledMapTileLayer.Cell cell = worldMap.setCell(pos, Layers.DECORATION, props.crop());
                        TileHelper.processTile(cell.getTile(), crop -> crop.init(props));
                        Events.fire(new EventType.ShowPopupObject(itemStack.item.image, "-1", UIEffects::applyFadeDownEffect));
                        itemStack.addAmount(-1);
                        break;
                    }
                }
            }
        });
    }

    public void interactLeftDrag(Vector2 pos) {
        float toleranceX = Settings.WORLD_OFFSET_X * Settings.MOVE_MARGIN_X;
        float toleranceY = Settings.WORLD_OFFSET_Y * Settings.MOVE_MARGIN_Y;
        float lowerX = Settings.WORLD_OFFSET_X - toleranceX;
        float upperX = Settings.WORLD_OFFSET_X + toleranceX;
        float lowerY = Settings.WORLD_OFFSET_Y - toleranceY;
        float upperY = Settings.WORLD_OFFSET_Y + toleranceY;
        float deltaX = (pos.x - lastX) / SCALE;
        float deltaY = (pos.y - lastY) / SCALE;

        if (dragging
                && worldCamera.position.x >= lowerX + deltaX && worldCamera.position.x <= upperX + deltaX
                && worldCamera.position.y >= lowerY + deltaY && worldCamera.position.y <= upperY + deltaY) {

            worldCamera.translate(-deltaX * worldCamera.zoom, deltaY * worldCamera.zoom);
            worldCamera.update();
            lastX = pos.x;
            lastY = pos.y;
        }
    }

    public boolean interactScroll(float amountY) {
        if (amountY == 1) {
            if (worldCamera.zoom <= MAX_ZOOM) worldCamera.zoom += ZOOM;
        } else {
            if (worldCamera.zoom >= MIN_ZOOM) worldCamera.zoom -= ZOOM;
        }
        return true;
    }

    @Override
    public void dispose() {
        worldMap.dispose();
        renderer.dispose();
    }

    public static class Layers {
        public static final int GROUND = 0;
        public static final int MODIFY = 1;
        public static final int DECORATION = 2;
        public static final int LIGHTS = 3;
    }
}
