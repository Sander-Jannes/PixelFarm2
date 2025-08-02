package com.sj.pixelfarm.core.itemgrid;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pool;
import com.sj.pixelfarm.core.grid.SlotObject;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.mem.Assets;
import com.sj.pixelfarm.core.mem.PoolManager;
import com.sj.pixelfarm.core.ui.styles.LabelStyles;

import static com.sj.pixelfarm.core.ui.UIUtils.*;


public class ItemStack extends SlotObject implements Pool.Poolable {

    public @Null Item item;
    public @Null Item.Quality quality;
    public int amount;

    private final Label amountLabel = createLabel("", LabelStyles.X12, null, Align.center);
    private final Container<Image> overlayImageContainer = new Container<>();
    private final Container<Image> imageContainer = new Container<Image>().fill();

    public ItemStack() {
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
        overlayImageContainer.setActor(new Image(Assets.qualities.get(quality.ordinal())));
        amountLabel.setText(String.valueOf(amount));
    }

    public void setAmount(int n) {
        this.amount = n;
        amountLabel.setText(String.valueOf(this.amount));
    }

    public void addAmount(int n) {
        this.amount += n;

        if (this.amount == 0) {
            getParent().destroyObj(false);
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

    @Override
    public ItemStackSlot getParent() {
        return (ItemStackSlot) super.getParent();
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
