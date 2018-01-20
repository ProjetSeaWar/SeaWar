package fr.lesprogbretons.seawar.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import fr.lesprogbretons.seawar.assets.Assets;

import static fr.lesprogbretons.seawar.SeaWar.*;

public class Ui extends Stage {

    private Stage s = this;

    private Table show;
    private Table hide;

    private Label playerLabel;
    private Label turnLabel;


    public Ui() {
        super();
        Skin skin = (Skin) assets.get(Assets.skin);
        show = new Table();
        show.setFillParent(false);
        show.setPosition(0,770);
        show.setSize(20, 800);
        show.setWidth(800);
        addActor(show);

        hide = new Table();
        hide.setFillParent(true);
        addActor(hide);
        hide.setVisible(false);

        // Get the image
        final Texture t = (Texture) assets.get(Assets.background);
        final Sprite sp = new Sprite(t);
        show.setBackground(new SpriteDrawable(sp));
        show.pack();

        TextButton optionsButton = new TextButton("Options", skin, "default");
        TextButton saveButton = new TextButton("Save", skin, "default");

        TextButton endTurnButton = new TextButton("End Turn", skin, "default");
        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean turnOver = seaWarController.endTurn();
                if (!turnOver) {
                    Dialog d = new Dialog("Turn isn't over", skin, "dialog")
                            .text("One of your ship haven't moved")
                            .button("Okay", true)
                            .key(Input.Keys.ENTER, true)
                            .show(s);
                }
            }
        });

        TextButton hideButton = new TextButton("Hide", skin, "default");
        hideButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               show.setVisible(false);
               hide.setVisible(true);
            }
        });

        TextButton showButton = new TextButton("Show", skin, "default");
        showButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide.setVisible(false);
                show.setVisible(true);
            }
        });

        playerLabel = new Label("Player", skin, "default");
        turnLabel = new Label("Turn XXX", skin, "default");

        show.add(playerLabel).width(100).padLeft(10).padTop(2).padBottom(3);
        show.add(optionsButton).padLeft(10);
        show.add(saveButton).padLeft(10);
        show.add(hideButton).padLeft(10);
        show.add(endTurnButton).padLeft(50);
        show.add(turnLabel).width(100).padLeft(200);
        show.row();
        show.left().top();

        hide.add(showButton).padLeft(10);
        hide.row();
        hide.left().top();
    }

    public void setPlayer(String j) {
        playerLabel.setText(j);
    }

    public void setTurn(int turn) {
        turnLabel.setText("Turn " + turn);
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
