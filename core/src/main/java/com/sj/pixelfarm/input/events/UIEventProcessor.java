package com.sj.pixelfarm.input.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.sj.pixelfarm.Entities;
import com.sj.pixelfarm.core.Events;
import com.sj.pixelfarm.itemgrid.ItemStack;
import com.sj.pixelfarm.core.ui.UIUtils;
import com.sj.pixelfarm.core.ui.effects.UIEffects;
import com.sj.pixelfarm.ui.styles.LabelStyles;
import com.sj.pixelfarm.world.World;

import static com.sj.pixelfarm.core.ui.UIUtils.*;


public class UIEventProcessor {

    private static final Vector2 tmpVec = new Vector2();

    public static void init(Stage stage, World world) {

        Events.on(EventType.ShowPopupObject.class, e -> {
            tmpVec.set(Gdx.input.getX(), Gdx.input.getY() - 20);
            stage.screenToStageCoordinates(tmpVec);
            UIUtils.createPopupObject(stage, tmpVec, e.image(), e.text(), e.effectApplier());
        });

        Events.on(EventType.ShowActionBarEvent.class, e -> {
            e.bar().setPosition(e.pos().x, e.pos().y + 50);
            stage.addActor(e.bar());
            UIEffects.applyBounceEffect(e.bar());
        });

        Events.on(EventType.ShowErrorMessage.class, e -> {
            // Om ervoor te zorgen dat de error message niet gespamd wordt
            if (stage.getRoot().findActor(Entities.ERROR_MESSAGE) != null) return;

            tmpVec.set(Gdx.input.getX(), Gdx.input.getY() - 20);
            stage.screenToStageCoordinates(tmpVec);
            UIUtils.createErrorMessage(stage, tmpVec, e.text());
        });

//        Events.on(EventType.ShowOrderEvent.class, e -> {
//            world.viewport.project(e.pos());
//            stage.screenToStageCoordinates(e.pos());
//
//            Stack stack = new Stack();
//            stack.setName(Entities.ORDER);
//            stack.setPosition(e.pos().x, Gdx.graphics.getHeight() - e.pos().y + 50);
//            UIEffects.applySinusBounce(stack, 12f, 2f);
//
//            stage.getRoot().addActorAt(0, stack);
//
//            stack.add(new Container<>(createImage("box_info/order_popup")));
//
//            HorizontalGroup body = new HorizontalGroup().center().space(-22).padBottom(20);
//
//            for (ItemStack itemStack : e.order().getItems()) {
//                VerticalGroup group = new VerticalGroup().space(-20);
//
//                group.addActor(createItemPreview(itemStack.item, -0.4f));
//                Container<Label> quantityContainer = createLabelContainer(itemStack.amount + "x", LabelStyles.X13, null, null);
//
//                group.addActor(quantityContainer);
//                body.addActor(group);
//            }
//
//            stack.add(body);
//        });

//        Events.on(EventType.RemoveOrderEvent.class, e -> {
//            stage.getRoot().findActor(Entities.ORDER).remove();
//        });
    }
}
