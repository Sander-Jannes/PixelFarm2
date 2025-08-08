package com.sj.pixelfarm.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Null;
import com.sj.pixelfarm.core.Vars;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.ui.actionbar.ActionBar;
import com.sj.pixelfarm.core.ui.styles.ButtonStyles;
import com.sj.pixelfarm.core.ui.styles.LabelStyles;
import com.sj.pixelfarm.core.ui.styles.UIColors;

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
            HorizontalGroup priceLabel = new HorizontalGroup().space(5);
            priceLabel.addActor(createImage("ui/icons/coin"));
            priceLabel.addActor(createLabel("", LabelStyles.X20, null, null));

            Button buy = createButton(ButtonStyles.BUY_BUTTON, button -> {
                if (Vars.state.money >= selectedField.price) {
                    Vars.state.money -= selectedField.price;
                    Events.fire(new EventType.UpdateOverlayEvent());
                    selectedField.unlock();
                    Vars.state.unlockedFields.add(selectedField);
                }
            });

            bar.space(10);
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
            HorizontalGroup g = actionBar.getElement(1, 0, HorizontalGroup.class);
            Label l = (Label) g.getChild(1);
            l.setText(selectedField.price);
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
                shapeRenderer.setColor(UIColors.selectColor);
                shapeRenderer.polygon(selectedField.getVertices());
            }
        }

        if (selectedField != null && selectedField.isSelected) {
            shapeRenderer.setColor(UIColors.selectColor);
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
