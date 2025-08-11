package com.sj.pixelfarm.items;


import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.item.ItemType;
import com.sj.pixelfarm.core.item.actions.ActionInfo;
import com.sj.pixelfarm.core.item.actions.ActionTarget;
import com.sj.pixelfarm.core.item.actions.Actions;
import com.sj.pixelfarm.core.input.Interactions;
import com.sj.pixelfarm.core.item.actions.ActionProps;
import com.sj.pixelfarm.world.TileType;


public class Items {

    public static Item
        lettuce, carrot, tomato, cauliflower, broccoli, pumpkin, cucumber, onion, eggplant,
        lettuce_seeds, carrot_seeds, tomato_seeds, cauliflower_seeds, broccoli_seeds, pumpkin_seeds, cucumber_seeds, onion_seeds, eggplant_seeds,
        carrot_soup, tomato_soup, cauliflower_soup, broccoli_soup, pumpkin_soup,
        scythe, watering_can, fertilizer,
        box, closed_box, test;

    public static BonusItem flowers;

    public static void load() {
        lettuce = new Item(ItemType.LETTUCE, 1, 1) {{
            description = "";
        }};

        carrot = new Item(ItemType.CARROT, 1, 1) {{
            description = "";
        }};

        tomato = new Item(ItemType.TOMATO, 1, 1) {{
            description = "";
        }};

        cauliflower = new Item(ItemType.CAULIFLOWER, 1, 1) {{
            description = "";
        }};

        broccoli = new Item(ItemType.BROCCOLI, 1, 1) {{
            description = "";
        }};

        pumpkin = new Item(ItemType.PUMPKIN, 1, 1) {{
            description = "";
        }};

        cucumber = new Item(ItemType.CUCUMBER, 1, 1){{
            description = "";
        }};

        onion = new Item(ItemType.ONION, 1, 1) {{
            description = "";
        }};

        eggplant = new Item(ItemType.EGGPLANT, 1, 1) {{
            description = "";
        }};

        lettuce_seeds = new Item(ItemType.LETTUCE_SEEDS, 1, lettuce.unlockLevel) {{
            description = "";
            groups.add(Group.SEEDS);
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.PLANT, ActionTarget.FIELDS,
                    new ActionProps.Plant(TileType.LETTUCE, 5f, 99f, 0, 0, 4, Items.lettuce))
            );
        }};

        carrot_seeds = new Item(ItemType.CARROT_SEEDS, 1, carrot.unlockLevel) {{
            description = "";
            groups.add(Group.SEEDS);
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.PLANT, ActionTarget.FIELDS,
                    new ActionProps.Plant(TileType.CARROT, 2f, 99f, 0, 0, 4, Items.carrot))
            );
        }};

        tomato_seeds = new Item(ItemType.TOMATO_SEEDS, 1, tomato.unlockLevel) {{
            description = "";
            groups.add(Group.SEEDS);
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.PLANT, ActionTarget.FIELDS,
                    new ActionProps.Plant(TileType.TOMATO, 2f, 99f, 0, 0, 4, Items.tomato))
            );
        }};

        cauliflower_seeds = new Item(ItemType.CAULIFLOWER_SEEDS, 1, cauliflower.unlockLevel) {{
            description = "";
            groups.add(Group.SEEDS);
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.PLANT, ActionTarget.FIELDS,
                    new ActionProps.Plant(TileType.CAULIFLOWER, 2f, 99f, 0, 0, 4, Items.cauliflower))
            );
        }};

        broccoli_seeds = new Item(ItemType.BROCCOLI_SEEDS, 1, broccoli.unlockLevel) {{
            description = "";
            groups.add(Group.SEEDS);
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.PLANT, ActionTarget.FIELDS,
                    new ActionProps.Plant(TileType.BROCCOLI, 2f, 99f, 0, 0, 4, Items.broccoli))
            );
        }};

        pumpkin_seeds = new Item(ItemType.PUMPKIN_SEEDS, 1, pumpkin.unlockLevel) {{
            description = "";
            groups.add(Group.SEEDS);
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.PLANT, ActionTarget.FIELDS,
                    new ActionProps.Plant(TileType.PUMPKIN, 2f, 99f, 0, 0, 4, Items.pumpkin))
            );
        }};

        cucumber_seeds = new Item(ItemType.CUCUMBER_SEEDS, 1, cucumber.unlockLevel) {{
            description = "";
            groups.add(Group.SEEDS);
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.PLANT, ActionTarget.FIELDS,
                    new ActionProps.Plant(TileType.CUCUMBER, 2f, 99f, 0, 0, 4, Items.cucumber))
            );
        }};

        onion_seeds = new Item(ItemType.ONION_SEEDS, 1, onion.unlockLevel) {{
            description = "";
            groups.add(Group.SEEDS);
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.PLANT, ActionTarget.FIELDS,
                    new ActionProps.Plant(TileType.ONION, 2f, 99f, 0, 0, 4, Items.onion))
            );
        }};

        eggplant_seeds = new Item(ItemType.EGGPLANT_SEEDS, 1, eggplant.unlockLevel) {{
            description = "";
            groups.add(Group.SEEDS);
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.PLANT, ActionTarget.FIELDS,
                    new ActionProps.Plant(TileType.EGGPLANT, 60f, 99f, 2, 0, 4, Items.eggplant))
            );
        }};

        carrot_soup = new Item(ItemType.CARROT_SOUP, 1, 1) {{
            description = "";
        }};

        tomato_soup = new Item(ItemType.TOMATO_SOUP, 1, 1) {{
            description = "";
        }};

        cauliflower_soup = new Item(ItemType.CAULIFLOWER_SOUP, 1, 1) {{
            description = "";
        }};

        broccoli_soup = new Item(ItemType.BROCCOLI_SOUP, 1, 1) {{
            description = "";
        }};

        pumpkin_soup = new Item(ItemType.PUMPKIN_SOUP, 1, 1) {{
            description = "";
        }};

        scythe = new Item(ItemType.SCYTHE, 2, 1) {{
            description = "";
            interactionMap.put(
                Interactions.LEFT_HOLD,
                new ActionInfo(Actions.HARVEST, ActionTarget.CROPS, null)
            );
        }};

        watering_can = new Item(ItemType.WATERING_CAN, 1, 1) {{
            description = "";
            groups.add(Group.CARE);
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.USE, ActionTarget.CROPS, new ActionProps.Use("water", 12.5f + 0.5f, TileType.WATER_ANIMATION))
            );
        }};

        fertilizer = new Item(ItemType.FERTILIZER, 1, 1) {{
            description = "";
            groups.add(Group.CARE);
            interactionMap.put(
                Interactions.COMBO_HOLD,
                new ActionInfo(Actions.USE, ActionTarget.CROPS, new ActionProps.Use("fertilizer", 12.5f + 0.5f, TileType.FERTILIZER_ANIMATION))
            );
        }};

        box = new Item(ItemType.BOX, 3, 1) {{
            description = "";
        }};

        closed_box = new Item(ItemType.CLOSED_BOX, 3, 1) {{
            description = "";
            interactionMap.put(
                Interactions.LEFT_RELEASE,
                new ActionInfo(Actions.SELL, ActionTarget.CAR, null)
            );
        }};

        flowers = new BonusItem(ItemType.FLOWERS, 1, 1) {{
            multiplier = 2.0f;
            description = "";
        }};

        test = new BonusItem(ItemType.CARROT_SOUP, 1, 1) {{
            multiplier = 2.0f;
            description = "";
        }};
    }
}
