package com.sj.pixelfarm.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.ui.effects.UIEffects;


public class UIEventProcessor {

    private static final Vector2 tmpVec = new Vector2();

    public static void init(Stage stage) {

        Events.on(EventType.ShowPopupObject.class, e -> {
            tmpVec.set(Gdx.input.getX(), Gdx.input.getY() - 20);
            stage.screenToStageCoordinates(tmpVec);
            UIUtils.createPopupObject(stage, tmpVec, e.image(), e.text(), e.effectApplier());
        });

        Events.on(EventType.ShowActionBarEvent.class, e -> {
            e.bar().setPosition(Gdx.graphics.getWidth() / 2f, 160);
            stage.addActor(e.bar());
            UIEffects.applyBounceEffect(e.bar());
        });
    }
}
