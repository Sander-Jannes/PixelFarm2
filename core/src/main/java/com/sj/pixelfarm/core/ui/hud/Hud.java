package com.sj.pixelfarm.core.ui.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.sj.pixelfarm.core.Vars;
import com.sj.pixelfarm.core.card.Card;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.ui.styles.LabelStyles;

import static com.sj.pixelfarm.core.ui.UIUtils.createLabel;


public class Hud extends Card {

    private static final String IMAGE = "overlay/hud";

    private final Label dayLabel;
    private final Label timeLabel;
    private final Label moneyLabel;
    private final Label levelLabel;

    public Hud() {
        super(IMAGE, 0, 0, false);

        dayLabel = createLabel(Vars.clock.getFormatted(), LabelStyles.X20, null, null);
        dayLabel.setPosition(35, 172);
        addActor(dayLabel);

        timeLabel = createLabel(Vars.clock.getTime(), LabelStyles.X18, null, null);
        timeLabel.setPosition(258, 155);
        addActor(timeLabel);

        moneyLabel = createLabel(String.format("%.2f", Vars.state.money), LabelStyles.X18, null, null);
        moneyLabel.setPosition(65, 122);
        addActor(moneyLabel);

        levelLabel = createLabel("Level: " + Vars.state.level, LabelStyles.X20, null, null);
        levelLabel.setPosition(130, 65);
        addActor(levelLabel);

        Events.on(EventType.NewDayEvent.class, e -> updateDayLabel() );
        Events.on(EventType.UpdateClockEvent.class, e -> timeLabel.setText(Vars.clock.getTime()));
    }

    private void updateDayLabel() { dayLabel.setText(Vars.clock.getFormatted()); }

    private void updateMoneyLabel() {
        moneyLabel.setText(String.format("%.2f", Vars.state.money));
    }

    public void updateLevelLabelText() {
        levelLabel.setText("Level: " + Vars.state.level );
    }

    public void update() {
        updateLevelLabelText();
        updateMoneyLabel();
    }
}
