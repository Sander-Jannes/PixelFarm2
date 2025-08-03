package com.sj.pixelfarm.core.ui.styles;

public enum ButtonStyles {
    OPEN_BOX("open-box"),
    CLOSE_BOX("close-box"),
    REMOVE_ITEM("remove-item"),
    SPLIT_ITEM("split-item"),
    FLIP_BUTTON("flip-button");

    private final String name;

    ButtonStyles(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
