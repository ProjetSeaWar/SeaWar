package fr.lesprogbretons.seawar.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    private AssetManager assetManager = new AssetManager();

    private static TextureParameter param = new TextureParameter();

    public static final AssetDescriptor<Texture> splash =
            new AssetDescriptor<>("splash.png", Texture.class, param);

    public void load() {
        param.genMipMaps = true; // enabling mipmaps

        assetManager.load(splash);
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
