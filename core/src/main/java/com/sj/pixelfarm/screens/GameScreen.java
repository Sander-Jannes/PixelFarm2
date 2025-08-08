package com.sj.pixelfarm.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.sj.pixelfarm.*;
import com.sj.pixelfarm.core.Entities;
import com.sj.pixelfarm.core.Vars;
import com.sj.pixelfarm.core.card.CropInfoPopup;
import com.sj.pixelfarm.core.grid.GridLoader;
import com.sj.pixelfarm.core.input.listeners.WorldInputListener;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.item.Items;
import com.sj.pixelfarm.core.itemgrid.ItemGrid;
import com.sj.pixelfarm.core.mem.Assets;
import com.sj.pixelfarm.core.mem.PoolManager;
import com.sj.pixelfarm.core.ui.hud.OverlayMenu;
import com.sj.pixelfarm.core.ui.UIEventProcessor;
import com.sj.pixelfarm.tasks.TaskManager;
import com.sj.pixelfarm.world.World;


public class GameScreen implements Screen {

    private final Stage stage;
    private final World world;
    private final WorldInputListener worldInputListener;

    public GameScreen(Main main) {
        OrthographicCamera camera = new OrthographicCamera();
        ExtendViewport viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport);

        OrthographicCamera worldCamera = new OrthographicCamera();
        worldCamera.position.set(Settings.WORLD_OFFSET_X, Settings.WORLD_OFFSET_Y, 0);
        ExtendViewport worldViewport = new ExtendViewport(10, 10, worldCamera);

        world = new World(worldViewport);
        worldInputListener = new WorldInputListener(world, stage);

        ItemGrid grid = new ItemGrid(GridLoader.load("gridconfig/hotbar.json", Assets::getAtlasTexture), 64, Entities.HOTBAR);
        grid.setPosition(Gdx.graphics.getWidth() / 2f - grid.getWidth() / 2f,40);

        grid.setObjToSlot(1, PoolManager.obtain(Items.cucumber, 4, Item.Quality.GOOD));
        grid.setObjToSlot(2, PoolManager.obtain(Items.eggplant_seeds, 64, Item.Quality.NONE));
        grid.setObjToSlot(3, PoolManager.obtain(Items.onion_seeds, 64, Item.Quality.NONE));
        grid.setObjToSlot(4, PoolManager.obtain(Items.cucumber_seeds, 64, Item.Quality.NONE));
        grid.setObjToSlot(5, PoolManager.obtain(Items.broccoli_seeds, 64, Item.Quality.NONE));
        grid.setObjToSlot(6, PoolManager.obtain(Items.scythe, 1, Item.Quality.NONE));

        CropInfoPopup popup = new CropInfoPopup(20, Gdx.graphics.getHeight() - 255 - 20, 200);
        OverlayMenu overlayMenu = new OverlayMenu();

        stage.addActor(grid);
        stage.addActor(popup);
        stage.addActor(overlayMenu);
        main.multiplexer.addProcessor(stage);
        main.multiplexer.addProcessor(worldInputListener);

        UIEventProcessor.init(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        if (! world.editMode.isActive()) {
            Vars.clock.update(delta);
        }

        worldInputListener.update();
        world.draw(delta);
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void show() {
        TaskManager.initCropGrowTask(world);
    }

    @Override
    public void resize(int width, int height) {
        if (width == 0 || height == 0) return;
        world.viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        TaskManager.stopTasks();
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        System.out.println("# GameScreen disposed!");
    }
}
