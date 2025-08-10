package com.sj.pixelfarm.items.box;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.card.Card;
import com.sj.pixelfarm.core.grid.GridLoader;
import com.sj.pixelfarm.core.itemgrid.ItemGrid;
import com.sj.pixelfarm.core.itemgrid.ItemStackSlot;
import com.sj.pixelfarm.core.mem.Assets;
import com.sj.pixelfarm.core.ui.styles.LabelStyles;
import com.sj.pixelfarm.items.Items;

import static com.sj.pixelfarm.core.ui.UIUtils.*;


public class BoxInfo extends Card {

    private static final String IMAGE = "box_info/box_info2";
    private static final String GRID_FILE = "gridconfig/boxInfo.json";
    private static final int MAX_SLOT_CAPACITY = 64;

    public final ItemGrid grid = new ItemGrid(GridLoader.load(GRID_FILE, Assets::getAtlasTexture), MAX_SLOT_CAPACITY, "");

    private final Label orderNumberLabel;
    private final Label clientLabel;

    public BoxInfo() {
        super(IMAGE, 0, 0, false);
        setName(Entities.BOX_INFO);

        ItemStackSlot slot = grid.getSlotByNumber(1);
        slot.setFixed(Items.flowers);

        orderNumberLabel = createLabel("", LabelStyles.X20, Color.BROWN, null);
        clientLabel = createLabel("", LabelStyles.X14, null, null);

        Table table = createTableWithRemoveButton(button -> setVisible(false));
        stack.add(grid);
        stack.addActor(table);

        table.add(orderNumberLabel);
        table.row().padTop(10);
        table.add(clientLabel);
    }

    public void updateOrderInformation() {
        orderNumberLabel.setText("Order: #" + String.format("%04d", OrderGenerator.currentOrder));
        clientLabel.setText("Client: " + Order.client);
    }
}
