package com.sj.pixelfarm.core.card;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import com.sj.pixelfarm.core.ui.styles.ButtonStyles;

import java.util.function.Consumer;

import static com.sj.pixelfarm.core.ui.UIUtils.createImageButton;


public class FlipCard extends Card {

    protected final Stack front = new Stack();
    protected final Stack back = new Stack();

    public FlipCard(String imagePath, float x, float y, boolean isMovable) {
        super(imagePath, x, y, isMovable);

        back.setVisible(false);
        stack.add(front);
        stack.add(back);

        ImageButton flipButton = createImageButton(ButtonStyles.FLIP_BUTTON, button -> flip(f -> flipSide()));
        Container<ImageButton> flip = new Container<>(flipButton);
        flip.left().top().padTop(7).padLeft(8);
        stack.add(flip);
    }

    private void flipSide() {
        boolean showingFront = front.isVisible();
        front.setVisible(!showingFront);
        back.setVisible(showingFront);
    }

    private void flip(Consumer<Card> onFlipped) {
        setTransform(true);
        setOrigin(Align.center);

        addAction(Actions.sequence(
                Actions.scaleTo(0f, 1f, 0.15f),
                Actions.run(() -> onFlipped.accept(this)),
                Actions.scaleTo(1f, 1f, 0.15f)
        ));
    }
}
