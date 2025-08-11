package com.sj.pixelfarm.input.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sj.pixelfarm.Entities;
import com.sj.pixelfarm.input.Interactions;
import com.sj.pixelfarm.input.events.EventType;
import com.sj.pixelfarm.core.Events;
import com.sj.pixelfarm.itemgrid.ItemStackSlot;
import com.sj.pixelfarm.world.World;
import com.sj.pixelfarm.world.utils.WorldUtils;


public class WorldInputListener extends InputAdapter {

    private boolean isLeftMousePressed = false;
    private boolean isRightMousePressed = false;
    private final Vector2 tmpVector = new Vector2();
    private final World world;
    private final Stage stage;

    private GridPoint2 hitPosition = new GridPoint2(0, 0);

    public WorldInputListener(World world, Stage stage) {
        this.world = world;
        this.stage = stage;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        tmpVector.set(x, y);

        if (button == Input.Buttons.LEFT) {
            isLeftMousePressed = true;
            world.lastX = x;
            world.lastY = y;
            world.dragging = true;
            world.editMode.showActionBar(tmpVector);
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
        ItemStackSlot activeSlot = stage.getRoot().findActor(Entities.ACTIVE_SLOT);
        if (isRightMousePressed && activeSlot != null && !activeSlot.isEmpty()) {
            world.dropItem(activeSlot, Interactions.COMBO_HOLD);
        }

        GridPoint2 gridPosition = WorldUtils.getGridPosFromMouse(world.viewport);
        TiledMapTile tile = world.worldMap.getTile(gridPosition, World.Layers.DECORATION);

        if (!hitPosition.equals(gridPosition) && tile != null) {
            if (tile.getProperties().get("group").equals("crops")) {
                Events.fire(new EventType.ShowCropInfoPopupEvent(tile));
                hitPosition = gridPosition.cpy();

            } else if (hitPosition.x != 0){
                hitPosition.set(0, 0);
                Events.fire(new EventType.HideCropInfoPopupEvent());
            }
        }
    }
}
