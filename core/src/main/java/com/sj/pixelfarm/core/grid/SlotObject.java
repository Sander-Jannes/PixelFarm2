package com.sj.pixelfarm.core.grid;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;

public abstract class SlotObject extends Container<Actor> {

    public abstract int getSlotType();
}
