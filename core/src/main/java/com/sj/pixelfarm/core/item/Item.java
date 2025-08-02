package com.sj.pixelfarm.core.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectMap;
import com.sj.pixelfarm.core.input.Actions;
import com.sj.pixelfarm.core.input.Interactions;
import com.sj.pixelfarm.core.mem.Assets;
import com.sj.pixelfarm.world.TileType;


public class Item {

    public TextureRegion image;

    public ItemType itemType;
    public int slotType;

    public boolean isStackable = true;
    public String description = "";

    public ObjectMap<Interactions, ActionInfo> interactionMap = new ObjectMap<>();

    public Item(ItemType itemType, int slotType) {
        this.itemType = itemType;
        this.slotType = slotType;
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
        NONE;
    }

    public interface ActionProps { }

    public record MapChangeProps(TileType tile) implements ActionProps { }
    public record PlantProps(TileType crop, float growTimePerLevel, float timeTillRotten, int waterLevel, int fertilizerLevel, int harvestQuantity, Item item) implements ActionProps { }

    public record ActionInfo(Actions action, String targetProperty, String targetName, int layer, @Null ActionProps props) { }
}
