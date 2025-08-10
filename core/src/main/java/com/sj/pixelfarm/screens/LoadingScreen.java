package com.sj.pixelfarm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pools;
import com.sj.pixelfarm.Main;
import com.sj.pixelfarm.items.Items;
import com.sj.pixelfarm.core.itemgrid.ItemStack;
import com.sj.pixelfarm.items.box.Box;


public class LoadingScreen implements Screen {

    private final Main main;

    private float progress = 0;
    private final float showAfter = 1.5f;
    float timeShown = 0f;

    private final Texture background;
    private final Texture loadingBg;
    private final Texture loadingBarTex;
    private final NinePatch loadingBar;
    private final Texture title;
    private final Texture logo;
    private final Batch batch;

    public LoadingScreen(Main main) {
        this.main = main;
        background = new Texture(Gdx.files.internal("images/loading_screen.png"));
        loadingBg = new Texture(Gdx.files.internal("images/loading_bg.png"));
        loadingBarTex = new Texture(Gdx.files.internal("images/loading_bar.png"));
        loadingBar = new NinePatch(loadingBarTex, 10, 10, 10, 10);
        title = new Texture(Gdx.files.internal("images/title.png"));
        logo = new Texture(Gdx.files.internal("images/logo.png"));
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        if (!main.assets.update()) {
            float midX = Gdx.graphics.getWidth() / 2f - loadingBg.getWidth() / 2f;
            batch.begin();
            batch.draw(background, 0, 0);
            batch.draw(loadingBg, midX, 60);
            loadingBar.draw(batch, midX + 5, 65,
                progress * loadingBg.getWidth() - 10, loadingBg.getHeight() - 10);
            batch.end();

        } else if (main.assets.update() && !main.assets.isDoneLoading()) {
            System.out.println("# Assets initialized");
            main.assets.init();
            Items.load();

            // Init the pools
            Pools.set(ItemStack.class, Pools.get(ItemStack.class));
            Pools.set(Box.class, Pools.get(Box.class));

        } else if (timeShown >= showAfter) {
            main.setScreen(new GameScreen(main));
        }

        timeShown += delta;
        progress = main.assets.getProgress();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        title.dispose();
        background.dispose();
        loadingBg.dispose();
        loadingBarTex.dispose();
        logo.dispose();
        System.out.println("# LoadingScreen disposed!");
    }
}
