package com.sj.pixelfarm.core.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;
import com.sj.pixelfarm.core.input.Interactions;
import com.sj.pixelfarm.core.item.actions.ActionInfo;
import com.sj.pixelfarm.core.mem.Assets;

import java.util.ArrayList;


public class Item {

    public final TextureRegion image;

    public final ItemType itemType;
    public final int slotType;

    public boolean isStackable = true;
    public String description = "";
    public int unlockLevel;
    public int price = 0;

    public final ObjectMap<Interactions, ActionInfo> interactionMap = new ObjectMap<>();
    public final ArrayList<Group> groups = new ArrayList<>();

    public Item(ItemType itemType, int slotType, int unlockLevel) {
        this.itemType = itemType;
        this.slotType = slotType;
        this.unlockLevel = unlockLevel;
        this.image = Assets.getItemImage(itemType.getIndex());
    }

    public boolean equals(Item other) {
        return itemType.equals(other.itemType);
    }

    public enum Quality {
        BAD,
        MODERATE,
        GOOD,
        EXCELLENT,
        NONE
    }

    public enum Group {
        SEEDS,
        CARE,
    }
}
