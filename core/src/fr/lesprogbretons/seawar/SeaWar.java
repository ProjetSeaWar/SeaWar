package fr.lesprogbretons.seawar;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import fr.lesprogbretons.seawar.assets.Assets;
import fr.lesprogbretons.seawar.model.Partie;
import fr.lesprogbretons.seawar.screen.SeaWarLoadingScreen;

/**
 * Classe principale du programme qui permet un accès aux classes
 * <p>
 * Architecture générale permise par PixelScientists
 */
public class SeaWar implements ApplicationListener {

    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 480;

    /**
     * {@link Logger} utilisé pour logger dans tout le jeu
     */
    public static final Logger logger = new Logger("SeaWar");

    /**
     * {@link Game} est publique et permet de changer les {@link Screen}
     */
    public static final Game game = new Game() {
        @Override
        public void create() {
            setScreen(new SeaWarLoadingScreen());
        }
    };

    /**
     * La classe {@link Assets} qui est la seule responsable des ressources
     */
    public static final Assets assets = new Assets();

    /**
     * Le {@link SpriteBatch} qui permet de rendre les textures du jeu
     */
    public static SpriteBatch spriteBatch;

    /**
     * Le {@link ShapeRenderer} qui permet de rendre les formes géométriques du jeu
     */
    public static ShapeRenderer shapeRenderer;

    /**
     * La {@link Partie} qui contient toutes les données du jeu
     */
    public static Partie partie;

    @Override
    public void create() {
        try {
            logger.setLevel(Logger.DEBUG);
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
            Texture.setAssetManager(assets.getManager());
            spriteBatch = new SpriteBatch();
            shapeRenderer = new ShapeRenderer();
            partie = new Partie();
            game.create();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void resume() {
        try {
            game.resume();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void render() {
        try {
            game.render();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        try {
            game.resize(width, height);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void pause() {
        try {
            game.pause();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        game.dispose();
        assets.dispose();

        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}
