package com.sj.pixelfarm.ui.elements.hotbar;

import com.sj.pixelfarm.core.grid.GridLoader;
import com.sj.pixelfarm.itemgrid.ItemGrid;
import com.sj.pixelfarm.itemgrid.ItemStack;
import com.sj.pixelfarm.itemgrid.ItemStackSlot;

public class HotBar extends ItemGrid {

    public HotBar(GridLoader.GridConfig<ItemStack, ItemStackSlot> data, int maxSlotCapacity, String name) {
        super(data, maxSlotCapacity, name);
        setNoActions(2);
    }
}
