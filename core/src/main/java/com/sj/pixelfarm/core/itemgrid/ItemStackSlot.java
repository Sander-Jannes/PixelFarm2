package com.sj.pixelfarm.core.itemgrid;

import com.sj.pixelfarm.core.grid.Slot;
import com.sj.pixelfarm.core.item.Item;

import java.util.ArrayList;


public class ItemStackSlot extends Slot<ItemStack> {

    public ArrayList<Item> allowedItems = new ArrayList<>();
    public boolean fixed = false;

    public ItemStackSlot() {
        super();
    }

    public ItemStackSlot(int number, int slotType, float rx, float ry, float width, float height) {
        super(number, slotType, rx, ry, width, height);
    }

    public ItemStack split() {
        return obj.split();
    }

    public void setAmount(int amount) {
        obj.setAmount(amount);
    }

    protected boolean canMergeWith(ItemStack other, int maxSlotCapacity) {
        return !isEmpty() && getObj().amount != maxSlotCapacity && obj.equals(other);
    }

    public boolean itemFits(Item item) {
        return (!fixed || allowedItems.contains(item)) && item.slotType == getSlotType();
    }

    public void set(ItemStackSlot slot) {
       super.set(slot);
        fixed = slot.fixed;
        allowedItems = slot.allowedItems;
    }
}
