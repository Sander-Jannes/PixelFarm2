package com.sj.pixelfarm.core.ui.styles;


public enum TextButtonStyles {
    SHOP_BUTTON("shop-button"),
    SOP_CARD_BUTTON("shop-card-button");

    private final String name;

    TextButtonStyles(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
