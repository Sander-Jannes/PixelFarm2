package com.sj.pixelfarm.core.itemgrid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Align;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.grid.GridBase;
import com.sj.pixelfarm.core.grid.GridLoader;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.input.listeners.GridListener;
import com.sj.pixelfarm.core.ui.actionbar.ActionBar;
import com.sj.pixelfarm.core.ui.effects.UIEffects;
import com.sj.pixelfarm.core.ui.styles.ButtonStyles;

import static com.sj.pixelfarm.core.ui.UIUtils.createActionBar;
import static com.sj.pixelfarm.core.ui.UIUtils.createButton;


public class ItemGrid extends GridBase<ItemStack, ItemStackSlot> {

    private final ItemStackSlot selectedSlot = new ItemStackSlot();
    private ItemStackSlot clickedSlot;
    private final int maxSlotCapacity;
    public ActionBar actionBar;

    public ItemGrid(GridLoader.GridConfig<ItemStack, ItemStackSlot> data, int maxSlotCapacity, String name) {
        super(data);

        this.maxSlotCapacity = maxSlotCapacity;

        setName(name);
        selectedSlot.setName(Entities.ACTIVE_SLOT);
        addListener(new GridListener(this));

        actionBar = createActionBar(bar -> {
            Button split = createButton(ButtonStyles.SPLIT_ITEM, button -> splitSlot(clickedSlot));
            Button remove = createButton(ButtonStyles.REMOVE_ITEM, button -> clickedSlot.destroyObj(true));
            bar.space(5);
            bar.addState(1, split, remove);
            bar.setState(1);
        });

        Events.on(EventType.TransferSlotEvent.class, e -> {
            if (e.source().equals(this) || e.source().selectedSlot.isEmpty() || !isVisible() || !isTouchable()) return;

            // Belangrijk: maak kopie, bij meerdere grids wordt 'e.pos()' steeds veranderd en gebruikt voor verdere berekeningen
            Vector2 localPos = stageToLocalCoordinates(e.pos().cpy());
            if (dropSlot(e.source().selectedSlot, localPos)) {
                e.source().removeSelectedSlot();
            }
        });

        Events.on(EventType.PutItemInGridEvent.class, e -> {
            if (!getName().equals(e.name())) return;

            for (ItemStackSlot slot : getSlots()) {
                if (slot.canMergeWith(e.stack(), maxSlotCapacity) && tryMergeItem(e.stack(), slot)) {
                    e.onSuccess().run();
                    return;
                }
            }

            ItemStackSlot freeSlot = getFreeSlot(e.stack().getSlotType(), new int[] { selectedSlot.getNumber() });
            if (freeSlot != null) {
                freeSlot.setObj(e.stack());
                e.onSuccess().run();
            }
        });
    }

    public ItemStackSlot getSelectedSlot() {
        return selectedSlot;
    }

    public void grabItem(Vector2 localPos) {
        ItemStackSlot clickedSlot = getSlotByPos(localPos);

        if (clickedSlot != null && !clickedSlot.isEmpty()) {
            selectedSlot.set(clickedSlot);
            updateSelectedSlot(localPos);
            getStage().getRoot().addActor(selectedSlot);
        }
    }

    public void splitSlot(ItemStackSlot slot) {
        if (slot != null && !slot.isEmpty()) {
            ItemStackSlot newSlot = getFreeSlot(slot.getSlotType());

            if (newSlot != null) {
                newSlot.setObj(slot.split());
            }
        }
    }

    public boolean dropSelectedSlot(Vector2 localPos) {
        return dropSlot(selectedSlot, localPos);
    }

    public void updateSelectedSlot(Vector2 localPos) {
        Vector2 pos = localToStageCoordinates(localPos);
        selectedSlot.setPosition(pos.x, pos.y, Align.center);
    }

    public void removeSelectedSlot() {
        selectedSlot.detachObj();
        selectedSlot.remove();
    }

    public boolean selectedSlotHasItem() {
        return !selectedSlot.isEmpty();
    }

    public void returnSelectedSlotToOrigin() {
        returnSlotToOrigin(selectedSlot);
    }

    public void showActionBar(Vector2 localPos) {
        clickedSlot = getSlotByPos(localPos);

        if (clickedSlot != null && !clickedSlot.isEmpty()) {
            Vector2 stagePos = localToStageCoordinates(new Vector2(clickedSlot.getX(), clickedSlot.getY()));
            actionBar.setPosition(stagePos.x + clickedSlot.getWidth() / 2 + actionBar.getWidth() / 2, stagePos.y + clickedSlot.getHeight() + 10);
            getStage().getRoot().addActor(actionBar);
            UIEffects.applyBounceEffect(actionBar);
        }
    }

    private void returnSlotToOrigin(ItemStackSlot slot) {
        if (!slot.isEmpty()) {
            setObjToSlot(slot.getNumber(), slot.getObj());
        }
    }

    private boolean dropSlot(ItemStackSlot dropSlot, Vector2 localPos) {
        ItemStackSlot targetSlot = getSlotByPos(localPos);

        if (targetSlot == null || dropSlot == null) return false;

        if (targetSlot.equals(dropSlot)) {
            if (targetSlot.isEmpty()) {
                targetSlot.setObj(dropSlot.getObj());

            } else if (targetSlot.canMergeWith(dropSlot.getObj(), maxSlotCapacity)) {
                tryMergeItem(dropSlot.getObj(), targetSlot);

            } else {
                dropSlot.swapObj(targetSlot);
                ItemStackSlot oldSlot = getSlotByNumber(dropSlot.getNumber());
                oldSlot.setObj(dropSlot.getObj());
            }
            return true;
        }

        return false;
    }

    private boolean tryMergeItem(ItemStack dropItem, ItemStackSlot targetSlot) {
        int total = targetSlot.getObj().amount + dropItem.amount;
        if (total <= maxSlotCapacity) {
            targetSlot.getObj().addStack(dropItem);
            return true;
        }

        ItemStackSlot freeSlot = getFreeSlot(dropItem.getSlotType());

        if (freeSlot != null) {
            targetSlot.setAmount(maxSlotCapacity);
            dropItem.setAmount(total - maxSlotCapacity);
            freeSlot.setObj(dropItem);
            return true;
        }

        return false;
    }
}
