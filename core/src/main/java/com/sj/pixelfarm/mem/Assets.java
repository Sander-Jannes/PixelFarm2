package com.sj.pixelfarm.mem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.*;
import com.sj.pixelfarm.Settings;


public final class Assets implements Disposable {

    private final AssetManager assetManager = new AssetManager();

    private boolean doneLoading = false;

    private static final ObjectMap<String, Skin> skins = new ObjectMap<>();
    private static final ObjectMap<String, TextureRegion> textureMap = new ObjectMap<>();
    private static final OrderedMap<Integer, TextureRegion> itemMap = new OrderedMap<>();
    public static OrderedMap<Integer, TextureRegion> qualities = new OrderedMap<>();

    private static final String atlasPath = "images/assets.atlas";

    public Assets() {
        assetManager.load(atlasPath, TextureAtlas.class);

        JsonReader json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.internal(Settings.TILE_SETS_PATH));

        for (String item : base.get("textures").asStringArray()) {
            assetManager.load(item, Texture.class);
        }
    }

    public static TextureRegion getAtlasTexture(String name) {
        return textureMap.get(name);
    }

    public static Skin getSkin(String name) {
        return skins.get(name);
    }

    public static TextureRegion getItemImage(int id) {
        return itemMap.get(id);
    }

    public void init() {
        doneLoading = true;

        TextureAtlas atlas = assetManager.get(atlasPath, TextureAtlas.class);

        for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
            textureMap.put(region.name, region);
        }

        qualities = loadTileSet("images/tile_sets/ui/qualities.png", 32, 32, 0);
        ObjectMap<Integer, TextureRegion> items1Map = loadTileSet("images/tile_sets/items/items1all.png", 64, 64, 0);
        ObjectMap<Integer, TextureRegion> items2Map = loadTileSet("images/tile_sets/items/items2.png", 64, 32, items1Map.size);

        itemMap.putAll(items1Map);
        itemMap.putAll(items2Map);

        Skin commonSkin = new Skin(Gdx.files.internal("skins/common.json"), atlas);
        skins.put("common", commonSkin);
    }

    private OrderedMap<Integer, TextureRegion> loadTileSet(String name, int tileWidth, int tileHeight, int startIndex) {
        TextureRegion tileSet =  new TextureRegion(assetManager.get(name, Texture.class));
        OrderedMap<Integer, TextureRegion> tileImages = new OrderedMap<>();

        for(int y = 0; y < tileSet.getRegionHeight(); y += tileHeight) {
            for(int x = 0; x < tileSet.getRegionWidth(); x += tileWidth) {
                TextureRegion region = new TextureRegion(tileSet, x, y, tileWidth, tileHeight);
                tileImages.put(startIndex, region);
                startIndex++;
            }
        }

        return tileImages;
    }

    public boolean update() {
        return assetManager.update();
    }

    public boolean isDoneLoading() {
        return doneLoading;
    }

    public float getProgress() {
        return assetManager.getProgress();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        for (Skin skin : skins.values()) {
            skin.dispose();
        }
    }
}
