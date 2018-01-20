package fr.lesprogbretons.seawar.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import fr.lesprogbretons.seawar.assets.Assets;

import static fr.lesprogbretons.seawar.SeaWar.assets;
import static fr.lesprogbretons.seawar.SeaWar.logger;

public class Ui extends Stage {
    private Label joueurLabel;
    private Table show;
    private Table hide;


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

        TextButton options = new TextButton("Options", skin, "default");
        TextButton save = new TextButton("Save", skin, "default");
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

        joueurLabel = new Label("Joueur", skin, "default");

        show.add(joueurLabel).width(100).padLeft(10).padTop(2).padBottom(3);
        show.add(options).padLeft(10);
        show.add(save).padLeft(10);
        show.add(hideButton).padLeft(10);
        show.add(new Label("Tour XXX", skin, "default")).width(100).padLeft(350);
        show.row();
        show.left().top();

        hide.add(showButton).padLeft(10);
        hide.row();
        hide.left().top();
    }

    public void setJoueur(String j) {
        joueurLabel.setText(j);
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
