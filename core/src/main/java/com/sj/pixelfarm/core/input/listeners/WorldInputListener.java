package com.sj.pixelfarm.core.input.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.input.Interactions;
import com.sj.pixelfarm.core.itemgrid.ItemStackSlot;
import com.sj.pixelfarm.world.World;


public class WorldInputListener extends InputAdapter {

    private boolean isLeftMousePressed = false;
    private boolean isRightMousePressed = false;
    private final Vector2 tmpVector = new Vector2();
    private final World world;
    private final Stage stage;

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
    }
}
