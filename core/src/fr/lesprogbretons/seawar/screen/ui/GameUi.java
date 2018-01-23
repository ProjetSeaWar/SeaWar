package fr.lesprogbretons.seawar.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.lesprogbretons.seawar.model.boat.Boat;
import fr.lesprogbretons.seawar.model.cases.Case;
import fr.lesprogbretons.seawar.screen.SeaWarMenuScreen;

import java.util.ArrayList;

import static fr.lesprogbretons.seawar.SeaWar.*;
import static fr.lesprogbretons.seawar.screen.SeaWarMapScreen.selectedTile;

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
                        .button("Dismiss", false)
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
                d.button("Dismiss", false);
                d.show(s);
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
                    selectedTile.setCoords(-1, -1);
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

        hide.add(showButton).padLeft(10).padTop(2).padBottom(3);
        hide.row();
        hide.left().top();
        //endregion
    }

    public void showInfoMessage() {
        //Récupérer les turnInfos
        Case aCase = partie.getMap().getCase(selectedTile.row, selectedTile.column);
        if (partie.getMap().casePossedeBateaux(aCase)) {
            Boat boat = partie.getMap().bateauSurCase(aCase);

            Dialog d = new Dialog(boat.getJoueur().toString() + "'s " + boat.toString(),
                    skin, "default");

            Table t = new Table();
            t.setFillParent(true);

            Label name = new Label(boat.infos(), skin, "default");
            ProgressBar coolDownProgress = new ProgressBar(0, boat.getSelectedCanonReload(),
                    1, false, skin, "default-horizontal");
            coolDownProgress.setValue(boat.getSelectedCanonReload() - boat.getSelectedCanonCoolDown());

            Label hp = new Label("Hp", skin, "default");
            //TODO Boat get Max HP
            ProgressBar hpProgressBar = new ProgressBar(0, boat.getMaxHp(), 1,
                    false, skin, "default-horizontal");
            hpProgressBar.setValue(boat.getHp());

            TextButton changeButton = new TextButton("Switch Weapon", skin, "default");
            changeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    d.hide();
                    seaWarController.changerCanon(boat);
                    showInfoMessage();
                }
            });


            TextButton dismissButton = new TextButton("Dismiss", skin, "default");
            dismissButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    d.hide();
                }
            });

            t.add(name).width(300).padLeft(10).padTop(2).padBottom(3);
            t.add(coolDownProgress).width(50).padLeft(10);
            t.row();
            t.add(hp).width(300).padLeft(10).padTop(2).padBottom(3);
            t.add(hpProgressBar).width(50).padLeft(10);
            t.row();
            t.add(changeButton).width(250).padLeft(10).padTop(2).padBottom(3);
            t.add(dismissButton).width(100).padLeft(10);
            t.left().top();

            d.getContentTable().add(t);
            d.show(s);

        } else {
            //Récupérer phare
            String lighthouse;
            if (aCase.isPhare() && aCase.getPossedePhare() != null) {
                lighthouse = "Lighthouse owned by " + aCase.getPossedePhare().toString();
            } else if (aCase.isPhare() && aCase.getPossedePhare() == null) {
                lighthouse = "Free lighthouse";
            } else {
                lighthouse = "Nothing to see here";
            }
            openedDialog = new Dialog(aCase.toString(), skin, "default")
                    .text(lighthouse)
                    .button("Dismiss", true)
                    .key(Input.Keys.ENTER, true)
                    .show(s);
        }


    }

    public void startTurnMessage() {
        //Construire la string qui contient les turnInfos de tour
        ArrayList<String> boatHps = new ArrayList<>();
        if (partie.getCurrentPlayer().getNumber() == 1) {
            for (Boat b : partie.getMap().getBateaux1()) {
                boatHps.add(b.turnInfos());
            }
        } else {
            for (Boat b : partie.getMap().getBateaux2()) {
                boatHps.add(b.turnInfos());
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
