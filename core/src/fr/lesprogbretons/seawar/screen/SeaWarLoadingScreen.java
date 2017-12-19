package fr.lesprogbretons.seawar.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.lesprogbretons.seawar.SeaWar;
import fr.lesprogbretons.seawar.utils.Utils;

//Utilisé pour ne pas avoir à réécrire SeaWar. devant chaque
import static fr.lesprogbretons.seawar.SeaWar.*;


/**
 * Classe qui permet de charger les ressources nécessaires au jeu
 * <p>
 * Inspiré par PixelScientists
 */
public class SeaWarLoadingScreen extends ScreenAdapter {
    private static final float PROGRESS_BAR_WIDTH = SeaWar.WORLD_WIDTH - 10f;
    private static final float PROGRESS_BAR_HEIGHT = 20f;

    private float minimumShowTime = 1.0f;

    //Image to show on the splash
    private Texture splash;

    //Viewing tools
    private OrthographicCamera camera;

    private Viewport viewport;


    @Override
    public void show() {
        splash = new Texture(Gdx.files.internal("splash.png"), true);
        camera = new OrthographicCamera();
        viewport = new FitViewport(splash.getWidth(), splash.getHeight(), camera);
        splash.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.MipMap);

        assets.load();
    }

    @Override
    public void resume() {
        assets.getManager().finishLoading();
    }

    @Override
    public void render(float deltaTime) {
        if (assets.getManager().update() && minimumShowTime <= 0) {
            SeaWar.game.setScreen(new SeaWarMenuScreen());
            Gdx.app.log("APP", "MenuScreen Set");
            dispose();
        }

        Utils.clearScreen();

        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        spriteBatch.draw(splash, 0, 0);
        spriteBatch.end();

        renderProgressBar();

        minimumShowTime -= deltaTime;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    private void renderProgressBar() {
        float progress = assets.getManager().getProgress();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(5f, 5f,
                PROGRESS_BAR_WIDTH * progress,
                PROGRESS_BAR_HEIGHT
        );
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        Gdx.app.log("APP", "Disposed image screen.png");
        splash.dispose();
    }
}
