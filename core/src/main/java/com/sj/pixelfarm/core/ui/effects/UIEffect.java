package com.sj.pixelfarm.core.ui.effects;

import com.badlogic.gdx.scenes.scene2d.Actor;

@FunctionalInterface
public interface UIEffect {
    void apply(Actor actor);
}
