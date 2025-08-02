package com.sj.pixelfarm.core.input.events;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class Events {

    private static final Map<Class<?>, List<Consumer<?>>> listeners = new HashMap<>();

    public static <T> void on(Class<T> type, Consumer<T> consumer) {
        listeners
                .computeIfAbsent(type, k -> new ArrayList<>())
                .add(consumer);
    }

    public static void fire(Object event) {
        List<Consumer<?>> list = listeners.get(event.getClass());

        if (list != null) {
            for (Consumer<?> consumer : list) {
                @SuppressWarnings("unchecked")
                Consumer<Object> typed = (Consumer<Object>) consumer;
                typed.accept(event);
            }
        }
    }

    /* Methode to add a ClickListener to an actor */
    public static void addClickListener(Actor actor, Runnable action) {
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });
    }
}
