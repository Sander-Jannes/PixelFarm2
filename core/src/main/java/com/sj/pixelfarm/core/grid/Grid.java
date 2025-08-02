package com.sj.pixelfarm.core.grid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public interface Grid<T extends SlotObject, U extends Slot<T>> {

    int getSize();

    Array<U> getSlots();
    Array<U> getFullSlots();

    U getSlotByNumber(int number);
    U getSlotByPos(Vector2 pos);
    U getFreeSlot(int slotType);

    void setSelectable(boolean value);
    void setObjToSlot(int slotNumber, T item);

    void reset(boolean hasPool);
}
