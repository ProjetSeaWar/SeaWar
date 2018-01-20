package fr.lesprogbretons.seawar.screen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import fr.lesprogbretons.seawar.assets.Assets;

import static fr.lesprogbretons.seawar.SeaWar.assets;
import static fr.lesprogbretons.seawar.SeaWar.logger;

public class Ui extends Stage {
    private Label joueurLabel;
    private Table table;


    public Ui() {
        super();
        Skin skin = (Skin) assets.get(Assets.skin);
        table = new Table();
        table.setFillParent(true);
        addActor(table);
        table.setDebug(true);

        joueurLabel = new Label("Joueur", skin, "default");
        table.add(joueurLabel).width(100).padLeft(10).top();
        table.add(new TextButton("Save", skin, "default")).padLeft(10);
        table.row();
        table.left().top();
    }

    public void setJoueur(String j) {
        joueurLabel.setText(j);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        logger.debug("screenY = " + screenY);
        //Only keep these clicks for the table, send the other to the board
        if (screenY < 25) {
            return true;
        } else {
            return false;
        }
    }
}
