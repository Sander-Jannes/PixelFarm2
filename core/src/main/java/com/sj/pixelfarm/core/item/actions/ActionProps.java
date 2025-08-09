package com.sj.pixelfarm.core.item.actions;

import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.world.TileType;


public interface ActionProps {

    record MapChange(TileType tile) implements ActionProps { }

    record Plant(TileType crop, float growTimePerLevel, float timeTillRotten, int waterLevel, int fertilizerLevel, int harvestQuantity, Item item) implements ActionProps { }

    record Sell(int money, int xp) implements ActionProps { }

    record Use(float amount) implements ActionProps { }
}
