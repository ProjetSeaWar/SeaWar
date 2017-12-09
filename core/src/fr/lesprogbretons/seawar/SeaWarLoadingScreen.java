package fr.lesprogbretons.seawar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fr.lesprogbretons.seawar.asset.Assets;


/**
 * Classe qui se contente d'afficher une progress bar
 */
public class SeaWarLoadingScreen extends ScreenAdapter {
    private static final float PROGRESS_BAR_WIDTH = SeaWar.WORLD_WIDTH - 10f;
    private static final float PROGRESS_BAR_HEIGHT = 20f;

    private final SeaWar game = (SeaWar) Gdx.app.getApplicationListener();
    private final Assets assets = game.getAssets();
    private final Batch batch = game.getBatch();
    //Image to show on the splash
    private final Texture splash = new Texture(Gdx.files.internal("splash.png"), true);
    private final ShapeRenderer shapeRenderer = game.getShapeRenderer();

    @Override
    public void show() {
        super.show();
        splash.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.MipMap);
        assets.load();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(splash, 0, 0);
        batch.end();
        renderProgressBar();

        if (assets.getManager().update()) {
            // Comment this out if you just want to see the progress bar. As this can be quite quick on desktop.
//            game.setScreen(new SeaWarMenuScreen());
            dispose();
        }
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
        super.dispose();
        splash.dispose();
    }
}
