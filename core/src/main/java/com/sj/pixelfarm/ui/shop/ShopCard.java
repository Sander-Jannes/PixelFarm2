package com.sj.pixelfarm.ui.shop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.Vars;
import com.sj.pixelfarm.core.card.UnlockableCard;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.itemgrid.ItemStack;
import com.sj.pixelfarm.core.mem.PoolManager;
import com.sj.pixelfarm.core.ui.styles.LabelStyles;
import com.sj.pixelfarm.core.ui.styles.TextButtonStyles;

import java.util.ArrayList;

import static com.sj.pixelfarm.core.ui.UIUtils.*;


public class ShopCard extends UnlockableCard {

    private static final String CARD_TEXTURE = "shop/shop_card";
    private final Item item;
    private final TextButton button;

    public ShopCard(Item item) {
        super(CARD_TEXTURE, 0, 0, false, item.unlockLevel);
        this.item = item;

        Label label = createLabel(item.description, LabelStyles.X13, Color.BROWN, null);
        label.setWrap(true);
        label.setAlignment(Align.topLeft);

        Container<Label> container = new Container<>(label);
        container.pad(20).padTop(60);
        container.fill();

        back.add(container);

        button = createTextButton("buy", TextButtonStyles.SOP_CARD_BUTTON, button -> {
            if (Vars.state.money >= item.price) {
                ItemStack itemStack = PoolManager.obtain(item, 1, Item.Quality.NONE);
                Events.fire(new EventType.PutItemInGridEvent(itemStack, Entities.HOTBAR, () -> { }));

            } else {
                Events.fire(new EventType.ShowErrorMessage("Not enough money!"));
            }
        });
        button.setTouchable(Touchable.disabled);

        Table frontTable = new Table();
        frontTable.add(createLabel(item.itemType.getName(), LabelStyles.X16, null, null));
        frontTable.row().space(20);
        frontTable.add(createItemPreview(item, 0.6f));
        frontTable.row().space(20);

        HorizontalGroup price = new HorizontalGroup().space(5);
        price.addActor(createLabel(item.price + "", LabelStyles.X20, null, null));
        price.addActor(createImage("ui/icons/coin"));
        frontTable.add(price);

        frontTable.row().space(20);
        frontTable.add(button);
        front.add(frontTable);

        unlock(() -> button.setTouchable(Touchable.enabled));

        Events.on(EventType.UnlockCardEvent.class, e -> unlock(() -> button.setTouchable(Touchable.enabled)));
    }

    public ArrayList<Item.Group> getGroups() {
        return item.groups;
    }
}
