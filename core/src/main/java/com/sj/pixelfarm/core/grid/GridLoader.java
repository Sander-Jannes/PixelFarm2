package com.sj.pixelfarm.core.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Null;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;


public final class GridLoader {

    @SuppressWarnings("unchecked")
    public static <T extends SlotObject, U extends Slot<T>> GridConfig<T, U> load(String filePath, TextureResolver resolver) {
        try {
            JsonReader json = new JsonReader();
            JsonValue base = json.parse(Gdx.files.internal(filePath));
            TextureRegion background = null;

            // background
            String backgroundPath = base.getString("background", null);
            if (backgroundPath != null) {
                background = resolver.get(backgroundPath);
            }

            // slot info
            Map<Integer, SlotInfo> slotInfoMap = new HashMap<>();
            JsonValue info = base.get("slotInfo");
            for (JsonValue slot : info) {
                String selectImage = slot.getString("selectImage");

                slotInfoMap.put(slot.getInt("slotType"), new SlotInfo(
                    selectImage != null ? resolver.get(selectImage) : null,
                    slot.get("size").asFloatArray(),
                    slot.getString("class")
                ));
            }

            // slot instances
            Array<U> slots = new Array<>();
            JsonValue allSlots = base.get("slots");
            for (JsonValue slot : allSlots) {
                float[] pos = slot.get("pos").asFloatArray();
                int slotType = slot.getInt("slotType");
                String className = slotInfoMap.get(slotType).clazz();

                if (className != null) {
                    Class<?> clazz = Class.forName(className);
                    Constructor<?> constructor = clazz.getDeclaredConstructor(
                        int.class, int.class, float.class, float.class, float.class, float.class
                    );

                    U newSlot = (U) constructor.newInstance(
                        slot.getInt("number"), slotType,
                        pos[0], pos[1],
                        slotInfoMap.get(slotType).size()[0],
                        slotInfoMap.get(slotType).size()[1]
                    );
                    slots.add(newSlot);

                } else {
                    Slot<T> newSlot = new Slot<>(slot.getInt("number"), slotType,
                        pos[0], pos[1],
                        slotInfoMap.get(slotType).size()[0],
                        slotInfoMap.get(slotType).size()[1]);

                    slots.add((U) newSlot);
                }
            }

            return new GridConfig<>(background, slots, slotInfoMap);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load grid from JSON: " + filePath, e);
        }
    }

    public record GridConfig<T extends SlotObject, U extends Slot<T>>(
            @Null TextureRegion background,
            Array<U> slots,
            Map<Integer, SlotInfo> slotInfo
    ) { }

    public record SlotInfo(
            @Null TextureRegion selectImage,
            float[] size,
            String clazz
    ) {}
}
