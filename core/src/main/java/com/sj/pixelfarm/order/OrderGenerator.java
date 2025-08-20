package com.sj.pixelfarm.order;

import com.sj.pixelfarm.items.Item;
import com.sj.pixelfarm.itemgrid.ItemStack;
import com.sj.pixelfarm.mem.PoolManager;
import com.sj.pixelfarm.items.Items;

import java.util.ArrayList;
import java.util.Random;


public final class OrderGenerator {

    private static final Random random = new Random();
    public static int currentOrder = 0;

    private static final Item[] possibleItems = {
        Items.cucumber,
        Items.tomato,
        Items.carrot,
        Items.onion,
        Items.lettuce
    };

    private static final String[] possibleNames = {
        "Jos Vandeberg", "Marie Peeters", "Luc Janssens",
        "Sofie Vermeulen", "Koen De Smet"
    };

    public static Order generateRandomOrder() {
        int price = 20 + random.nextInt(81);
        int xp = 5 + random.nextInt(16);
        String customerName = possibleNames[random.nextInt(possibleNames.length)];
        int itemAmount = random.nextInt(3) + 1;
        ItemStack[] stacks = new ItemStack[itemAmount];

        for (int i = 0; i < itemAmount; i++) {
            Item randomItem = possibleItems[random.nextInt(possibleItems.length)];
            int amount = random.nextInt(10) + 1;
            stacks[i] = PoolManager.obtain(randomItem, amount, Item.Quality.NONE);
        }

        currentOrder++;

        return new Order(stacks, price, xp, customerName);
    }

    public static ArrayList<Order> generateOrders(int n) {
        ArrayList<Order> orders = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            orders.add(generateRandomOrder());
        }
        return orders;
    }
}
