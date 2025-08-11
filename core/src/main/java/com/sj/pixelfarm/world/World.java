package com.sj.pixelfarm.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.sj.pixelfarm.Entities;
import com.sj.pixelfarm.Settings;
import com.sj.pixelfarm.Vars;
import com.sj.pixelfarm.input.Interactions;
import com.sj.pixelfarm.input.events.EventType;
import com.sj.pixelfarm.items.Item;
import com.sj.pixelfarm.items.actions.ActionInfo;
import com.sj.pixelfarm.items.actions.ActionProps;
import com.sj.pixelfarm.items.actions.ActionTarget;
import com.sj.pixelfarm.itemgrid.ItemStack;
import com.sj.pixelfarm.itemgrid.ItemStackSlot;
import com.sj.pixelfarm.mem.Assets;
import com.sj.pixelfarm.mem.PoolManager;
import com.sj.pixelfarm.items.box.Box;
import com.sj.pixelfarm.utils.TileHelper;
import com.sj.pixelfarm.world.entities.Car;
import com.sj.pixelfarm.world.map.WorldMap;
import com.sj.pixelfarm.world.modes.EditMode;
import com.sj.pixelfarm.world.types.FieldGroup;
import com.sj.pixelfarm.world.utils.WorldUtils;

import com.sj.pixelfarm.core.Events;
import com.sj.pixelfarm.core.ui.effects.UIEffects;


public class World implements Disposable {

    /** Tile scale in pixels */
    public static final float SCALE = 128f;
    /** Inverse scale */
    private static final float INV_SCALE = 1.f/SCALE;
    /** The amount of zoom per scroll */
    private static final float ZOOM = 0.1f;
    /** How far you can zoom out */
    private static final float MAX_ZOOM = 100f;
    /** How far you can zoom in, 0 is close to the map  */
    private static final float MIN_ZOOM = 0.5f;
    private static final int[] ground = new int[] { Layers.GROUND };
    private static final int[] animation = new int[] { Layers.ANIMATION };
    private static final int[] edit = new int[] { Layers.EDIT};
    private static final int[] decoration = new int[] { Layers.DECORATION };
    private static final TextureRegion cursorImage = Assets.getAtlasTexture("world/cursor");

    private static final Vector2 tmpVec = new Vector2();

    private final Car car;

    private final IsometricTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    private final Vector2 cursorBlitPos = new Vector2();

    public final ExtendViewport viewport;
    public final WorldMap worldMap = new WorldMap();

    public float lastX, lastY;
    public boolean dragging;

    public EditMode editMode;

    public World(ExtendViewport vp) {
        viewport = vp;
        camera = (OrthographicCamera) viewport.getCamera();
        renderer = new IsometricTiledMapRenderer(worldMap.getMap(), INV_SCALE);

        editMode = new EditMode(this, worldMap.fields);

        MapObjects objects = worldMap.getLayer(World.Layers.CAR, MapLayer.class).getObjects();

        car = new Car(new GridPoint2[] {
            WorldUtils.getRectanglePos(((RectangleMapObject) objects.get("start")).getRectangle()),
            WorldUtils.getRectanglePos(((RectangleMapObject) objects.get("end")).getRectangle()),
            WorldUtils.getRectanglePos(((RectangleMapObject) objects.get("pickup")).getRectangle())
        });

        car.start();

        Events.on(EventType.DropItemOnWorld.class, e -> dropItem(e.itemStackSlot(), e.interaction()));
        Events.on(EventType.ToggleEditMode.class, e -> editMode.toggle());
        Events.on(EventType.StartCar.class, e -> car.start());
    }

    public void draw(float delta) {
        camera.update();
        renderer.setView(camera);

        renderer.render(ground);
        renderer.render(animation);

        if (editMode.isActive()) {
            renderer.render(edit);
            editMode.draw();

        } else {
            renderCursor();

            car.drive(delta);
        }

        renderer.render(decoration);
        car.draw(renderer.getBatch());
    }

    private void renderCursor() {
        GridPoint2 gridPosition = WorldUtils.getGridPosFromMouse(viewport);
        cursorBlitPos.set(WorldUtils.gridToVec(gridPosition));
        TiledMapTile selectedCell = worldMap.getTile(gridPosition, Layers.GROUND);

        TileHelper.processTile(selectedCell, tile -> {
            boolean selectable = tile.getProperty("selectable", Boolean.class);
            if (selectable) {
                renderer.getBatch().begin();
                renderer.getBatch().draw(cursorImage, cursorBlitPos.x, cursorBlitPos.y, 1f, 1f);
                renderer.getBatch().end();
            }
        });
    }

