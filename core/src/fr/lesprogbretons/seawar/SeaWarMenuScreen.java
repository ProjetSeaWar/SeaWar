package fr.lesprogbretons.seawar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import fr.lesprogbretons.seawar.asset.Assets;

/**
 * Ici ça sera l'écran du jeu
 */
public class SeaWarMenuScreen extends ScreenAdapter {
    private final SeaWar game = (SeaWar) Gdx.app.getApplicationListener();
    private final Assets assets = game.getAssets();
    private final Batch batch = game.getBatch();

    private Texture splash;

    @Override
    public void show() {
        super.show();
        splash = (Texture) assets.get(Assets.splash);
        splash.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.MipMap);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        //Pluisieurs updates nécéssaires ici
        //Mise à jour de la caméa
        //TODO Changer la manière d'implémenter le viewport

        batch.begin();
        batch.draw(splash, 0, 0);
//        game.getFont().draw(batch, "Coucou", 100, 150);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        splash.dispose();
    }
}
