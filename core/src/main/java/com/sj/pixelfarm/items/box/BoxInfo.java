package com.sj.pixelfarm.items.box;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.sj.pixelfarm.Entities;
import com.sj.pixelfarm.core.card.Card;
import com.sj.pixelfarm.core.grid.GridLoader;
import com.sj.pixelfarm.itemgrid.ItemStack;
import com.sj.pixelfarm.mem.Assets;
import com.sj.pixelfarm.ui.styles.LabelStyles;
import com.sj.pixelfarm.items.BonusItem;
import com.sj.pixelfarm.order.OrderGenerator;

import static com.sj.pixelfarm.core.ui.UIUtils.*;


public class BoxInfo extends Card {

    private static final String IMAGE = "box_info/box_info2";
    private static final String GRID_FILE = "gridconfig/boxInfo.json";
    private static final int MAX_SLOT_CAPACITY = 64;

    private final Label orderNumberLabel;
    private final Label bonusLabel;

    public final BoxInfoGrid grid;

    public BoxInfo() {
        super(IMAGE, 0, 0, false);
        setName(Entities.BOX_INFO);

        grid = new BoxInfoGrid(GridLoader.load(GRID_FILE, Assets::getAtlasTexture), MAX_SLOT_CAPACITY, this);

        orderNumberLabel = createLabel("", LabelStyles.X20, Color.BROWN, null);
        bonusLabel = createLabel("Bonus: 0x", LabelStyles.X17, null, null);
        bonusLabel.setTouchable(Touchable.disabled);

        Table table = createTableWithRemoveButton(button -> setVisible(false));
        cardStack.add(grid);
        cardStack.addActor(table);

        table.add(orderNumberLabel).padTop(20);
        table.row().expand();
        table.add(bonusLabel).left().padLeft(15).bottom().padBottom(35);
    }

    public void setBonus(ItemStack stack) {
        BonusItem item = (BonusItem) stack.item;
        bonusLabel.setText("");
        bonusLabel.setText("Bonus: " + item.multiplier * stack.amount + "x");
    }

    public void removeBonus() {
        bonusLabel.setText("Bonus: 0x");
    }

    public void updateOrderInformation() {
        orderNumberLabel.setText("Order: #" + String.format("%04d", OrderGenerator.currentOrder));
    }

    public void reset() {
        grid.reset(true);
        grid.setTouchable(Touchable.enabled);
        bonusLabel.setText("Bonus: 0x");
    }

    public float getMultiplier() {
        return grid.getMultiplier();
    }
}
