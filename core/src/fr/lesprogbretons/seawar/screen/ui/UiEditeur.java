package fr.lesprogbretons.seawar.screen.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import fr.lesprogbretons.seawar.assets.Assets;
import fr.lesprogbretons.seawar.screen.SeaWarEditeurScreen;
import fr.lesprogbretons.seawar.screen.SeaWarMenuScreen;

import static fr.lesprogbretons.seawar.SeaWar.*;

public class UiEditeur extends Stage {

    private Stage s = this;

    private Table show;

    private Label playerLabel;
    private Label turnLabel;

    private SeaWarEditeurScreen swes;


    public UiEditeur() {
        super();
        Skin skin = (Skin) assets.get(Assets.skin);
        show = new Table();
        show.setFillParent(false);
        show.setPosition(0,770);
        show.setSize(20, 800);
        show.setWidth(800);

        // Get the image
        final Texture t = (Texture) assets.get(Assets.background);
        final Sprite sp = new Sprite(t);
        show.setBackground(new SpriteDrawable(sp));
        show.pack();

        TextButton optionsButton = new TextButton("Options", skin, "default");
        TextButton saveButton = new TextButton("Save", skin, "default");
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TextField nomCarte = new TextField("",skin);
                Dialog d = new Dialog("Nom de la carte", skin, "dialog")
                        .text("Choisissez le nom de votre carte :");

                TextButton validerButton = new TextButton("Sauvegarder",skin,"default");
                TextButton annulerButton = new TextButton("Annuler",skin,"default");
                validerButton.addListener(new ClickListener(){
                    public void clicked(InputEvent event, float x, float y) {
                        editeurController.save(nomCarte.getText());
                        d.hide();
                    }
                });
                annulerButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        d.hide();
                    }
                });
                d.row();
                d.add(nomCarte);
                d.row();
                d.add(validerButton);
                d.add(annulerButton);
                d.show(s);

            }

        });

        TextButton menuButton = new TextButton("Menu", skin, "default");
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SeaWarMenuScreen());
            }
        });

        TextButton caseEau = new TextButton("Case Eau",skin,"default");
        caseEau.addListener(new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                //TODO : ajouter une case eau
            }
        });

        show.add(optionsButton).width(100).padLeft(10).padTop(2).padBottom(3);
        show.add(saveButton).padLeft(10);
        show.add(menuButton).padLeft(10);
        show.add(turnLabel).width(100).padLeft(100);
        show.row();
        show.left().top();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        logger.debug("screenY = " + screenY);
        //Only keep these clicks for the table, send the other to the board
        if (screenY < 25 && show.isVisible()) {
            return true;
        } else {
            return false;
        }
    }
}