    public void dropItem(ItemStackSlot itemStackSlot, Interactions interaction) {
        if (itemStackSlot.isEmpty()) return;

        GridPoint2 pos = WorldUtils.getGridPosFromMouse(viewport);
        ItemStack itemStack = itemStackSlot.getObj();
        ActionInfo actionInfo = itemStack.item.interactionMap.get(interaction);

        if (actionInfo == null) return;

        ActionTarget target = actionInfo.target();
        TiledMapTile tile = worldMap.getTile(pos, target.layer);

        TileHelper.processTile(tile, t -> {
            if (t.evaluateActionTarget(target)) {
                switch (actionInfo.action()) {
                    case HARVEST: {
                        if (t.isHarvestable()) {
                            Item harvestItem = t.getProperty("item", Item.class);
                            int amount = t.getProperty("props", ActionProps.Plant.class).harvestQuantity();

                            Events.fire(new EventType.PutItemInGridEvent(
                                PoolManager.obtain(harvestItem, amount, t.getItemQuality()),
                                Entities.HOTBAR,
                                () -> {
                                    worldMap.removeCell(pos, target.layer);
                                    Events.fire(new EventType.ShowPopupObject(harvestItem.image, "+" + amount, UIEffects::applyFadeOutEffect));
                                }));
                        }
                        break;
                    }

                    case PLANT: {
                        if (worldMap.getTile(pos, Layers.DECORATION) != null) return;

                        for (FieldGroup fieldGroup : editMode.fields) {
                            if (fieldGroup.contains(getMouse())) {
                                if (fieldGroup.isUnlocked) {
                                    ActionProps.Plant props = (ActionProps.Plant) actionInfo.props();
                                    TiledMapTileLayer.Cell cell = worldMap.setCell(pos, Layers.DECORATION, props.crop());
                                    TileHelper.processTile(cell.getTile(), crop -> crop.init(props));
                                    Events.fire(new EventType.ShowPopupObject(itemStack.item.image, "-1", UIEffects::applyFadeDownEffect));
                                    itemStack.addAmount(-1);

                                } else {
                                    Events.fire(new EventType.ShowErrorMessage("Field is not unlocked!"));
                                }
                            }
                        }
                        break;
                    }

                    case SELL: {
                        if (car.getPosition().equals(pos)) {
                            Box box = (Box) itemStack;

                            if (car.acceptOrder(box)) {
                                itemStackSlot.destroyObj(true);
                                itemStackSlot.setObj(PoolManager.obtainBox());

                                Vars.state.money += car.order.value + (car.order.value * box.getMultiplier());
                                Vars.state.xp += car.order.xp * itemStack.amount;
                                Events.fire(new EventType.UpdateOverlayEvent());
                            }
                        }
                        break;
                    }

                    case USE:
                        if (worldMap.getTile(pos,Layers.ANIMATION) == null) {
                            ActionProps.Use props = (ActionProps.Use) actionInfo.props();
                            t.updateValueByN(props.target(), props.amount(), 0, 100);
                            worldMap.setAnimatedCell(pos, Layers.ANIMATION, props.animation());
                            itemStack.addAmount(-1);

                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    worldMap.removeCell(pos, Layers.ANIMATION);
                                }
                            }, 2f);
                        }
                        break;
                }
            }
        });
    }

    public Vector2 getMouse() {
        tmpVec.set(Gdx.input.getX(), Gdx.input.getY());
        return viewport.unproject(tmpVec);
    }

    public void interactLeftDrag(Vector2 pos) {
        float toleranceX = Settings.WORLD_OFFSET_X * Settings.MOVE_MARGIN_X;
        float toleranceY = Settings.WORLD_OFFSET_Y * Settings.MOVE_MARGIN_Y;
        float lowerX = Settings.WORLD_OFFSET_X - toleranceX;
        float upperX = Settings.WORLD_OFFSET_X + toleranceX;
        float lowerY = Settings.WORLD_OFFSET_Y - toleranceY;
        float upperY = Settings.WORLD_OFFSET_Y + toleranceY;
        float deltaX = (pos.x - lastX) / SCALE;
        float deltaY = (pos.y - lastY) / SCALE;

        if (dragging
                && camera.position.x >= lowerX + deltaX && camera.position.x <= upperX + deltaX
                && camera.position.y >= lowerY + deltaY && camera.position.y <= upperY + deltaY) {

            camera.translate(-deltaX * camera.zoom, deltaY * camera.zoom);
            camera.update();
            lastX = pos.x;
            lastY = pos.y;
        }
    }

    public boolean interactScroll(float amountY) {
        camera.zoom = MathUtils.clamp(camera.zoom + amountY * ZOOM, MIN_ZOOM, MAX_ZOOM);
        return true;
    }

    @Override
    public void dispose() {
        worldMap.dispose();
        renderer.dispose();
    }

    public static class Layers {
        public static final int GROUND = 0;
        public static final int ANIMATION = 1;
        public static final int EDIT = 2;
        public static final int DECORATION = 3;
        public static final int BUY = 4;
        public static final int CAR = 5;
    }
}
