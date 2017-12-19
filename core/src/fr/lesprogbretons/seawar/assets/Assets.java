package fr.lesprogbretons.seawar.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    private AssetManager assetManager = new AssetManager();

    private static TextureParameter param = new TextureParameter();

    public static final AssetDescriptor<Texture> menu =
            new AssetDescriptor<>("menuShip.jpg", Texture.class, param);

    public static final AssetDescriptor<Texture> hexes =
            new AssetDescriptor<>("hexes.png", Texture.class, param);

    public static final AssetDescriptor<Texture> hexes2 =
            new AssetDescriptor<>("hexes2.png", Texture.class, param);

    public static final AssetDescriptor<Skin> menuSkin =
            new AssetDescriptor<>("skin/uiskin.json", Skin.class);

    public void load() {
        param.genMipMaps = true; // enabling mipmaps

        assetManager.load(menu);
        assetManager.load(hexes);
        assetManager.load(hexes2);
        assetManager.load(menuSkin);
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
