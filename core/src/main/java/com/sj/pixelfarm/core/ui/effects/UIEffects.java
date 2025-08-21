package com.sj.pixelfarm.core.ui.effects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public final class UIEffects {

    public static void applyBounceEffect(Actor actor) {
        actor.addAction(Actions.sequence(
                Actions.moveTo(actor.getX(), actor.getY() + 20, 0.1f, Interpolation.smooth),
                Actions.moveBy(0, -5, 0.08f, Interpolation.smooth),
                Actions.moveBy(0, 3, 0.06f, Interpolation.smooth),
                Actions.moveBy(0, -2, 0.05f, Interpolation.smooth)
        ));
    }

    public static void applyFadeOutEffect(Actor actor) {
        actor.addAction(Actions.sequence(
                Actions.moveBy(0, 20,0.5f, Interpolation.smooth),
                Actions.removeActor()
        ));
    }

    public static void applyFadeDownEffect(Actor actor) {
        actor.addAction(Actions.sequence(
                Actions.moveBy(0, -15, 0.25f, Interpolation.linear),
                Actions.removeActor()
        ));
    }

    public static void applyErrorMessageEffect(Actor actor) {
        actor.addAction(Actions.sequence(
                Actions.moveBy(0, 25, 0.75f, Interpolation.linear),
                Actions.removeActor()
        ));
    }

    public static void applySpawnInEffect(Actor actor) {
        actor.addAction(sequence(
                scaleTo(1.02f, 1.02f, 0.04f),
                scaleTo(1.0f, 1.0f, 0.04f)
        ));
    }

    public static void applySinusBounce(Actor actor, float amplitude, float duration) {
        actor.addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.moveBy(0, amplitude, duration / 2f, Interpolation.sine),
                    Actions.moveBy(0, -amplitude, duration / 2f, Interpolation.sine)
                )
            )
        );
    }
}
