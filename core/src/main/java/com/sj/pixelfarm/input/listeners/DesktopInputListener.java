package com.sj.pixelfarm.input.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.sj.pixelfarm.input.events.EventType;
import com.sj.pixelfarm.core.Events;


public class DesktopInputListener extends InputAdapter {

    @Override
    public boolean keyDown (int keycode) {
        if (keycode == Input.Keys.ESCAPE) Gdx.app.exit();
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Remove the actions
        Events.fire(new EventType.RemoveActionBar(new Vector2(screenX, screenY)));

        return false;
    }
}
