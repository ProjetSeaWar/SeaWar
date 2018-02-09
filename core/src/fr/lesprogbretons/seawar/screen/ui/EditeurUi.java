package fr.lesprogbretons.seawar.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import fr.lesprogbretons.seawar.assets.Assets;
import fr.lesprogbretons.seawar.model.Orientation;
import fr.lesprogbretons.seawar.screen.SeaWarMenuScreen;

import static fr.lesprogbretons.seawar.SeaWar.*;
import static fr.lesprogbretons.seawar.screen.SeaWarMapScreen.selectedTile;

public class EditeurUi extends Ui {

    private Table choixJoueur;
    private Table choixBateauJoueur1;
    private Table choixBateauJoueur2;
    private Table choixOrientationa1;
    private Table choixOrientationa2;
    private Table choixOrientationf1;
    private Table choixOrientationf2;


    public EditeurUi() {
        super();
        skin = (Skin) assets.get(Assets.skin);
        final Texture t = (Texture) assets.get(Assets.background);
        final Sprite sp = new Sprite(t);

        choixJoueur = new Table();
        choixJoueur.setFillParent(false);
        choixJoueur.setPosition(0, 770);
        choixJoueur.setSize(20, 800);
        choixJoueur.setWidth(800);
        addActor(choixJoueur);
        choixJoueur.setVisible(false);
        choixJoueur.setBackground(new SpriteDrawable(sp));
        choixJoueur.pack();

        choixBateauJoueur1 = new Table();
        choixBateauJoueur1.setFillParent(false);
        choixBateauJoueur1.setPosition(0, 770);
        choixBateauJoueur1.setSize(20, 800);
        choixBateauJoueur1.setWidth(800);
        addActor(choixBateauJoueur1);
        choixBateauJoueur1.setVisible(false);
        choixBateauJoueur1.setBackground(new SpriteDrawable(sp));
        choixBateauJoueur1.pack();

        choixBateauJoueur2 = new Table();
        choixBateauJoueur2.setFillParent(false);
        choixBateauJoueur2.setPosition(0, 770);
        choixBateauJoueur2.setSize(20, 800);
        choixBateauJoueur2.setWidth(800);
        addActor(choixBateauJoueur2);
        choixBateauJoueur2.setVisible(false);
        choixBateauJoueur2.setBackground(new SpriteDrawable(sp));
        choixBateauJoueur2.pack();

        choixOrientationa1 = new Table();
        choixOrientationa1.setFillParent(false);
        choixOrientationa1.setPosition(0, 770);
        choixOrientationa1.setSize(20, 800);
        choixOrientationa1.setWidth(800);
        addActor(choixOrientationa1);
        choixOrientationa1.setVisible(false);
        choixOrientationa1.setBackground(new SpriteDrawable(sp));
        choixOrientationa1.pack();

        choixOrientationa2 = new Table();
        choixOrientationa2.setFillParent(false);
        choixOrientationa2.setPosition(0, 770);
        choixOrientationa2.setSize(20, 800);
        choixOrientationa2.setWidth(800);
        addActor(choixOrientationa2);
        choixOrientationa2.setVisible(false);
        choixOrientationa2.setBackground(new SpriteDrawable(sp));
        choixOrientationa2.pack();

        choixOrientationf1 = new Table();
        choixOrientationf1.setFillParent(false);
        choixOrientationf1.setPosition(0, 770);
        choixOrientationf1.setSize(20, 800);
        choixOrientationf1.setWidth(800);
        addActor(choixOrientationf1);
        choixOrientationf1.setVisible(false);
        choixOrientationf1.setBackground(new SpriteDrawable(sp));
        choixOrientationf1.pack();

        choixOrientationf2 = new Table();
        choixOrientationf2.setFillParent(false);
        choixOrientationf2.setPosition(0, 770);
        choixOrientationf2.setSize(20, 800);
        choixOrientationf2.setWidth(800);
        addActor(choixOrientationf2);
        choixOrientationf2.setVisible(false);
        choixOrientationf2.setBackground(new SpriteDrawable(sp));
        choixOrientationf2.pack();

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
                        if (!editeur.getMap().getBateaux1().isEmpty()) {
                            if (!editeur.getMap().getBateaux2().isEmpty()) {
                                editeurController.saveGrille(nomCarte.getText());
                                d.hide();
                            } else {
                                Dialog d = new Dialog("Error", skin, "dialog")
                                        .text("Player 2 has no boat !")
                                        .button("Ok");
                                d.show(s);

                            }

                        } else {
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
                    editeurController.creerCaseEau(selectedTile.row, selectedTile.column);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton caseTerre = new TextButton("Case Terre", skin, "default");
        caseTerre.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.creerCaseTerre(selectedTile.row, selectedTile.column);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton casePhare = new TextButton("Case Phare", skin, "default");
        casePhare.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.creerCasePhare(selectedTile.row, selectedTile.column);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton norda1 = new TextButton("Nord", skin, "default");
        norda1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur1(selectedTile.row, selectedTile.column, Orientation.NORD);
                    choixOrientationa1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton nordEsta1 = new TextButton("Nord Est", skin, "default");
        nordEsta1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur1(selectedTile.row, selectedTile.column, Orientation.NORDEST);
                    choixOrientationa1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton sudEsta1 = new TextButton("Sud Est", skin, "default");
        sudEsta1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur1(selectedTile.row, selectedTile.column, Orientation.SUDEST);
                    choixOrientationa1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton suda1 = new TextButton("Sud", skin, "default");
        suda1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur1(selectedTile.row, selectedTile.column, Orientation.SUD);
                    choixOrientationa1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton sudOuesta1 = new TextButton("Sud Ouest", skin, "default");
        sudOuesta1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur1(selectedTile.row, selectedTile.column, Orientation.SUDOUEST);
                    choixOrientationa1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton nordOuesta1 = new TextButton("Nord Ouest", skin, "default");
        nordOuesta1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur1(selectedTile.row, selectedTile.column, Orientation.NORDOUEST);
                    choixOrientationa1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton norda2 = new TextButton("Nord", skin, "default");
        norda2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur2(selectedTile.row, selectedTile.column, Orientation.NORD);
                    choixOrientationa2.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton nordEsta2 = new TextButton("Nord Est", skin, "default");
        nordEsta2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur2(selectedTile.row, selectedTile.column, Orientation.NORDEST);
                    choixOrientationa2.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton sudEsta2 = new TextButton("Sud Est", skin, "default");
        sudEsta2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur2(selectedTile.row, selectedTile.column, Orientation.SUDEST);
                    choixOrientationa2.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton suda2 = new TextButton("Sud", skin, "default");
        suda2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur2(selectedTile.row, selectedTile.column, Orientation.SUD);
                    choixOrientationa2.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton sudOuesta2 = new TextButton("Sud Ouest", skin, "default");
        sudOuesta2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur2(selectedTile.row, selectedTile.column, Orientation.SUDOUEST);
                    choixOrientationa2.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton nordOuesta2 = new TextButton("Nord Ouest", skin, "default");
        nordOuesta2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterAmiralJoueur2(selectedTile.row, selectedTile.column, Orientation.NORDOUEST);
                    choixOrientationa2.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton nordOuestf1 = new TextButton("Nord Ouest", skin, "default");
        nordOuestf1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur1(selectedTile.row, selectedTile.column, Orientation.NORDOUEST);
                    choixOrientationf1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton nordf1 = new TextButton("Nord", skin, "default");
        nordf1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur1(selectedTile.row, selectedTile.column, Orientation.NORD);
                    choixOrientationf1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton nordEstf1 = new TextButton("Nord Est", skin, "default");
        nordEstf1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur1(selectedTile.row, selectedTile.column, Orientation.NORDEST);
                    choixOrientationf1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton sudEstf1 = new TextButton("Sud Est", skin, "default");
        sudEstf1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur1(selectedTile.row, selectedTile.column, Orientation.SUDEST);
                    choixOrientationf1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton sudf1 = new TextButton("Sud", skin, "default");
        sudf1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur1(selectedTile.row, selectedTile.column, Orientation.SUD);
                    choixOrientationf1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton sudOuestf1 = new TextButton("Sud Ouest", skin, "default");
        sudOuestf1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur1(selectedTile.row, selectedTile.column, Orientation.SUDOUEST);
                    choixOrientationf1.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton nordOuestf2 = new TextButton("Nord Ouest", skin, "default");
        nordOuestf2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur2(selectedTile.row, selectedTile.column, Orientation.NORDOUEST);
                    choixOrientationf2.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton nordf2 = new TextButton("Nord", skin, "default");
        nordf2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur2(selectedTile.row, selectedTile.column, Orientation.NORD);
                    choixOrientationf2.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton nordEstf2 = new TextButton("Nord Est", skin, "default");
        nordEstf2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur2(selectedTile.row, selectedTile.column, Orientation.NORDEST);
                    choixOrientationf2.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton sudEstf2 = new TextButton("Sud Est", skin, "default");
        sudEstf2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur2(selectedTile.row, selectedTile.column, Orientation.SUDEST);
                    choixOrientationf2.setVisible(false);

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton sudf2 = new TextButton("Sud", skin, "default");
        sudf2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur2(selectedTile.row, selectedTile.column, Orientation.SUD);
                    choixOrientationf2.setVisible(false);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton sudOuestf2 = new TextButton("Sud Ouest", skin, "default");
        sudOuestf2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    editeurController.ajouterFregateJoueur2(selectedTile.row, selectedTile.column, Orientation.SUDOUEST);
                    choixOrientationf2.setVisible(false);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    selectionDialog();
                }
            }
        });

        TextButton amiralJoueur1 = new TextButton("Amiral", skin, "default");
        amiralJoueur1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choixOrientationa1.setVisible(true);
                choixBateauJoueur1.setVisible(false);
            }
        });

        TextButton fregateJoueur1 = new TextButton("Fregate", skin, "default");
        fregateJoueur1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choixOrientationf1.setVisible(true);
                choixBateauJoueur1.setVisible(false);
            }
        });

        TextButton amiralJoueur2 = new TextButton("Amiral", skin, "default");
        amiralJoueur2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choixOrientationa2.setVisible(true);
                choixBateauJoueur2.setVisible(false);
            }
        });

        TextButton fregateJoueur2 = new TextButton("Fregate", skin, "default");
        fregateJoueur2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choixOrientationf2.setVisible(true);
                choixBateauJoueur2.setVisible(false);
            }
        });


        TextButton joueur1 = new TextButton("Bateau Joueur 1", skin, "default");
        joueur1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choixJoueur.setVisible(false);
                choixBateauJoueur1.setVisible(true);
            }
        });

        TextButton joueur2 = new TextButton("Bateau Joueur 2", skin, "default");
        joueur2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choixJoueur.setVisible(false);
                choixBateauJoueur2.setVisible(true);
            }
        });

        TextButton bateau = new TextButton("Bateau", skin, "default");
        bateau.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choixJoueur.setVisible(true);
            }
        });

        show.add(optionsButton).width(100).padLeft(10).padTop(2).padBottom(3);
        show.add(saveButton).padLeft(10);
        show.add(menuButton).padLeft(10);
        show.add(caseEau).padLeft(10);
        show.add(caseTerre).padLeft(10);
        show.add(casePhare).padLeft(10);
        show.add(bateau).padLeft(10);

        choixJoueur.add(joueur1).padLeft(10).padTop(2).padBottom(3);
        choixJoueur.add(joueur2).padLeft(10);

        choixBateauJoueur1.add(amiralJoueur1).width(100).padLeft(10).padTop(2).padBottom(3);
        choixBateauJoueur1.add(fregateJoueur1).padLeft(10);

        choixBateauJoueur2.add(amiralJoueur2).width(100).padLeft(10).padTop(2).padBottom(3);
        choixBateauJoueur2.add(fregateJoueur2).padLeft(10);

        choixOrientationa1.add(norda1).width(100).padLeft(10).padTop(2).padBottom(3);
        choixOrientationa1.add(nordEsta1).padLeft(10);
        choixOrientationa1.add(sudEsta1).padLeft(10);
        choixOrientationa1.add(suda1).padLeft(10);
        choixOrientationa1.add(sudOuesta1).padLeft(10);
        choixOrientationa1.add(nordOuesta1).padLeft(10);

        choixOrientationa2.add(norda2).width(100).padLeft(10).padTop(2).padBottom(3);
        choixOrientationa2.add(nordEsta2).padLeft(10);
        choixOrientationa2.add(sudEsta2).padLeft(10);
        choixOrientationa2.add(suda2).padLeft(10);
        choixOrientationa2.add(sudOuesta2).padLeft(10);
        choixOrientationa2.add(nordOuesta2).padLeft(10);

        choixOrientationf1.add(nordf1).width(100).padLeft(10).padTop(2).padBottom(3);
        choixOrientationf1.add(nordEstf1).padLeft(10);
        choixOrientationf1.add(sudEstf1).padLeft(10);
        choixOrientationf1.add(sudf1).padLeft(10);
        choixOrientationf1.add(sudOuestf1).padLeft(10);
        choixOrientationf1.add(nordOuestf1).padLeft(10);

        choixOrientationf2.add(nordf2).width(100).padLeft(10).padTop(2).padBottom(3);
        choixOrientationf2.add(nordEstf2).padLeft(10);
        choixOrientationf2.add(sudEstf2).padLeft(10);
        choixOrientationf2.add(sudf2).padLeft(10);
        choixOrientationf2.add(sudOuestf2).padLeft(10);
        choixOrientationf2.add(nordOuestf2).padLeft(10);

        show.row();
        show.left().top();

        choixJoueur.row();
        choixJoueur.left().top();

        choixBateauJoueur1.row();
        choixBateauJoueur1.left().top();

        choixBateauJoueur2.row();
        choixBateauJoueur2.left().top();

        choixOrientationa1.row();
        choixOrientationa1.left().top();

        choixOrientationa2.row();
        choixOrientationa2.left().top();

        choixOrientationf1.row();
        choixOrientationf1.left().top();

        choixOrientationf2.row();
        choixOrientationf2.left().top();
    }

    private void selectionDialog() {
        openedDialog = new Dialog("Select a case", skin, "dialog")
                .text("You must select a case!")
                .button("Okay", true)
                .key(Input.Keys.ENTER, true)
                .show(s);
    }
}
