package com.sj.pixelfarm.core.item.actions;

import com.sj.pixelfarm.world.World;

public enum ActionTarget {
    HOME("name", "home", World.Layers.DECORATION),
    FIELDS("group", "fields", World.Layers.GROUND),
    CROPS("group", "crops", World.Layers.DECORATION),
    TILES("group", "tiles", World.Layers.GROUND);

    public final String property;
    public final String name;
    public final int layer;

    ActionTarget(String property, String name, int layer) {
        this.property = property;
        this.name = name;
        this.layer = layer;
    }
}
