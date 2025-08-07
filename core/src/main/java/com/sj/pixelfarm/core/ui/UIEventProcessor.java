package com.sj.pixelfarm.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sj.pixelfarm.core.Entities;
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
            e.bar().setPosition(e.pos().x, e.pos().y);
            stage.addActor(e.bar());
            UIEffects.applyBounceEffect(e.bar());
        });

        Events.on(EventType.ShowErrorMessage.class, e -> {
            // Om ervoor te zorgen dat de error message niet gespamd wordt
            if (stage.getRoot().findActor(Entities.ERROR_MESSAGE) != null) return;

            tmpVec.set(Gdx.input.getX(), Gdx.input.getY() - 20);
            stage.screenToStageCoordinates(tmpVec);
            UIUtils.createErrorMessage(stage, tmpVec, e.text());
        });
    }
}
