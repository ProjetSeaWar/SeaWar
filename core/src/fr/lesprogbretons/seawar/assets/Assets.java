package fr.lesprogbretons.seawar.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    private AssetManager assetManager = new AssetManager();

    private static TextureParameter param = new TextureParameter();

    public static final AssetDescriptor<Texture> menu =
            new AssetDescriptor<>("menuShip.jpg", Texture.class, param);

    public static final AssetDescriptor<TextureAtlas> hexes =
                new AssetDescriptor<>("hexes.atlas", TextureAtlas.class);

    public static final AssetDescriptor<Texture> background =
            new AssetDescriptor<>("back.png", Texture.class, param);

    public static final AssetDescriptor<Skin> skin =
            new AssetDescriptor<>("skin/uiskin.json", Skin.class);

    public static final AssetDescriptor<Music> menuMusic = new AssetDescriptor<>("music/musiquemenu.mp3", Music.class);
    public static final AssetDescriptor<Music> gameMusic = new AssetDescriptor<>("music/grandioso.mp3", Music.class);

    public void load() {
        param.genMipMaps = true; // enabling mipmaps

        assetManager.load(menu);
        assetManager.load(menuMusic);
        assetManager.load(gameMusic);
        assetManager.load(hexes);
        assetManager.load(background);
        assetManager.load(skin);
    }

    public AssetManager getManager() {
        return assetManager;
    }

    public Object get(AssetDescriptor ad) {
        return assetManager.get(ad);
    }

    public void dispose() {
        assetManager.dispose();
    }
}
