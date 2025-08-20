package com.sj.pixelfarm.itemgrid;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pool;
import com.sj.pixelfarm.core.grid.SlotObject;
import com.sj.pixelfarm.items.Item;
import com.sj.pixelfarm.mem.Assets;
import com.sj.pixelfarm.mem.PoolManager;
import com.sj.pixelfarm.ui.styles.LabelStyles;
import com.sj.pixelfarm.ui.elements.actionbar.ActionBar;

import static com.sj.pixelfarm.core.ui.UIUtils.*;


public class ItemStack extends SlotObject implements Pool.Poolable {

    public @Null Item item;
    public @Null Item.Quality quality;
    public int amount;

    private final Label amountLabel = createLabel("", LabelStyles.X12, null, Align.center);
    private final Container<Image> overlayImageContainer = new Container<>();
    private final Container<Image> imageContainer = new Container<Image>().fill();

    protected @Null ActionBar actionBar;

    public ItemStack() {
        setName("ItemStack");

        Container<Stack> overlayContainer = new Container<>();
        overlayContainer.right().top().padTop(5).padRight(5);

        Stack overlayStack = new Stack();
        overlayStack.add(overlayImageContainer);
        overlayStack.add(amountLabel);
        overlayContainer.setActor(overlayStack);

        Stack stack = new Stack();
        stack.add(imageContainer);
        stack.add(overlayContainer);
        setActor(stack);
        fill();
    }

    public void init(Item item, int amount, Item.Quality quality) {
        this.item = item;
        this.quality = quality;
        this.amount = amount;

        imageContainer.setActor(new Image(item.image));

        if (amount > 0) {
            overlayImageContainer.setActor(new Image(Assets.qualities.get(quality.ordinal())));
            amountLabel.setText(String.valueOf(amount));
        }
    }

    public void setAmount(int n) {
        this.amount = n;
        amountLabel.setText(String.valueOf(this.amount));
    }

    public void addAmount(int n) {
        this.amount += n;

        if (this.amount == 0) {
           ((ItemStackSlot) getParent()).destroyObj(true);
            return;
        }

        amountLabel.setText(String.valueOf(this.amount));
    }

    public void addStack(ItemStack stack) {
        amount += stack.amount;
        amountLabel.setText(String.valueOf(this.amount));
    }

    public ItemStack split() {
        if (!item.isStackable || amount == 1) return null;

        int bq = amount;
        setAmount(amount / 2);
        ItemStack clone = PoolManager.obtain(item, amount, quality);
        clone.addAmount(bq % 2);

        return clone;
    }

    public boolean equals(ItemStack other) {
        return quality == other.quality && item.equals(other.item);
    }

    public boolean equalsWithAmount(ItemStack other) {
        return amount == other.amount && equals(other);
    }

    @Override
    public int getSlotType() {
        return item.slotType;
    }

    @Override
    public void reset() {
        item = null;
        quality = null;
        amountLabel.clear();
        overlayImageContainer.clear();
        imageContainer.clear();
    }
}
