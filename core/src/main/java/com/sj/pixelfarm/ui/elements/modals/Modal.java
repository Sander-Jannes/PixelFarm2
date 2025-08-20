package com.sj.pixelfarm.ui.elements.modals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.sj.pixelfarm.core.card.Card;
import com.sj.pixelfarm.core.ui.effects.UIEffects;
import com.sj.pixelfarm.input.events.EventType;
import com.sj.pixelfarm.core.Events;

import static com.sj.pixelfarm.core.ui.UIUtils.createTableWithRemoveButton;


/** Parent for all the models with a baked-in remove button */
public abstract class Modal extends Card {

    protected final Table modalTable;

    public Modal(String image, String name) {
        super(image, 0, 0, false);
        setOrigin(Align.center);
        setVisible(false);
        setName(name);
        moveCenterTo(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        modalTable = createTableWithRemoveButton(button -> this.onClose());
        cardStack.add(modalTable);

        Events.on(EventType.ShowModalEvent.class, e -> {
            if (getName().equals(e.name())) setVisible(true);
            UIEffects.applySpawnInEffect(this);
        });
    }

    protected void onClose() {
        Events.fire(new EventType.HideCurrentModalEvent());
        setVisible(false);
    }
}
