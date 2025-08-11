package com.sj.pixelfarm.world.types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.sj.pixelfarm.ui.styles.UIColors;


public class FieldGroup {

    private final Color color = UIColors.fieldGroupColor.cpy();
    private final Polygon field;

    public final int price;
    public final int unlockLevel;
    public boolean isUnlocked = false;
    public boolean isSelected = false;

    public FieldGroup(Polygon field, int price, int unlockLevel) {
        this.field = field;
        this.price = price;
        this.unlockLevel = unlockLevel;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public Color getColor() {
        return color;
    }

    public float[] getVertices() {
        return field.getVertices();
    }

    public boolean contains(Vector2 pos) {
        return field.contains(pos);
    }

    public void unlock() {
        isUnlocked = true;
        setColor(UIColors.selectColor);
    }

    public Vector2 getCenter() {
        return field.getCentroid(new Vector2(0, 0));
    }
}
