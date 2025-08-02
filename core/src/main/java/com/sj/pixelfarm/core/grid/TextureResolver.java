package com.sj.pixelfarm.core.grid;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

@FunctionalInterface
public interface TextureResolver {
    TextureRegion get(String path);
}
