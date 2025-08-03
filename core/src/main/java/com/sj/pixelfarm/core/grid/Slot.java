package com.sj.pixelfarm.core.grid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pools;


public class Slot<T extends SlotObject> extends Container<T> {

    protected @Null T obj;
    private int number;
    private int slotType;
    private final Vector2 tmpVec = new Vector2();

    public Slot() {}

    public Slot(int number, int slotType, float rx, float ry, float width, float height) {
        this.number = number;
        this.slotType = slotType;
        setBounds(rx, ry, width, height);
    }

    public void set(Slot<T> slot) {
        this.number = slot.number;
        this.slotType = slot.slotType;
        setObj(slot.obj);
        setBounds(slot.getX(), slot.getY(), slot.getWidth(), slot.getHeight());
        slot.detachObj();
    }

    public @Null T getObj() { return obj; }

    public int getNumber() { return number; }

    public int getSlotType() { return slotType; }

    public Vector2 getScreenPosition() {
        tmpVec.set(0, 0);
        return localToAscendantCoordinates(null, tmpVec);
    }

    public void setObj(T obj) {
        if (this.obj != null) {
            removeActor(this.obj);
        }

        this.obj = obj;

        if (obj != null) {
            setActor(obj);
            fill();
        }
    }

    public boolean contains(Vector2 point) {
        return point.x >= getX() && point.x <= getX() + getWidth()
                && point.y >= getY() && point.y <= getY() + getHeight();
    }

    public boolean isEmpty() { return obj == null; }

    public void swapObj(Slot<T> other) {
        T copy = other.getObj();
        other.setObj(obj);
        setObj(copy);
    }

    public void detachObj() {
        if (obj != null) {
            removeActor(obj);
            obj = null;
        }
    }

    public void destroyObj(boolean hasPool) {
        if (obj != null) {
            removeActor(obj);
            if (hasPool) Pools.free(obj);
            obj = null;
        }
    }

    public boolean equals(Slot<T> other) {
        return slotType == other.getSlotType();
    }
}
