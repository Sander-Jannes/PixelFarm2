package com.sj.pixelfarm;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.sj.pixelfarm.input.listeners.DesktopInputListener;
import com.sj.pixelfarm.mem.Assets;
import com.sj.pixelfarm.screens.LoadingScreen;


/** {@link ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public Assets assets;
    public InputMultiplexer multiplexer = new InputMultiplexer();

    @Override
    public void create() {
        assets = new Assets();
        multiplexer.addProcessor(new DesktopInputListener());
        Gdx.input.setInputProcessor(multiplexer);
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        assets.dispose();
        System.out.println("# Assets disposed!");
    }
}
