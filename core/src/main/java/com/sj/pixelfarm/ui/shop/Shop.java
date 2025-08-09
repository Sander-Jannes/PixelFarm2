package com.sj.pixelfarm.ui.shop;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.item.Items;
import com.sj.pixelfarm.core.ui.effects.UIEffects;
import com.sj.pixelfarm.core.ui.modals.TabModal;
import static com.sj.pixelfarm.core.ui.UIUtils.*;


public class Shop extends TabModal<Item.Group> {

    private static final String IMAGE = "shop/shop";

    private final Array<ShopCard> cards = new Array<>();
    private final Table cardsTable = new Table();

    private final Item[] items = new Item[] {
            Items.lettuce_seeds, Items.carrot_seeds, Items.tomato_seeds, Items.cauliflower_seeds, Items.broccoli_seeds,
            Items.pumpkin_seeds, Items.cucumber_seeds, Items.onion_seeds, Items.eggplant_seeds,
            Items.watering_can, Items.fertilizer
    };

    public Shop() {
        super(IMAGE, Item.Group.SEEDS);
        setName(Entities.SHOP);
        setVisible(false);

        addTab("seeds", Item.Group.SEEDS, this::swapCards);
        addTab("care", Item.Group.CARE, this::swapCards);
        addTab("tools", Item.Group.TOOLS, this::swapCards);
        addTab("extra", Item.Group.EXTRA, this::swapCards);
        updateBar();

        for (Item item : items) {
            ShopCard card = new ShopCard(item);
            addCard(card);
        }

        ScrollPane scrollPane = createScrollPane(cardsTable, pane -> {
            pane.setFadeScrollBars(false);
            pane.setForceScroll(true, false);
            pane.setScrollingDisabled(false, true);
        });

        table.add(scrollPane).expandY().pad(10).center();
        swapCards();

        Events.on(EventType.ShowModalEvent.class, e -> {
            if (getName().equals(e.name())) setVisible(true);
            UIEffects.applySpawnInEffect(this);
        });
    }

    private void addCard(ShopCard card) {
        cards.add(card);
    }

    private void swapCards() {
        cardsTable.clear();

        for (int i = 0; i < cards.size; i++) {
            ShopCard card = cards.get(i);

            if (card.getGroups().stream().anyMatch(group -> group == getActiveTab())) {
                cardsTable.add(card).pad(25);
            }
        }
    }
}
