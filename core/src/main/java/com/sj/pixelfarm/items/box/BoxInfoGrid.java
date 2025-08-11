package com.sj.pixelfarm.items.box;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Null;
import com.sj.pixelfarm.core.grid.GridLoader;
import com.sj.pixelfarm.core.itemgrid.ItemGrid;
import com.sj.pixelfarm.core.itemgrid.ItemStack;
import com.sj.pixelfarm.core.itemgrid.ItemStackSlot;
import com.sj.pixelfarm.items.Items;


public class BoxInfoGrid extends ItemGrid {

    private final ItemStackSlot bonus;
    private final BoxInfo boxInfo;

    public BoxInfoGrid(GridLoader.GridConfig<ItemStack, ItemStackSlot> data, int maxSlotCapacity, BoxInfo boxInfo) {
        super(data, maxSlotCapacity, "");
        this.boxInfo = boxInfo;

        bonus = getSlotByNumber(1);
        bonus.setFixed(Items.flowers, Items.test);
    }

    @Override
    public @Null ItemStackSlot grabSlot(Vector2 localPos) {
        ItemStackSlot clickedSlot = super.grabSlot(localPos);

        if (isBonusSlot(clickedSlot)) {
            boxInfo.removeBonus();
        }
        return clickedSlot;
    }

    @Override
    protected @Null ItemStackSlot putSlot(ItemStackSlot dropSlot, Vector2 localPos) {
        ItemStackSlot targetSlot = super.putSlot(dropSlot, localPos);

        if (isBonusSlot(targetSlot)) {
            boxInfo.setBonus(bonus.getObj());
        }
        return targetSlot;
    }

    @Override
    public @Null ItemStackSlot splitSlot(ItemStackSlot slot) {
        ItemStackSlot newSlot = super.splitSlot(slot);

        if (isBonusSlot(slot, newSlot)) {
            boxInfo.setBonus(bonus.getObj());
        }
        return newSlot;
    }

    @Override
    protected @Null ItemStackSlot swapSlot(ItemStackSlot dropSlot, Vector2 localPos) {
        ItemStackSlot targetSlot = super.swapSlot(dropSlot, localPos);

        if (isBonusSlot(targetSlot, dropSlot)) {
            boxInfo.setBonus(bonus.getObj());
        }
        return targetSlot;
    }

    private boolean isBonusSlot(ItemStackSlot... slots) {
        for (ItemStackSlot slot : slots) {
            if (slot != null && slot.getNumber() == 1) {
                return true;
            }
        }
        return false;
    }
}
