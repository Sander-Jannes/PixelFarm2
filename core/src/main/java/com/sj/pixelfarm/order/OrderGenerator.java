package com.sj.pixelfarm.order;

import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.itemgrid.ItemStack;
import com.sj.pixelfarm.core.mem.PoolManager;
import com.sj.pixelfarm.items.Items;

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
        Item randomItem = possibleItems[random.nextInt(possibleItems.length)];
        int amount = random.nextInt(10) + 1;
        int price = 20 + random.nextInt(81);
        int xp = 5 + random.nextInt(16);
        String customerName = possibleNames[random.nextInt(possibleNames.length)];

        ItemStack[] stacks = {
            PoolManager.obtain(randomItem, amount, Item.Quality.NONE)
        };

        currentOrder++;

        return new Order(stacks, price, xp, customerName);
    }
}
