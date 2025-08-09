package com.sj.pixelfarm.ui.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.ui.styles.ButtonStyles;

import static com.sj.pixelfarm.core.ui.UIUtils.*;


public class OverlayMenu extends Group {

    private final Hud hud = new Hud();

    public OverlayMenu() {
        setName(Entities.OVERLAY_MENU);
        setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        setTouchable(Touchable.childrenOnly);

        Table parent = new Table();
        parent.setFillParent(true);
        parent.row().expand();

        Table body = new Table();
        body.row();

        HorizontalGroup row = new HorizontalGroup().space(15);
        VerticalGroup buttonParent = new VerticalGroup().space(10);

        Button inventoryButton = createButton(ButtonStyles.INVENTORY_BUTTON, button -> {
            Events.fire(new EventType.ToggleEditMode());
        });

        Button shopButton = createButton(ButtonStyles.INVENTORY_BUTTON, button -> {
            Events.fire(new EventType.ShowModalEvent(Entities.SHOP));
        });

        buttonParent.addActor(inventoryButton);
        buttonParent.addActor(shopButton);
        row.addActor(buttonParent);

        row.addActor(hud);

        body.add(row);
        body.row().space(10);

        parent.add(body).top().right().padTop(10).padRight(10);
        addActor(parent);

        Events.on(EventType.UpdateOverlayEvent.class, e -> update());
    }

    public void update() {
        hud.update();
    }
}
