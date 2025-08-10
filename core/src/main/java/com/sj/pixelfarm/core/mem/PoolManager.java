package com.sj.pixelfarm.core.mem;

import com.badlogic.gdx.utils.Pools;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.itemgrid.ItemStack;
import com.sj.pixelfarm.items.box.Box;


public final class PoolManager {

    @SuppressWarnings("unchecked")
    public static <T extends ItemStack> T obtain(Item item, int amount, Item.Quality quality) {
        return (T) obtainStack(item, amount, quality);
    }

    public static Box obtainBox() {
        Box box = Pools.obtain(Box.class);
        box.init();
        return box;
    }

    private static ItemStack obtainStack(Item item, int amount, Item.Quality quality) {
        ItemStack itemStack = Pools.obtain(ItemStack.class);
        itemStack.init(item, amount, quality);
        return itemStack;
    }
}
