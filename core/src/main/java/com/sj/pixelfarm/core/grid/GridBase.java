package com.sj.pixelfarm.core.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;

import java.util.Arrays;
import java.util.HashMap;


/**
 * The Grid class is a generic grid-based container system for managing and displaying slots
 * of clazz T, where T should extend SlotObject. It provides functionality to load a grid
 * configuration from a JSON file, manage slots, query slot information, and render the grid.
 * @param <T> The clazz of objects this grid will hold, extending SlotObject.
 */
public abstract class GridBase<T extends SlotObject, U extends Slot<T>> extends Group {

    private final HashMap<Integer, GridLoader.SlotInfo> slotInfo = new HashMap<>();
    private boolean isSelectable = true;
    private @Null TextureRegion background = null;
    protected final Vector2 tempVec2 = new Vector2();
    protected final Array<U> slots = new Array<>();

    public GridBase(GridLoader.GridConfig<T, U> data) {
        this.slotInfo.putAll(data.slotInfo());

        for (U slot : data.slots()) {
            addSlot(slot);
        }

        if (data.background() != null) {
            this.background = data.background();
            setBounds(0, 0, background.getRegionWidth(), background.getRegionHeight());
        }
    }

    public int getSize() {
        return slots.size;
    }

    public Array<U> getSlots() {
        return this.slots;
    }

    public Array<U> getFullSlots() {
        Array<U> fullSlots = new Array<>();

        for (U slot : slots) {
            if (!slot.isEmpty()) fullSlots.add(slot);
        }
        return fullSlots;
    }

    public @Null U getSlotByNumber(int number) {
        for (U slot : slots) {
            if (slot.getNumber() == number) {
                return slot;
            }
        }
        return null;
    }

    public @Null U getSlotByPos(Vector2 localPos) {
        for (U slot : slots) {
            if (slot.contains(localPos)) {
                return slot;
            }
        }
        return null;
    }

    public @Null U getFreeSlot(T obj) {
        for (U slot : slots) {
            if (slot.isEmpty() && slot.getSlotType() == obj.getSlotType()) {
                return slot;
            }
        }
        return null;
    }

    public @Null U getFreeSlot(T obj, int[] excludeNumbers) {
        for (U slot : slots.select(s -> Arrays.stream(excludeNumbers).anyMatch(n -> n != s.getNumber()))) {
            if (slot.isEmpty() && slot.getSlotType() == obj.getSlotType()) {
                return slot;
            }
        }
        return null;
    }

    public void setSelectable(boolean value) { this.isSelectable = value; }

    public final void setObjToSlot(int slotNumber, T item) {
        U slot = getSlotByNumber(slotNumber);

        if (slot != null && item.getSlotType() == slot.getSlotType()) {
            slot.setObj(item);
        }
    }

    public void reset(boolean hasPool) {
        for (U slot : slots) {
            slot.destroyObj(hasPool);
        }
    }

    private void addSlot(U slot) {
        slot.setOrigin(Align.center);
        slots.add(slot);
        addActor(slot);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        tempVec2.set(Gdx.input.getX(), Gdx.input.getY());
        screenToLocalCoordinates(tempVec2);

        if (background != null) {
            batch.draw(background, getX(), getY());
        }

        if (isSelectable) {
            for (U slot : slots) {
                GridLoader.SlotInfo info = slotInfo.get(slot.getSlotType());

                if (info != null) {
                    if (slot.contains(tempVec2) && info.selectImage() != null) {
                        batch.draw(info.selectImage(), getX() + slot.getX(), getY() + slot.getY(),
                                slot.getWidth(), slot.getHeight());
                    }
                }
            }
        }

        super.draw(batch, parentAlpha);
    }
}
