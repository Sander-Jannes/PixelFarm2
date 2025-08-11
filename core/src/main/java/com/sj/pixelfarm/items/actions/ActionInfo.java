package com.sj.pixelfarm.items.actions;

import com.badlogic.gdx.utils.Null;

public record ActionInfo(
    Actions action,
    ActionTarget target,
    @Null ActionProps props
) { }
