package fr.lesprogbretons.seawar.screen.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import fr.lesprogbretons.seawar.assets.Assets;

import static fr.lesprogbretons.seawar.SeaWar.assets;
import static fr.lesprogbretons.seawar.SeaWar.logger;

public abstract class Ui extends Stage {

    protected Stage s = this;
    protected Table show;
    protected Skin skin;
    protected Dialog openedDialog;
    protected Dialog hideDialog;


    public Ui() {
        super();
        skin = (Skin) assets.get(Assets.skin);
        show = new Table();
        show.setFillParent(false);
        show.setPosition(0, 770);
        show.setSize(20, 800);
        show.setWidth(800);
        addActor(show);

        // Get the image
        final Texture t = (Texture) assets.get(Assets.background);
        final Sprite sp = new Sprite(t);
        show.setBackground(new SpriteDrawable(sp));
        show.pack();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        logger.debug("screenY = " + screenY);
        //Only keep these clicks for the table, send the other to the board
        if (openedDialog != null) {
            if (hideDialog != null) {
                hideDialog.hide();
                //On retire la référence pour s'assurer du bon fonctionnement
                hideDialog = null;
                openedDialog = null;
            }
            //On ne veut pas cliquer quand on utilise une boite de dialogue
            return true;
        }
        return screenY < 25 && show.isVisible();
    }
}
