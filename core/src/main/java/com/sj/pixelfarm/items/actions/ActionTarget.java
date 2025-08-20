package com.sj.pixelfarm.items.actions;

import com.sj.pixelfarm.world.World;

public enum ActionTarget {
    HOME("name", "home", World.Layers.DECORATION),
    FIELDS("group", "fields", World.Layers.GROUND),
    CROPS("group", "crops", World.Layers.DECORATION),
    GRASS("group", "grass", World.Layers.GROUND),
    CAR("group", "roads", World.Layers.GROUND),
    STALL("name", "stall", World.Layers.DECORATION);

    public final String property;
    public final String name;
    public final int layer;

    ActionTarget(String property, String name, int layer) {
        this.property = property;
        this.name = name;
        this.layer = layer;
    }
}
