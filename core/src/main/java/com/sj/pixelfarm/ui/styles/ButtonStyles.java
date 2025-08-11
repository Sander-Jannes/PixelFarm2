package com.sj.pixelfarm.ui.styles;

public enum ButtonStyles {
    ENTER("enter-button"),
    OPEN_BOX("open-box"),
    CLOSE_BOX("close-box"),
    REMOVE_ITEM("remove-item"),
    SPLIT_ITEM("split-item"),
    FLIP_BUTTON("flip-button"),
    INFO_BUTTON("info-button"),
    INVENTORY_BUTTON("inventory-button"),
    BUY_BUTTON("buy-button"),
    EDIT_BUTTON("edit-button"),
    CARD_REMOVE_BUTTON("card-remove-button"),
    ENTER_BUTTON("enter-button");

    private final String name;

    ButtonStyles(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
