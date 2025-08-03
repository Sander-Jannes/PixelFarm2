package com.sj.pixelfarm.core.card;


import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Null;


public abstract class TimerPopup<T> extends Card {

    protected @Null T obj;
    private boolean isShown = false;
    private final long timer;

    public TimerPopup(String image, float x, float y, boolean isMovable, long timer) {
        super(image, x, y, isMovable);
        this.timer = timer;
        setVisible(false);
    }

    protected void show(T obj) {
        this.obj = obj;
        if (isShown) return;

        addAction(Actions.delay(timer / 1000f, Actions.run(() -> setVisible(true))));
        isShown = true;
    }

    protected void hide() {
        this.obj = null;
        isShown = false;
        setVisible(false);
    }
}
