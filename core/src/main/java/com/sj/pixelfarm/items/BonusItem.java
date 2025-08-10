package com.sj.pixelfarm.items;

import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.item.ItemType;

public class BonusItem extends Item {

    public float multiplier;

    public BonusItem(ItemType itemType, int slotType, int unlockLevel) {
        super(itemType, slotType, unlockLevel);
    }
}
