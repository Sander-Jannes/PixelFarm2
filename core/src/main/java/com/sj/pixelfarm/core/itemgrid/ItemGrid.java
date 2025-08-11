package com.sj.pixelfarm.core.itemgrid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.grid.GridBase;
import com.sj.pixelfarm.core.grid.GridLoader;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.input.listeners.GridListener;
import com.sj.pixelfarm.ui.actionbar.ActionBar;
import com.sj.pixelfarm.core.ui.effects.UIEffects;
import com.sj.pixelfarm.core.ui.styles.ButtonStyles;

import java.util.ArrayList;
import java.util.Arrays;


import static com.sj.pixelfarm.core.ui.UIUtils.createActionBar;
import static com.sj.pixelfarm.core.ui.UIUtils.createButton;


public class ItemGrid extends GridBase<ItemStack, ItemStackSlot> {

    private final ItemStackSlot selectedSlot = new ItemStackSlot();
    private ItemStackSlot clickedSlot;
    private final int maxSlotCapacity;
    public ActionBar actionBar;

    private final ArrayList<Integer> noActions = new ArrayList<>();

    public ItemGrid(GridLoader.GridConfig<ItemStack, ItemStackSlot> data, int maxSlotCapacity, String name) {
        super(data);

        this.maxSlotCapacity = maxSlotCapacity;
        noActions.add(2);

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
            if (putSlot(e.source().selectedSlot, localPos) != null) {
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

            ItemStackSlot freeSlot = getFreeSlot(e.stack(), new int[] { selectedSlot.getNumber() });
            if (freeSlot != null) {
                freeSlot.setObj(e.stack());
                e.onSuccess().run();
            }
        });
    }

    public ItemStackSlot getSelectedSlot() {
        return selectedSlot;
    }

    public @Null ItemStackSlot grabSlot(Vector2 localPos) {
        ItemStackSlot clickedSlot = getSlotByPos(localPos);

        if (clickedSlot != null && !clickedSlot.isEmpty()) {
            selectedSlot.set(clickedSlot);
            updateSelectedSlot(localPos);
            getStage().getRoot().addActor(selectedSlot);
            return clickedSlot;
        }
        return null;
    }

    public @Null ItemStackSlot splitSlot(ItemStackSlot slot) {
        if (slot != null && !slot.isEmpty()) {
            ItemStackSlot newSlot = getFreeSlot(slot.getObj());

            if (newSlot != null) {
                newSlot.setObj(slot.split());
                return newSlot;
            }
        }
        return null;
    }

    protected @Null ItemStackSlot putSlot(ItemStackSlot dropSlot, Vector2 localPos) {
        ItemStackSlot targetSlot = getSlotByPos(localPos);
        if (targetSlot == null || dropSlot == null) return null;

        if (targetSlot.equals(dropSlot)) {
            if (targetSlot.isEmpty()) {
                targetSlot.setObj(dropSlot.getObj());
                return targetSlot;

            } else if (targetSlot.canMergeWith(dropSlot.getObj(), maxSlotCapacity)) {
                tryMergeItem(dropSlot.getObj(), targetSlot);
                return targetSlot;
            }
        }

        return null;
    }

    // Het is alleen mogelijk om binnen hetzelfde grid een slot te verwisselen, daarom zit dit niet bij in dropSlot
    protected @Null ItemStackSlot swapSlot(ItemStackSlot dropSlot, Vector2 localPos) {
        ItemStackSlot targetSlot = getSlotByPos(localPos);
        if (targetSlot == null || dropSlot == null) return null;

        if (targetSlot.equals(dropSlot) && dropSlot.equals(targetSlot)) {
            dropSlot.swapObj(targetSlot);
            ItemStackSlot oldSlot = getSlotByNumber(dropSlot.getNumber());
            oldSlot.setObj(dropSlot.getObj());
            return targetSlot;
        }
        return null;
    }

    public boolean dropSelectedSlot(Vector2 localPos) {
        if (putSlot(selectedSlot, localPos) != null) return true;
        return swapSlot(selectedSlot, localPos) != null;
    }

    public void updateSelectedSlot(Vector2 localPos) {
        Vector2 pos = localToStageCoordinates(localPos);
        selectedSlot.setPosition(pos.x, pos.y, Align.center);
    }

    public void removeSelectedSlot() {
        selectedSlot.reset();
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
        ActionBar ab = null;

        if (clickedSlot != null && !clickedSlot.isEmpty()) {
            Vector2 stagePos = localToStageCoordinates(new Vector2(clickedSlot.getX(), clickedSlot.getY()));

            if (clickedSlot.getObj().actionBar != null) {
                ab = clickedSlot.getObj().actionBar;
            } else if (!noActions.contains(clickedSlot.getSlotType())) {
               ab = actionBar;
            }

            if (ab != null) {
                ab.setPosition(stagePos.x + clickedSlot.getWidth() / 2 + ab.getWidth() / 2, stagePos.y + clickedSlot.getHeight() + 10);
                getStage().getRoot().addActor(ab);
                UIEffects.applyBounceEffect(ab);
            }
        }
    }

    private void returnSlotToOrigin(ItemStackSlot slot) {
        if (!slot.isEmpty()) {
            setObjToSlot(slot.getNumber(), slot.getObj());
        }
    }

    private boolean tryMergeItem(ItemStack dropItem, ItemStackSlot targetSlot) {
        int total = targetSlot.getObj().amount + dropItem.amount;
        if (total <= maxSlotCapacity) {
            targetSlot.getObj().addStack(dropItem);
            return true;
        }

        ItemStackSlot freeSlot = getFreeSlot(dropItem);

        if (freeSlot != null) {
            targetSlot.setAmount(maxSlotCapacity);
            dropItem.setAmount(total - maxSlotCapacity);
            freeSlot.setObj(dropItem);
            return true;
        }

        return false;
    }

    @Override
    public @Null ItemStackSlot getFreeSlot(ItemStack stack) {
        for (ItemStackSlot slot : slots.select(s -> s.itemFits(stack))) {
            if (slot.isEmpty()) return slot;
        }
        return null;
    }

    @Override
    public @Null ItemStackSlot getFreeSlot(ItemStack stack, int[] excludeNumbers) {
        for (ItemStackSlot slot : slots.select(
            s -> s.itemFits(stack) &&
                Arrays.stream(excludeNumbers).anyMatch(n -> n != s.getNumber()))) {
            if (slot.isEmpty()) return slot;
        }
        return null;
    }
}
