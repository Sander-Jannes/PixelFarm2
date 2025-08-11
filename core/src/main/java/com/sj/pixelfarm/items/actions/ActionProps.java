package com.sj.pixelfarm.items.actions;

import com.sj.pixelfarm.items.Item;
import com.sj.pixelfarm.world.types.TileType;


public interface ActionProps {

    record Plant(TileType crop, float growTimePerLevel, int waterLevel, int fertilizerLevel, int harvestQuantity, Item item) implements ActionProps { }

    record Use(String target, float amount, TileType animation) implements ActionProps { }
}
