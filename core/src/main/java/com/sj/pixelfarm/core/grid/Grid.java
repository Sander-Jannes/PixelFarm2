package com.sj.pixelfarm.core.grid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;


public interface Grid<T extends SlotObject, U extends Slot<T>> {

    int getSize();

    Array<U> getSlots();
    Array<U> getFullSlots();

    @Null U getSlotByNumber(int number);
    @Null U getSlotByPos(Vector2 pos);
    @Null U getFreeSlot(int slotType);
    @Null U getFreeSlot(int slotType, int[] excludeNumbers);

    void setSelectable(boolean value);
    void setObjToSlot(int slotNumber, T item);

    void reset(boolean hasPool);
}
