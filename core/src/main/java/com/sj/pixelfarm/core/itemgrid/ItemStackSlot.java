package com.sj.pixelfarm.core.itemgrid;

import com.sj.pixelfarm.core.grid.Slot;
import com.sj.pixelfarm.core.item.Item;

import java.util.ArrayList;
import java.util.List;


public class ItemStackSlot extends Slot<ItemStack> {

    private ArrayList<Item> allowedItems = new ArrayList<>();
    private boolean fixed = false;

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

    public boolean itemFits(ItemStack stack) {
        return (!fixed || allowedItems.contains(stack.item)) && stack.item.slotType == getSlotType();
    }

    public boolean equals(ItemStackSlot slot) {
        return !slot.isEmpty() && itemFits(slot.getObj());
    }

    public void setFixed(Item... items) {
        fixed = true;
        allowedItems.addAll(List.of(items));
    }

    public void set(ItemStackSlot slot) {
        super.set(slot);
        fixed = slot.fixed;
        allowedItems = slot.allowedItems;
    }
}
