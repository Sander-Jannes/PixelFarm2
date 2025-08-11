package com.sj.pixelfarm.world.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Null;
import com.sj.pixelfarm.Vars;
import com.sj.pixelfarm.input.events.EventType;
import com.sj.pixelfarm.core.Events;
import com.sj.pixelfarm.ui.elements.actionbar.ActionBar;
import com.sj.pixelfarm.ui.styles.ButtonStyles;
import com.sj.pixelfarm.ui.styles.LabelStyles;
import com.sj.pixelfarm.ui.styles.UIColors;
import com.sj.pixelfarm.world.types.FieldGroup;
import com.sj.pixelfarm.world.World;

import java.util.ArrayList;

import static com.sj.pixelfarm.core.ui.UIUtils.*;


public class EditMode implements Disposable {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final ActionBar actionBar;
    private final World world;
    private boolean editMode = false;
    private @Null FieldGroup selectedField = null;
    public final ArrayList<FieldGroup> fields;

    public EditMode(World world, ArrayList<FieldGroup> fields) {
        this.world = world;
        this.fields = fields;

        actionBar = createActionBar(bar -> {
            Label level = createLabel("", LabelStyles.X18, null, null);
            HorizontalGroup priceLabel = new HorizontalGroup().space(5);
            priceLabel.addActor(createImage("ui/icons/coin"));
            priceLabel.addActor(createLabel("", LabelStyles.X20, null, null));

            Button buy = createButton(ButtonStyles.BUY_BUTTON, button -> {
                if (Vars.state.money >= selectedField.price) {
                    Vars.state.money -= selectedField.price;
                    Events.fire(new EventType.UpdateOverlayEvent());
                    Vars.state.unlockedFields.add(selectedField);
                    selectedField.unlock();
                } else {
                    Events.fire(new EventType.ShowErrorMessage("Not enough money!"));
                }
            });

            bar.space(10);
            bar.addState(2, level);
            bar.addState(1, priceLabel, buy);
            bar.setState(1);
        });

        Events.on(EventType.RemoveActionBar.class, e -> {
            if (selectedField != null) selectedField.isSelected = false;
        });
    }

    public void showActionBar(Vector2 pos) {
        world.viewport.unproject(pos);

        if (editMode && selectedField != null && !selectedField.isUnlocked && selectedField.contains(pos)) {
            selectedField.isSelected = true;

            if (Vars.state.level >= selectedField.unlockLevel) {
                HorizontalGroup g = actionBar.getElement(1, 0, HorizontalGroup.class);
                Label l = (Label) g.getChild(1);
                l.setText(selectedField.price);
                actionBar.setState(1);
            } else {
                Label level = actionBar.getElement(2, 0, Label.class);
                level.setText("Unlocked at level: " + selectedField.unlockLevel);
                actionBar.setState(2);
            }

            Events.fire(new EventType.ShowActionBarEvent(actionBar, world.viewport.project(selectedField.getCenter())));
        }
    }

    public boolean isActive() {
        return editMode;
    }

    public void toggle() {
        editMode = !editMode;
        selectedField = null;

        if (editMode) {
            world.worldMap.setLayerOpacity(World.Layers.DECORATION, 0.15f);
        } else {
            world.worldMap.setLayerOpacity(World.Layers.DECORATION, 1f);
        }
    }

    public void draw() {
        shapeRenderer.setProjectionMatrix(world.viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glLineWidth(4f);

        for (FieldGroup field : fields) {
            shapeRenderer.setColor(field.getColor());
            shapeRenderer.polygon(field.getVertices());

            if (field.contains(world.getMouse())) {
                selectedField = field;
                shapeRenderer.setColor(UIColors.hightlightColor);
                shapeRenderer.polygon(selectedField.getVertices());
            }
        }

        if (selectedField != null && selectedField.isSelected) {
            shapeRenderer.setColor(UIColors.hightlightColor);
            shapeRenderer.polygon(selectedField.getVertices());
        }

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1f);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
