package com.sj.pixelfarm.core.input.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.sj.pixelfarm.core.card.Card;


public class CardInputListener extends DragListener {

    private final Card card;

    public CardInputListener(Card card) {
        this.card = card;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (event.isHandled()) return false;

        if (button == Input.Buttons.LEFT) {
            return super.touchDown(event, x, y, pointer, button);
        }

        return false;
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
        if (isDragging()) {
            card.moveBy(x - getDragStartX(), y - getDragStartY());
        }
    }
}
