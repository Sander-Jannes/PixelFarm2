package com.sj.pixelfarm.core.card;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.sj.pixelfarm.mem.Assets;


/**
 * Represents a card with graphical and interactive properties.
 * A Card is a visual element that can be added to the scene graph. It includes an selectImage,
 * supports user interactions, and may contain additional layers through a stack.
 */
public abstract class Card extends Group {

    protected final Stack stack = new Stack();

    public Card(String imagePath, float x, float y, boolean isMovable) {
        super();

        TextureRegion region = Assets.getAtlasTexture(imagePath);
        Image image = new Image(region);
        stack.setFillParent(true);
        setBounds(x, y, region.getRegionWidth(), region.getRegionHeight());

        stack.addActor(image);
        addActor(stack);

        if (isMovable) {
            addListener(new CardInputListener(this));
        }
    }

    public void moveCenterTo(float x, float y) {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    public void toggleVisibility() {
        setVisible(!isVisible());
    }
}
