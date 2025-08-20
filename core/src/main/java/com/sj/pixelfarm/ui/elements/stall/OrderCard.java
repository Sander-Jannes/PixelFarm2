package com.sj.pixelfarm.ui.elements.stall;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.sj.pixelfarm.core.card.Card;
import com.sj.pixelfarm.core.grid.GridLoader;
import com.sj.pixelfarm.itemgrid.ItemStack;
import com.sj.pixelfarm.mem.Assets;
import com.sj.pixelfarm.order.Order;
import com.sj.pixelfarm.ui.styles.LabelStyles;

import static com.sj.pixelfarm.core.ui.UIUtils.*;
import static com.sj.pixelfarm.core.ui.UIUtils.createLabelContainer;


public class OrderCard extends Card {

    private static final String IMAGE = "stall/order";
    private static final String GRID_FILE = "gridconfig/ordercard.json";

    public OrderCard(Order order) {
        super(IMAGE, 0, 0, false);

        OrderCardGrid grid = new OrderCardGrid(GridLoader.load(GRID_FILE, Assets::getAtlasTexture), 64, "");

        Table main = new Table();
        main.row().expandX();

        Table items = new Table();
        items.row();
        items.add(createLabel("name: " + order.client, LabelStyles.X14, null, null)).padTop(-getHeight() / 1.5f);

        HorizontalGroup orderBody = new HorizontalGroup();
        for (ItemStack itemStack : order.getItems()) {
            VerticalGroup group = new VerticalGroup().space(-10);

            group.addActor(createItemPreview(itemStack.item, -0.2f));
            Container<Label> quantityContainer = createLabelContainer(itemStack.amount + "x", LabelStyles.X15, null, null);

            group.addActor(quantityContainer);
            orderBody.addActor(group);
        }

        items.row();
        items.add(orderBody);

        main.add(items).left();
        main.add(grid).right();

        cardStack.add(main);
    }
}
