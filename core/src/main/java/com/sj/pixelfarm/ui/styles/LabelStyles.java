package com.sj.pixelfarm.ui.styles;

public enum LabelStyles {

    X48("48x"),
    X32("32x"),
    X28("28x"),
    X26("26x"),
    X20("20x"),
    X18("18x"),
    X17("17x"),
    X16("16x"),
    X15("15x"),
    X14("14x"),
    X13("13x"),
    X12("12x");

    private final String name;

    LabelStyles(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
