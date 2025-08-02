package com.sj.pixelfarm.core.item;


import com.sj.pixelfarm.core.input.Actions;
import com.sj.pixelfarm.core.input.Interactions;
import com.sj.pixelfarm.world.TileType;
import com.sj.pixelfarm.world.World;


public class Items {

    public static Item
        lettuce, carrot, tomato, cauliflower, broccoli, pumpkin, cucumber, onion, eggplant,
        lettuce_seeds,
        carrot_soup, tomato_soup, cauliflower_soup, broccoli_soup, pumpkin_soup,
        scythe, shovel, rake;

    public static void load() {

        lettuce = new Item(ItemType.LETTUCE, 1) {{
            description = "";
        }};

        carrot = new Item(ItemType.CARROT, 1) {{
            description = "";
        }};

        tomato = new Item(ItemType.TOMATO, 1) {{
            description = "";
        }};

        cauliflower = new Item(ItemType.CAULIFLOWER, 1) {{
            description = "";
        }};

        broccoli = new Item(ItemType.BROCCOLI, 1) {{
            description = "";
        }};

        pumpkin = new Item(ItemType.PUMPKIN, 1) {{
            description = "";
        }};

        cucumber = new Item(ItemType.CUCUMBER, 1){{
            description = "";
        }};

        onion = new Item(ItemType.ONION, 1) {{
            description = "";
        }};

        eggplant = new Item(ItemType.EGGPLANT, 1) {{
            description = "";
        }};

        lettuce_seeds = new Item(ItemType.LETTUCE_SEEDS, 1) {{
            description = "";
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.PLANT, "group", "fields", World.Layers.GROUND,
                    new PlantProps(TileType.LETTUCE, 1f, 99f, 0, 0, 4, Items.lettuce))
            );
        }};

        carrot_soup = new Item(ItemType.CARROT_SOUP, 1) {{
            description = "";
        }};

        tomato_soup = new Item(ItemType.TOMATO_SOUP, 1) {{
            description = "";
        }};

        cauliflower_soup = new Item(ItemType.CAULIFLOWER_SOUP, 1) {{
            description = "";
        }};

        broccoli_soup = new Item(ItemType.BROCCOLI_SOUP, 1) {{
            description = "";
        }};

        pumpkin_soup = new Item(ItemType.PUMPKIN_SOUP, 1) {{
            description = "";
        }};

        scythe = new Item(ItemType.SCYTHE, 1) {{
            description = "";
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.HARVEST, "group", "crops", World.Layers.DECORATION, null)
            );
        }};

        shovel = new Item(ItemType.SHOVEL, 2) {{
            description = "";
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.MAP_CHANGE, "group", "tiles", World.Layers.GROUND, new MapChangeProps(TileType.FIELD))
            );
        }};

        rake = new Item(ItemType.RAKE, 2) {{
            description = "";
        }};
    }
}
