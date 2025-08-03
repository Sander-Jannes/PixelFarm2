package com.sj.pixelfarm.core.card;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.ui.styles.LabelStyles;
import com.sj.pixelfarm.core.utils.Logic;
import com.sj.pixelfarm.core.utils.TileHelper;

import static com.sj.pixelfarm.core.ui.UIUtils.createLabel;
import static com.sj.pixelfarm.core.ui.UIUtils.createMeter;


public class CropInfoPopup extends TimerPopup<TiledMapTile> {

    private static final String IMAGE = "popup/popup2";
    private static final String QUALITY_METER = "popup/quality_meter";
    private static final String WATER_METER = "popup/water_meter";
    private static final String FERTILIZER_METER = "popup/fertilizer_meter";
    private static final String POINTER = "popup/pointer";

    private final Label statusLabel;
    private final Label titleLabel;

    private final String[] statuses = new String[] {
            "[###++++++++++++]",
            "[######++++++++]",
            "[##########++]",
            "Ready to harvest"
    };

    public CropInfoPopup(float x, float y, long timer) {
        super(IMAGE, x, y, false, timer);
        setName(Entities.POPUP);

        Table table = new Table();
        table.top();
        stack.add(table);

        VerticalGroup panel = new VerticalGroup();
        panel.space(30);

        VerticalGroup header = new VerticalGroup();
        header.space(10).padTop(20);
        titleLabel = createLabel("", LabelStyles.X26, null, null);
        statusLabel = createLabel("", LabelStyles.X16, null, null);

        header.addActor(titleLabel);
        header.addActor(statusLabel);

        panel.addActor(header);

        VerticalGroup body = new VerticalGroup();
        body.space(20);

        body.addActor(createMeter(QUALITY_METER, POINTER, "quality"));
        body.addActor(createMeter(WATER_METER, POINTER, "water"));
        body.addActor(createMeter(FERTILIZER_METER, POINTER, "fertilizer"));

        panel.addActor(body);
        table.add(panel);

        Events.on(EventType.ShowCropInfoPopupEvent.class, e -> show(e.tile()));
        Events.on(EventType.HideCropInfoPopupEvent.class, e -> hide());
    }

    @Override
    protected void show(TiledMapTile tile) {
        super.show(tile);

        TileHelper.processTile(tile, t -> {
            Item item = t.getProperty("item", Item.class);
            titleLabel.setText(item.itemType.getName());
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        TileHelper.processTile(obj, t -> {
            String message = statuses[t.getProperty("level", Integer.class) - 1];
            statusLabel.setText(message);

            findActor("quality").setX(Logic.itemRangeToPopupRange(t.getProperty("quality", Float.class)));
            findActor("water").setX(Logic.itemRangeToPopupRange(t.getProperty("water", Float.class)));
            findActor("fertilizer").setX(Logic.itemRangeToPopupRange(t.getProperty("fertilizer", Float.class)));
        });
    }
}
