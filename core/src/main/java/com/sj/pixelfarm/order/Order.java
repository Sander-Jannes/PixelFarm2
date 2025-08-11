package com.sj.pixelfarm.order;


import com.sj.pixelfarm.core.itemgrid.ItemStack;
import com.sj.pixelfarm.items.box.Box;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public final class Order {

    public static String client = "";

    private final ArrayList<ItemStack> items;
    private final Map<String, Integer> order;

    public float value;
    public int xp;

    public Order(ItemStack[] items, float value, int xp, String c) {
        this.items = new ArrayList<>(Arrays.asList(items));
        this.value = value;
        this.xp = xp;
        client = c;

        order = this.items.stream()
            .collect(Collectors.groupingBy(
                item -> item.item.itemType.getName(),
                Collectors.summingInt(item -> item.amount)
            ));
    }

    public boolean doesBoxFulfilOrder(Box box) {
        Map<String, Integer> groupedItems = box.getItems().stream()
            .collect(Collectors.groupingBy(
                item -> item.item.itemType.getName(),
                Collectors.summingInt(item -> item.amount)));

        for (Map.Entry<String, Integer> entry : order.entrySet()) {
            if (!groupedItems.getOrDefault(entry.getKey(), -1).equals(entry.getValue())) return false;
        }

        return true;
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }
}
