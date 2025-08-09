package com.sj.pixelfarm.ui.modals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.sj.pixelfarm.core.card.Card;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;

import static com.sj.pixelfarm.core.ui.UIUtils.createTableWithRemoveButton;


/** Parent for all the models with a baked-in remove button */
public abstract class Modal extends Card {

    protected final Table table;

    public Modal(String image) {
        super(image, 0, 0, false);
        setOrigin(Align.center);
        moveCenterTo(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        table = createTableWithRemoveButton(button -> this.onClose());
        stack.add(table);
    }

    protected void onClose() {
        Events.fire(new EventType.HideCurrentModalEvent());
        setVisible(false);
    }
}
