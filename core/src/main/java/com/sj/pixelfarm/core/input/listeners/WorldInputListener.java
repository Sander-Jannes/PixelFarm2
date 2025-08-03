package com.sj.pixelfarm.core.input.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.input.Interactions;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.itemgrid.ItemStackSlot;
import com.sj.pixelfarm.core.utils.TileHelper;
import com.sj.pixelfarm.world.TileSetNames;
import com.sj.pixelfarm.world.World;
import com.sj.pixelfarm.world.WorldUtils;


public class WorldInputListener extends InputAdapter {

    private boolean isLeftMousePressed = false;
    private boolean isRightMousePressed = false;
    private final Vector2 tmpVector = new Vector2();
    private final World world;
    private final Stage stage;
    private boolean isShown = false;

    public WorldInputListener(World world, Stage stage) {
        this.world = world;
        this.stage = stage;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            isLeftMousePressed = true;
            world.lastX = x;
            world.lastY = y;
            world.dragging = true;

            GridPoint2 gridPosition = WorldUtils.getGridPosFromMouse(world.viewport);
            TiledMapTile tile = world.worldMap.getTile(gridPosition, World.Layers.DECORATION);

            TileHelper.processTile(tile, t -> {
                if (t.equals("group", "crops")) {
                    Events.fire(new EventType.ShowCropInfoPopupEvent(tile));
                }
            });

            return true;

        } else if (button == Input.Buttons.RIGHT) {
            isRightMousePressed = true;
            return true;

        }
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        isLeftMousePressed = false;
        isRightMousePressed = false;
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        if (isLeftMousePressed) {
            tmpVector.set(x, y);
            world.interactLeftDrag(tmpVector);
            return true;
        }

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return world.interactScroll(amountY);
    }

    public void update() {
        if (!world.isTouchable()) return;


//        TileHelper.Tile t = TileHelper.getHelperTile(tile);
//        boolean hoverOnCrops = t.equals("group", TileSetNames.CROPS);
//
//        if (hoverOnCrops && !isShown) {
//            isShown = true;
//            Events.fire(new EventType.ShowCropInfoPopupEvent(tile));
//
//        } else if (!hoverOnCrops) {
//            isShown = false;
//            Events.fire(new EventType.HideCropInfoPopupEvent());
//        }

        ItemStackSlot activeSlot = stage.getRoot().findActor(Entities.ACTIVE_SLOT);
        if (isRightMousePressed && activeSlot != null && !activeSlot.isEmpty()) {
            world.dropItem(activeSlot, Interactions.COMBO_HOLD);
        }
    }
}
