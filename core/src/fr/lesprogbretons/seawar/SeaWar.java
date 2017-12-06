package fr.lesprogbretons.seawar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.lesprogbretons.seawar.asset.Assets;

//TODO Mettre en place un AssetManager qui load toutes les textures avec un splash screen puis go menu

/**
 * Classe qui s'occupe de dire quel est l'affichage nécessaire
 * et de mettre à jour le modèle
 */
public class SeaWar extends Game {
    public static final int WORLD_WIDTH = 800, WORLD_HEIGHT = 480;

    private final Assets assets = new Assets();

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Viewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer = new ShapeRenderer();
        setScreen(new SeaWarLoadingScreen());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void render() {
        clearScreen();
        super.render();
    }

    public Batch getBatch() {
        return batch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public Assets getAssets() {
        return assets;
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        super.dispose();
        assets.dispose();
    }
}
