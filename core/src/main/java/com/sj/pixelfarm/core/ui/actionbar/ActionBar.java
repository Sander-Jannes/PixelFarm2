package com.sj.pixelfarm.core.ui.actionbar;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.utils.ObjectMap;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A UI component representing an action bar with different button states.
 */
public class ActionBar extends HorizontalGroup {

    private final ObjectMap<Integer, ArrayList<Group>> state = new ObjectMap<>();

    public ActionBar() {
        super();
        setName(Entities.ACTIONBAR);
        center();

        Events.on(EventType.RemoveActionBar.class, e -> {
            Vector2 mouse = screenToLocalCoordinates(e.pos());
            if (hit(mouse.x, mouse.y, true) == null) {
                remove();
                Events.fire(new EventType.HideCurrentModalEvent());
            }
        });
    }

    public void addState(int s) {
        state.put(s, new ArrayList<>());
    }

    public void addState(int s, Group... buttons) {
        state.put(s, new ArrayList<>(Arrays.asList(buttons)));
    }

    public void addToState(int s, Group... buttons) {
        List<Group> buttonList = state.get(s);
        if (buttonList != null) buttonList.addAll(Arrays.asList(buttons));
    }

    public void setState(int s) {
        clearChildren();
        List<Group> buttons = state.get(s);
        if (buttons != null) {
            buttons.forEach(this::addActor);
        }
        layout();
    }
}
