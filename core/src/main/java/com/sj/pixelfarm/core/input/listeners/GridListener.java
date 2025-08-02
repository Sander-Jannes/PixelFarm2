package com.sj.pixelfarm.core.input.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.sj.pixelfarm.core.input.Interactions;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.itemgrid.ItemGrid;


public class GridListener extends InputListener {

    private final ItemGrid grid;
    private final Vector2 tmpVec2 = new Vector2();
    private boolean isLeftMousePressed = false;
    private boolean isRightMousePressed = false;

    public GridListener(ItemGrid grid) {
        this.grid = grid;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        tmpVec2.set(x, y);

        if (button == Input.Buttons.LEFT) {
            isLeftMousePressed = true;
            grid.grabItem(tmpVec2);
            return true;
        }

        if (button == Input.Buttons.RIGHT) {
            isRightMousePressed = true;
            return true;
        }
        return false;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        tmpVec2.set(x, y);

        if (isLeftMousePressed) {
            isLeftMousePressed = false;

            if (!grid.dropSelectedSlot(tmpVec2)) {
                Vector2 stagePos = grid.localToStageCoordinates(tmpVec2);

                // Probeer het item naar een ander grid te verplaatsen
                Events.fire(new EventType.TransferSlotEvent(grid, stagePos));

                // Het is niet gelukt om het item te droppen in een grid, fire het naar de world
                Events.fire(new EventType.DropItemOnWorld(grid.getSelectedSlot(), Interactions.LEFT_RELEASE));

                // Niets heeft het slot ontvangen, dus zet het terug
                if (grid.selectedSlotHasItem()) {
                    grid.returnSelectedSlotToOrigin();
                }
            }

            grid.removeSelectedSlot();

        } else if (isRightMousePressed) {
            isRightMousePressed = false;
            grid.showActionBar(tmpVec2);
        }
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        tmpVec2.set(x, y);

        if (isLeftMousePressed) {
            grid.updateSelectedSlot(tmpVec2);
            Events.fire(new EventType.DropItemOnWorld(grid.getSelectedSlot(), Interactions.LEFT_HOLD));
        }
    }
}
