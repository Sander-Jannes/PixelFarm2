package com.sj.pixelfarm.core.item;


public enum ItemType {

    LETTUCE(0, "lettuce"),
    CARROT(1, "carrot"),
    TOMATO(2, "tomato"),
    CAULIFLOWER(3, "cauliflower"),
    BROCCOLI(4, "broccoli"),
    PUMPKIN(5, "pumpkin"),
    CUCUMBER(6, "cucumber"),
    ONION(7, "onion"),
    EGGPLANT(8, "eggplant"),

    LETTUCE_SEEDS(10, "lettuce seeds"),
    CARROT_SEEDS(11, "carrot seeds"),
    TOMATO_SEEDS(12, "tomato seeds"),
    CAULIFLOWER_SEEDS(13, "cauliflower seeds"),
    BROCCOLI_SEEDS(14, "broccoli seeds"),
    PUMPKIN_SEEDS(15, "pumpkin seeds"),
    CUCUMBER_SEEDS(16, "cucumber seeds"),
    ONION_SEEDS(17, "onion seeds"),
    EGGPLANT_SEEDS(18, "eggplant seeds"),
    EMPTY_SEEDS(19, "empty seeds"),

    CARROT_SOUP(20, "carrot soup"),
    TOMATO_SOUP(21, "tomato soup"),
    CAULIFLOWER_SOUP(22, "cauliflower soup"),
    BROCCOLI_SOUP(23, "broccoli soup"),
    PUMPKIN_SOUP(24, "pumpkin soup"),

    WATERING_CAN(30, "watering can"),
    SCYTHE(34, "scythe"),
    FERTILIZER(31, "fertilizer"),
    BOX(32, "box"),
    CLOSED_BOX(33, "closed box"),
    ACID(35, "acid"),

    SHOVEL(100, "shovel"),
    RAKE(101, "rake");

    private final String name;
    private final int index;

    ItemType(int index, String name) {
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
