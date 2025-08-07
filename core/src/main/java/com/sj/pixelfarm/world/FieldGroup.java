package com.sj.pixelfarm.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.sj.pixelfarm.core.ui.styles.UIColors;


public class FieldGroup {

    private final Color color = UIColors.fieldGroupColor.cpy();
    private final Polygon field;
    private final int price;

    public boolean isUnlocked = false;
    public boolean isSelected = false;

    public FieldGroup(Polygon field, int price) {
        this.field = field;
        this.price = price;
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

    public String getPrice() {
        return String.valueOf(price);
    }

    public void unlock() {
        isUnlocked = true;
        setColor(UIColors.selectColor);
    }

    public Vector2 getCenter() {
        return field.getCentroid(new Vector2(0, 0));
    }
}
