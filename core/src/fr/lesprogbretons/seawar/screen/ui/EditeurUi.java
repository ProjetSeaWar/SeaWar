package fr.lesprogbretons.seawar.screen.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.lesprogbretons.seawar.model.cases.CaseEau;
import fr.lesprogbretons.seawar.screen.SeaWarMapScreen;
import fr.lesprogbretons.seawar.screen.SeaWarMenuScreen;

import static fr.lesprogbretons.seawar.SeaWar.editeur;
import static fr.lesprogbretons.seawar.SeaWar.editeurController;
import static fr.lesprogbretons.seawar.SeaWar.game;
import static fr.lesprogbretons.seawar.screen.SeaWarMapScreen.selectedTile;

public class EditeurUi extends Ui {


    public EditeurUi() {
        super();

        TextButton optionsButton = new TextButton("Options", skin, "default");
        TextButton saveButton = new TextButton("Save", skin, "default");
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TextField nomCarte = new TextField("", skin);
                Dialog d = new Dialog("Nom de la carte", skin, "dialog")
                        .text("Choisissez le nom de votre carte :");

                TextButton validerButton = new TextButton("Sauvegarder", skin, "default");
                TextButton annulerButton = new TextButton("Annuler", skin, "default");
                validerButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        if(!editeur.getMap().getBateaux1().isEmpty()) {
                            if (!editeur.getMap().getBateaux2().isEmpty()) {
                                editeurController.saveGrille(nomCarte.getText());
                                d.hide();
                            }
                            else{
                                Dialog d = new Dialog("Error", skin, "dialog")
                                        .text("Player 2 has no boat !")
                                        .button("Ok");
                                d.show(s);

                            }

                        }
                        else{
                            Dialog d = new Dialog("Error", skin, "dialog")
                                    .text("Player 1 has no boat !")
                                    .button("Ok");
                            d.show(s);

                        }
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

        TextButton caseEau = new TextButton("Case Eau", skin, "default");
        caseEau.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.creerCaseEau(selectedTile.row,selectedTile.column);
                } catch (ArrayIndexOutOfBoundsException ignored){}
            }
        });

        show.add(optionsButton).width(100).padLeft(10).padTop(2).padBottom(3);
        show.add(saveButton).padLeft(10);
        show.add(menuButton).padLeft(10);
        show.add(caseEau).padLeft(10);
        show.row();
        show.left().top();
    }
}
