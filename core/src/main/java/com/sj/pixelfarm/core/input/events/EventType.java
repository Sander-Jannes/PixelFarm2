package com.sj.pixelfarm.core.input.events;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.sj.pixelfarm.core.input.Interactions;
import com.sj.pixelfarm.core.itemgrid.ItemGrid;
import com.sj.pixelfarm.core.itemgrid.ItemStack;
import com.sj.pixelfarm.core.itemgrid.ItemStackSlot;
import com.sj.pixelfarm.core.ui.actionbar.ActionBar;
import com.sj.pixelfarm.core.ui.effects.UIEffect;


public class EventType {

    public record TransferSlotEvent(ItemGrid source, Vector2 pos) { }

    public record PutItemInGridEvent(ItemStack stack, String name, Runnable onSuccess) { }

    public record RemoveActionBar(Vector2 pos) { }

    public record HideCurrentModalEvent() { }

    public record DropItemOnWorld(ItemStackSlot itemStackSlot, Interactions interaction) { }

    public record ShowPopupObject(TextureRegion image, String text, UIEffect effectApplier) { }

    public record ShowCropInfoPopupEvent(TiledMapTile tile) { }

    public record HideCropInfoPopupEvent() { }

    public record ShowActionBarEvent(ActionBar bar) { }
}
