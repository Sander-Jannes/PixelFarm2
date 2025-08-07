package com.sj.pixelfarm.core.ui;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.mem.Assets;
import com.sj.pixelfarm.core.ui.actionbar.ActionBar;
import com.sj.pixelfarm.core.ui.effects.UIEffect;
import com.sj.pixelfarm.core.ui.effects.UIEffects;
import com.sj.pixelfarm.core.ui.styles.ButtonStyles;
import com.sj.pixelfarm.core.ui.styles.LabelStyles;

import java.util.function.Consumer;


public final class UIUtils {

    public static Button createButton(ButtonStyles styleName, Consumer<Button> action) {
        Button button = new Button(Assets.getSkin("common"), styleName.getName());
        Events.addClickListener(button, () -> action.accept(button));
        return button;
    }

    public static void createPopupObject(Stage stage, Vector2 mouse, TextureRegion image, String text, UIEffect effectApplier) {
        Table animation = new Table();
        Image itemImage = new Image(image);

        itemImage.setScaling(Scaling.fit);
        animation.add(itemImage).size(itemImage.getWidth() / 1.5f, itemImage.getHeight() / 1.5f);
        Label quantityLabel = createLabel(text, LabelStyles.X14, null, null);
        animation.add(quantityLabel);
        animation.setBounds(mouse.x - animation.getWidth() / 2f, mouse.y + 10,
            itemImage.getWidth() / 2f, itemImage.getHeight() / 2f);

        stage.addActor(animation);
        effectApplier.apply(animation);
    }

    public static ActionBar createActionBar(Consumer<ActionBar> action) {
        ActionBar bar = new ActionBar();
        action.accept(bar);
        Events.addClickListener(bar, bar::remove);
        return bar;
    }

    public static ImageButton createImageButton(ButtonStyles styleName, Consumer<ImageButton> action) {
        ImageButton button = new ImageButton(Assets.getSkin("common"), styleName.getName());
        Events.addClickListener(button, () -> action.accept(button));
        return button;
    }

    public static Stack createMeter(String meterTexturePath, String pointerPath, String name) {
        Stack meterStack = new Stack();
        Image meter = createImage(meterTexturePath);
        meterStack.add(meter);

        Image pointer = createImage(pointerPath);
        Container<Image> pointerContainer = new Container<>(pointer);
        pointerContainer.setName(name);
        pointerContainer.left();
        meterStack.add(pointerContainer);

        return meterStack;
    }

    public static Label createLabel(String text, LabelStyles styleName, @Null Color color, @Null Integer align) {
        Label l = new Label(text, Assets.getSkin("common"), styleName.getName());
        if (color != null) l.setColor(color);
        if (align != null) l.setAlignment(align);
        return l;
    }

    public static void createPopupObject(Stage stage, Vector2 mouse, String text, LabelStyles styleName, Color color, UIEffect effectApplier) {
        Label message = createLabel(text, styleName, color, null);
        message.setName(Entities.ERROR_MESSAGE);
        message.setBounds(mouse.x - message.getWidth() / 2f, mouse.y + 10, message.getWidth() / 2f, message.getHeight() / 2f);
        stage.addActor(message);
        effectApplier.apply(message);
    }

    public static void createErrorMessage(Stage stage, Vector2 mouse, String text) {
        createPopupObject(stage, mouse, text, LabelStyles.X20, Color.RED, UIEffects::applyErrorMessageEffect);
    }

    public static TextButton createTextButton(String text, LabelStyles styleName, Consumer<TextButton> action) {
        TextButton button = new TextButton(text, Assets.getSkin("common"), styleName.getName());
        Events.addClickListener(button, () -> action.accept(button));
        return button;
    }

    public static ScrollPane createScrollPane(Actor actor, Consumer<ScrollPane> action) {
        ScrollPane scrollPane = new ScrollPane(actor, Assets.getSkin("common"));
        action.accept(scrollPane);
        return scrollPane;
    }

    public static Container<Label> createLabelContainer(String text, LabelStyles styleName, @Null Color color, @Null Integer align) {
        Label label = createLabel(text, styleName, color, align);
        return new Container<>(label);
    }

    public static Image createImage(String texture) {
        return new Image(Assets.getAtlasTexture(texture));
    }
}
