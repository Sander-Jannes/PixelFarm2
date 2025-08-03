package com.sj.pixelfarm.core.item.actions;

import com.badlogic.gdx.utils.Null;

public record ActionInfo(
    Actions action,
    ActionTarget target,
    @Null ActionProps props
) { }
