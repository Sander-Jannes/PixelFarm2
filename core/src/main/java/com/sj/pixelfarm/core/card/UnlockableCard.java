package com.sj.pixelfarm.core.card;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.sj.pixelfarm.core.Vars;
import com.sj.pixelfarm.core.ui.styles.LabelStyles;

import static com.sj.pixelfarm.core.ui.UIUtils.createImage;
import static com.sj.pixelfarm.core.ui.UIUtils.createLabelContainer;


public class UnlockableCard extends FlipCard {

    private static final String LOCK_TEXTURE = "ui/icons/lock";
    private final Container<Stack> lockContainer = new Container<>();
    private boolean isLocked = true;
    private final int level;

    public UnlockableCard(String imagePath, float x, float y, boolean isMovable, int level) {
        super(imagePath, x, y, isMovable);
        this.level = level;

        Stack lockStack = new Stack();
        lockStack.addActor(createImage(LOCK_TEXTURE));

        Container<Label> levelContainer = createLabelContainer(level + "", LabelStyles.X14, null, null).bottom().padBottom(4);
        lockStack.addActor(levelContainer);

        lockContainer.setActor(lockStack);
        lockContainer.top().right().padRight(-15).padTop(-20);
        stack.add(lockContainer);
    }

    protected void unlock(Runnable action) {
        if (!isLocked) return;

        isLocked = level > Vars.state.level;
        if (!isLocked) {
            lockContainer.setVisible(false);
            action.run();
        }
    }

    protected boolean isLocked() { return isLocked; }
}
