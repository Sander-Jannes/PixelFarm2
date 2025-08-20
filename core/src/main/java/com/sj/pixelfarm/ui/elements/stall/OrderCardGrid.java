package com.sj.pixelfarm.ui.elements.stall;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Null;
import com.sj.pixelfarm.core.grid.GridLoader;
import com.sj.pixelfarm.itemgrid.ItemGrid;
import com.sj.pixelfarm.itemgrid.ItemStack;
import com.sj.pixelfarm.itemgrid.ItemStackSlot;
import com.sj.pixelfarm.items.Item;
import com.sj.pixelfarm.items.Items;
import com.sj.pixelfarm.items.box.Box;
import com.sj.pixelfarm.mem.PoolManager;

import java.util.ArrayList;


public class OrderCardGrid extends ItemGrid {

    private ArrayList<ItemStack> items = new ArrayList<>();

    public OrderCardGrid(GridLoader.GridConfig<ItemStack, ItemStackSlot> data, int maxSlotCapacity, String name) {
        super(data, maxSlotCapacity, name);
        noActions.add(3);
    }

    @Override
    protected @Null ItemStackSlot putSlot(ItemStackSlot dropSlot, Vector2 localPos) {
        ItemStackSlot targetSlot = getSlotByPos(localPos);
        if (targetSlot == null || dropSlot == null) return null;

        if (dropSlot.getObj().getName().equals("Box")) {
            Box box = (Box) dropSlot.getObj();
            items = box.getItems();
            targetSlot.setObj(PoolManager.obtain(Items.closed_box, 0, Item.Quality.NONE));
            box.reset();
        }

        return null;
    }
}
