package com.sj.pixelfarm.input.events;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.sj.pixelfarm.input.Interactions;
import com.sj.pixelfarm.itemgrid.ItemGrid;
import com.sj.pixelfarm.itemgrid.ItemStack;
import com.sj.pixelfarm.itemgrid.ItemStackSlot;
import com.sj.pixelfarm.order.Order;
import com.sj.pixelfarm.ui.elements.actionbar.ActionBar;
import com.sj.pixelfarm.core.ui.effects.UIEffect;


public class EventType {

    public record NewDayEvent() { }

    public record UpdateOverlayEvent() { }

    public record UpdateClockEvent() { }

    public record TransferSlotEvent(ItemGrid source, Vector2 pos) { }

    public record PutItemInGridEvent(ItemStack stack, String name, Runnable onSuccess) { }

    public record RemoveActionBar(Vector2 pos) { }

    public record DropItemOnWorld(ItemStackSlot itemStackSlot, Interactions interaction) { }

    public record ShowPopupObject(TextureRegion image, String text, UIEffect effectApplier) { }

    public record ShowCropInfoPopupEvent(TiledMapTile tile) { }

    public record HideCropInfoPopupEvent() { }

    public record ShowActionBarEvent(ActionBar bar, Vector2 pos) { }

    public record ToggleEditMode() { }

    public record ShowErrorMessage(String text) { }

    public record StartCar() { }

    public record UnlockCardEvent() { }

    public record HideCurrentModalEvent() { }

    public record ShowModalEvent(String name) { }

    public record ShowOrderEvent(Order order, Vector2 pos) { }

    public record RemoveOrderEvent() { }
}
