package fr.lesprogbretons.seawar.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.lesprogbretons.seawar.assets.Assets;
import fr.lesprogbretons.seawar.utils.Utils;

import static fr.lesprogbretons.seawar.SeaWar.*;

/**
 * Classe qui permet d'afficher un menu
 * <p>
 * Inspiré par PixelScientists
 */
public class SeaWarMenuScreen extends ScreenAdapter {

    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Sprite menu;

    @Override
    public void show() {
        Skin skin = (Skin) assets.get(Assets.skin);

        menu = new Sprite((Texture) assets.get(Assets.menu));
        menu.setPosition(-menu.getWidth() / 2, -menu.getHeight() / 2);

        camera = new OrthographicCamera();
        viewport = new FitViewport(menu.getWidth(), menu.getHeight(), camera);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton playButton = new TextButton("Jouer", skin, "default");
        playButton.setWidth(150);
        playButton.setHeight(50);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SeaWarMapScreen());
            }
        });

        TextButton editeurButton = new TextButton("Editeur", skin, "default");
        playButton.setWidth(150);
        playButton.setHeight(50);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SeaWarMapScreen());
            }
        });


        TextButton quitButton = new TextButton("Quitter", skin, "default");
        quitButton.setWidth(150);
        quitButton.setHeight(50);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Gdx.app.exit();
            }
        });

        TextButton loadButton = new TextButton("Charger", skin, "default");
        loadButton.setWidth(150);
        loadButton.setHeight(50);
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean turnOver = seaWarController.endTurn();
                if (!turnOver) {
                    Dialog d = new Dialog("Turn isn't over", skin, "dialog")
                            .text("One of your ship haven't moved")
                            .button("Okay", true)
                            .key(Input.Keys.ENTER, true)
                            .show(stage);
                }
            }
        });

        table.add(playButton).width(playButton.getWidth())
                .height(playButton.getHeight()).padTop(375).padBottom(10).padLeft(10);
        table.add(editeurButton).width(playButton.getWidth())
                .height(playButton.getHeight()).padTop(375).padBottom(10).padLeft(10);
        table.add(loadButton).width(loadButton.getWidth())
                .height(loadButton.getHeight()).padTop(375).padBottom(10).padLeft(10);
        table.add(quitButton).width(quitButton.getWidth())
                .height(quitButton.getHeight()).padTop(375).padBottom(10).padLeft(10);


        table.row();
        table.left();//.bottom();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void render(float delta) {
        Utils.clearScreen();
        spriteBatch.setProjectionMatrix(camera.combined);

        stage.act(delta);

        spriteBatch.begin();
        menu.draw(spriteBatch);
        spriteBatch.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
