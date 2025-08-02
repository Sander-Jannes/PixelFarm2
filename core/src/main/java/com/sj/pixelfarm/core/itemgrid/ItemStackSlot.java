package com.sj.pixelfarm.core.itemgrid;

import com.sj.pixelfarm.core.grid.Slot;


public class ItemStackSlot extends Slot<ItemStack> {

    public ItemStackSlot() {
        super();
    }

    public ItemStackSlot(int number, int slotType, float rx, float ry, float width, float height) {
        super(number, slotType, rx, ry, width, height);
    }

    public boolean canMergeWith(ItemStack other, int maxSlotCapacity) {
        return !isEmpty() && getObj().amount != maxSlotCapacity && obj.equals(other);
    }

    public ItemStack split() {
        return obj.split();
    }

    public void setAmount(int amount) {
        obj.setAmount(amount);
    }
}
