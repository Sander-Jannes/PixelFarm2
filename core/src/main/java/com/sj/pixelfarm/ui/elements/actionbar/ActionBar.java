package com.sj.pixelfarm.ui.elements.actionbar;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.utils.ObjectMap;
import com.sj.pixelfarm.Entities;
import com.sj.pixelfarm.input.events.EventType;
import com.sj.pixelfarm.core.Events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ActionBar extends HorizontalGroup {

    private final ObjectMap<Integer, ArrayList<Actor>> state = new ObjectMap<>();

    public ActionBar() {
        super();
        setName(Entities.ACTIONBAR);
        center();

        Events.on(EventType.RemoveActionBar.class, e -> {
            Vector2 mouse = screenToLocalCoordinates(e.pos());
            if (hit(mouse.x, mouse.y, true) == null) {
                remove();
            }
        });
    }

    public void addState(int s) {
        state.put(s, new ArrayList<>());
    }

    public void addState(int s, Actor... buttons) {
        state.put(s, new ArrayList<>(Arrays.asList(buttons)));
    }

    public void addToState(int s, Actor... buttons) {
        List<Actor> buttonList = state.get(s);
        if (buttonList != null) buttonList.addAll(Arrays.asList(buttons));
    }

    public void setState(int s) {
        clearChildren();
        List<Actor> buttons = state.get(s);
        if (buttons != null) {
            buttons.forEach(this::addActor);
        }
        layout();
    }

    public <T> T getElement(int s, int index, Class<T> clazz) {
        return clazz.cast(state.get(s).get(index));
    }
}
