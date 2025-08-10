package com.sj.pixelfarm.core.itemgrid;

import com.sj.pixelfarm.core.item.Item;

import java.util.ArrayList;


public class FixedItemStackSlot extends ItemStackSlot {

    public final ArrayList<Item> allowedItems = new ArrayList<>();

    public FixedItemStackSlot() {
        super();
    }

    public FixedItemStackSlot(int number, int slotType, float rx, float ry, float width, float height) {
        super(number, slotType, rx, ry, width, height);
    }

    @Override
    public void setObj(ItemStack obj) {
        if (allowedItems.contains(obj.item)) {
            super.setObj(obj);
        }
    }
}
