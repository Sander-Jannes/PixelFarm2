package com.sj.pixelfarm.items.box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.sj.pixelfarm.core.grid.Slot;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.items.Items;
import com.sj.pixelfarm.core.itemgrid.ItemStack;
import com.sj.pixelfarm.core.ui.styles.ButtonStyles;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.sj.pixelfarm.core.ui.UIUtils.createActionBar;
import static com.sj.pixelfarm.core.ui.UIUtils.createButton;


public class Box extends ItemStack {

    private final BoxInfo boxInfo = new BoxInfo();

    public Box() {
        super();
        boxInfo.moveCenterTo(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
    }

    public void init() {
        super.init(Items.box, 0, Item.Quality.NONE);

        boxInfo.updateOrderInformation();

        actionBar = createActionBar(bar -> {
            Button open = createButton(ButtonStyles.OPEN_BOX, button -> {
                boxInfo.setVisible(true);
                // Dit moet eigenlijk ergens anders gebeuren maar weet niet echt hoe, hoeft niet elke keer toegevoegd te worden
                getStage().addActor(boxInfo);
            });

            Button close = createButton(ButtonStyles.CLOSE_BOX, button -> {
                close();
                bar.setState(2);
            });

            Button remove = createButton(ButtonStyles.REMOVE_ITEM, button -> {
                item = Items.box;
                setActor(new Image(item.image));
                boxInfo.reset();
                bar.setState(1);
            });

            bar.addState(1, open, close);
            bar.addState(2, open, remove);
            bar.setState(1);
            bar.space(5);
        });
    }

    public float getMultiplier() {
        return boxInfo.getMultiplier();
    }

    public List<ItemStack> getItems() {
        return StreamSupport.stream(boxInfo.grid.getSlots().spliterator(), false)
            .map(Slot::getObj)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private void close() {
        item = Items.closed_box;
        setActor(new Image(item.image));
        boxInfo.grid.setTouchable(Touchable.disabled);
    }

    @Override
    public void reset() {
        super.reset();
        boxInfo.reset();
        item = Items.box;
        setActor(new Image(item.image));
    }
}
