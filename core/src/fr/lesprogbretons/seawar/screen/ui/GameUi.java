package fr.lesprogbretons.seawar.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.lesprogbretons.seawar.model.boat.Boat;
import fr.lesprogbretons.seawar.screen.SeaWarMenuScreen;

import java.util.ArrayList;

import static fr.lesprogbretons.seawar.SeaWar.*;

public class GameUi extends Ui {

    private Table hide;

    private Label playerLabel;
    private Label turnLabel;
    private Label infoSelected;

    public GameUi() {
        super();

        hide = new Table();
        hide.setFillParent(true);
        addActor(hide);
        hide.setVisible(false);

        //region Buttons
        TextButton optionsButton = new TextButton("Options", skin, "default");
        TextButton saveButton = new TextButton("Save", skin, "default");
        TextButton endTurnButton = new TextButton("End Turn", skin, "default");
        TextButton hideButton = new TextButton("Hide", skin, "default");
        TextButton showButton = new TextButton("Show", skin, "default");
        TextButton menuButton = new TextButton("Menu", skin, "default");

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openedDialog = new Dialog("Options", skin, "dialog")
                        .text("Choose your option")
                        .button(saveButton)
                        .button(menuButton)
                        .button("Quit", false)
                        .show(s);
            }
        });


        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                TextField nompartie = new TextField("", skin);
                Dialog d = new Dialog("Nom de la partie", skin, "dialog")
                        .text("Choisissez le nom de votre sauvegarde :");

                TextButton validerButton = new TextButton("Sauvegarder", skin, "default");
                validerButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        seaWarController.save(nompartie.getText());

                    }
                });
                d.getContentTable().row();
                d.getContentTable().add(nompartie);
                d.button(validerButton, true);
                d.button("Annuler", false);
                d.show(s);
                openedDialog = d;

            }

        });

        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog d;
                boolean turnOver = seaWarController.endTurn();
                if (!turnOver) {
                    if (partie.isFin()) {
                        d = new Dialog("The winner is " + partie.getWinner().toString(), skin, "dialog")
                                .text(partie.getWinner().toString() + " wins by " + partie.getVictoryType().toString())
                                .button(menuButton, true)
                                .show(s);
                        openedDialog = d;

                    } else {
                        d = new Dialog("Turn isn't over", skin, "dialog")
                                .text("One of your ship haven't moved")
                                .button("Okay", true)
                                .key(Input.Keys.ENTER, true)
                                .show(s);
                        openedDialog = d;
                    }
                } else {
                    startTurnMessage();
                }
            }
        });

        hideButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                show.setVisible(false);
                hide.setVisible(true);
            }
        });

        showButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide.setVisible(false);
                show.setVisible(true);
            }
        });

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SeaWarMenuScreen());
            }
        });
        //endregion

        //region Organisation
        playerLabel = new Label("", skin, "default");
        turnLabel = new Label("", skin, "default");
        infoSelected = new Label("", skin, "default");

        show.add(playerLabel).width(100).padLeft(10).padTop(2).padBottom(3);
        show.add(infoSelected).width(200).padLeft(10);
        show.add(optionsButton).padLeft(10);
        show.add(hideButton).padLeft(25);
        show.add(endTurnButton).padLeft(50);
        show.add(turnLabel).width(100).padLeft(45);
        show.row();
        show.left().top();

        hide.add(showButton).padLeft(10);
        hide.row();
        hide.left().top();
        //endregion
    }

    public void startTurnMessage() {
        //Construire la string qui contient les infos de tour
        ArrayList<String> boatHps = new ArrayList<>();
        if (partie.getCurrentPlayer().getNumber() == 1) {
            for (Boat b : partie.getMap().getBateaux1()) {
                boatHps.add(b.infos());
            }
        } else {
            for (Boat b : partie.getMap().getBateaux2()) {
                boatHps.add(b.infos());
            }
        }

        StringBuilder tour = new StringBuilder(partie.getCurrentPlayer().getPharesPossedes() + " lighthouses taken\n");
        for (String s : boatHps) {
            tour.append(s).append("\n");
        }
        openedDialog = new Dialog("It's " + partie.getCurrentPlayer().toString() + " turn", skin, "default")
                .text(tour.toString())
                .button("Okay", true)
                .key(Input.Keys.ENTER, true)
                .show(s);
    }

    public void setPlayer(String j) {
        playerLabel.setText(j);
    }

    public void setTurn(int turn) {
        turnLabel.setText("Turn " + turn);
    }

    public void setInfoSelected(String message) {
        infoSelected.setText(message);
    }
}