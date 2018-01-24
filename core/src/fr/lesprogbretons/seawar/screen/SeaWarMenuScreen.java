package fr.lesprogbretons.seawar.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
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
import fr.lesprogbretons.seawar.ia.IAAleatoire;
import fr.lesprogbretons.seawar.model.Partie;
import fr.lesprogbretons.seawar.model.map.Grille;
import fr.lesprogbretons.seawar.screen.manager.EditeurMapManager;
import fr.lesprogbretons.seawar.screen.manager.GameMapManager;
import fr.lesprogbretons.seawar.utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

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
    private Music music;

    public SeaWarMenuScreen() {
        music = (Music) assets.get(Assets.menuMusic);
        music.setLooping(true);
    }

    @Override
    public void show() {
        Gdx.graphics.setWindowedMode(800, 480);

        Skin skin = (Skin) assets.get(Assets.skin);

        music.play();

        menu = new Sprite((Texture) assets.get(Assets.menu));
        menu.setPosition(-menu.getWidth() / 2, -menu.getHeight() / 2);

        camera = new OrthographicCamera();
        viewport = new FitViewport(menu.getWidth(), menu.getHeight(), camera);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        /*Debut code pour la fenêtre "jouer/selectionner une carte" */

        List mapSauvegardes = new List(skin);
        stage.addActor(mapSauvegardes);
        FileHandle[] mapSaves = Gdx.files.local(String.valueOf(Gdx.files.internal("saves/cartes"))).list();
        String[] cartes = new String[mapSaves.length + 1];
        cartes[0] = "Default Map";
        int i = 1;
        for (FileHandle file : mapSaves) {
            cartes[i] = file.nameWithoutExtension();
            i = i + 1;
        }
        mapSauvegardes.setItems(cartes);

        Table tableMapSave = new Table();


        tableMapSave.add(mapSauvegardes);
        tableMapSave.row();

        TextButton utiliserButton = new TextButton("Use this map", skin, "default");
        utiliserButton.setWidth(150);
        utiliserButton.setHeight(50);
        utiliserButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                FileHandle fichierMap = Gdx.files.local("saves/cartes/" + mapSauvegardes.getSelected() + ".ser");
                if (!(mapSauvegardes.getSelected() == "Default Map")) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(String.valueOf(fichierMap)))) {

                        seaWarController.nouvellePartie((Grille) ois.readObject());
                        ois.close();
                        game.setScreen(new SeaWarMapScreen(new GameMapManager()));
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    seaWarController.nouvellePartie();
                    game.setScreen(new SeaWarMapScreen(new GameMapManager()));
                }
            }
        });

        /* Bouton "annuler" du JouerScreen */
        TextButton annulerButton = new TextButton("Cancel", skin, "default");
        annulerButton.setWidth(150);
        annulerButton.setHeight(50);
        annulerButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SeaWarMenuScreen());
                Gdx.graphics.setWindowedMode(800, 480);
            }
        });

        tableMapSave.add(utiliserButton);
        tableMapSave.add(annulerButton);

        /*Bouton "Jouer" du Playscreen */
        TextButton oneButton = new TextButton("One player", skin, "default");
        oneButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                IAAleatoire ia = new IAAleatoire();
                ia.start();
                newGame(cartes, skin, tableMapSave);
            }
        });

        TextButton twoButton = new TextButton("Two players", skin, "default");
        twoButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                newGame(cartes, skin, tableMapSave);
            }
        });

        TextButton playButton = new TextButton("Play", skin, "default");
        playButton.setWidth(150);
        playButton.setHeight(50);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                /////////////////       /////////////////       /////////////////
                Dialog d = new Dialog("Choose number of players", skin, "dialog")
                        .text("One or two players?")
                        .button(oneButton, true)
                        .button(twoButton, false)
                        .show(stage);
            }
        });

        /* Fin code "JouerScreen" */

        TextField hauteur = new TextField("", skin);
        TextField largeur = new TextField("", skin);
        hauteur.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        largeur.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        largeur.setMaxLength(3);
        hauteur.setMaxLength(3);

        TextButton openEditeur = new TextButton("Ok", skin, "default");
        openEditeur.setWidth(150);
        openEditeur.setHeight(50);
        openEditeur.addListener(new ClickListener() {


            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!hauteur.getText().trim().isEmpty() && !largeur.getText().trim().isEmpty()) {
                    editeurController.creerCarte(Integer.parseInt(hauteur.getText()), Integer.parseInt(largeur.getText()));
                    game.setScreen(new SeaWarMapScreen(new EditeurMapManager()));
                }
            }
        });
        TextButton editeurButton = new TextButton("Editor", skin, "default");
        editeurButton.setWidth(150);
        editeurButton.setHeight(50);
        editeurButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Dialog d = new Dialog("Choose the dimensions", skin, "dialog")
                        .text("Choose width and height");

                d.add(largeur);
                d.add(hauteur);
                d.getContentTable().row();
                d.add(annulerButton);
                d.add(openEditeur);

                d.show(stage);
            }
        });


        TextButton quitButton = new TextButton("Quit", skin, "default");
        quitButton.setWidth(150);
        quitButton.setHeight(50);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Gdx.app.exit();
            }
        });

        /*Début code pour la fenêtre "charger une partie*/

        List sauvegardes = new List(skin);
        stage.addActor(sauvegardes);
        FileHandle[] saves = Gdx.files.local(String.valueOf(Gdx.files.internal("saves/parties"))).list();
        String[] parties = new String[saves.length];
        i = 0;
        for (FileHandle file : saves) {
            parties[i] = file.nameWithoutExtension();
            i = i + 1;
        }
        sauvegardes.setItems(parties);

        Table tablesave = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        tablesave.add(sauvegardes);
        tablesave.row();

        TextButton chargerButton = new TextButton("Load", skin, "default");
        chargerButton.setWidth(150);
        chargerButton.setHeight(50);
        chargerButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                FileHandle fichier = Gdx.files.local("saves/parties/" + sauvegardes.getSelected() + ".ser");
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(String.valueOf(fichier)))) {
                    seaWarController.load((Partie) ois.readObject());
                    ois.close();
                    game.setScreen(new SeaWarMapScreen(new GameMapManager()));
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        TextButton annulerButton2 = new TextButton("Cancel", skin, "default");
        annulerButton2.setWidth(150);
        annulerButton2.setHeight(50);
        annulerButton2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SeaWarMenuScreen());
                Gdx.graphics.setWindowedMode(800, 480);
            }
        });

        tablesave.add(chargerButton);
        tablesave.add(annulerButton2);

        /*Fin code pour la fenêtre "charger une partie*/

        /*Bouton "charger" sur le menu sreen*/
        TextButton loadButton = new TextButton("Load", skin, "default");
        loadButton.setWidth(150);
        loadButton.setHeight(50);
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Dialog d = new Dialog("Load game", skin, "dialog")
                        .text("Choose a saved game");
                d.add(tablesave);
                d.show(stage);

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
        table.left();

    }

    private void newGame(String[] cartes, Skin skin, Table tableMapSave) {
        if (cartes.length == 1) {
            seaWarController.nouvellePartie();
            game.setScreen(new SeaWarMapScreen(new GameMapManager()));
        } else {
            Dialog d = new Dialog("Choose a map to play", skin, "dialog")
                    .text("");
            d.add(tableMapSave);
            d.show(stage);
        }
    }

    @Override
    public void hide() {
        music.pause();
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
        music.stop();
        music.dispose();
    }
}
