package com.sj.pixelfarm.ui.modals;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.mem.Assets;
import com.sj.pixelfarm.core.ui.styles.TextButtonStyles;


public class TabBar<T> extends HorizontalGroup {

    private T activeTab;
    private final Array<BarButton<T>> buttons = new Array<>();

    public TabBar(T initialTab) {
        super();
        this.activeTab = initialTab;
    }

    public void addButton(String text, T tab, Runnable action) {
        BarButton<T> button = new BarButton<>(text, TextButtonStyles.SHOP_BUTTON, tab);
        if (button.tab == activeTab) button.setChecked(true);

        Events.addClickListener(button, () -> {
            activeTab = button.tab;
            action.run();
            buttons.forEach(b -> b.setChecked(button == b));
        });

        buttons.add(button);
    }

    public void update() {
        clearChildren();
        buttons.forEach(this::addActor);
    }

    public T getActiveTab() {
        return activeTab;
    }


    public static class BarButton<T> extends TextButton {

        private static final Skin skin = Assets.getSkin("common");
        public T tab;

        public BarButton(String text, TextButtonStyles styleName, T tab) {
            super(text, skin, styleName.getName());
            this.tab = tab;
        }
    }
}
