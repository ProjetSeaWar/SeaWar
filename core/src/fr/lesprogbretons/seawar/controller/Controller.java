package fr.lesprogbretons.seawar.controller;



/* Classe Controller qui va gère l'interraction avec l'utilisateur */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import fr.lesprogbretons.seawar.model.Partie;
import fr.lesprogbretons.seawar.model.cases.Case;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Classe Controller
 */
public class Controller {

    //Partie que gère le contrôleur
    private Partie game;

    /**
     * Constructeur
     *
     * @param game
     */
    public Controller(Partie game) {
        this.game = game;
    }


    /**
     * Procédure qui gère la sélection d'une case quelconque à la souris
     *
     * @param x : colonne de la case sélectionnée
     * @param y : ligne de la case sélectionnée
     */
    public void selection(int x, int y) {
        Case c = game.getMap().getCase(x, y);

        boolean actionFaite = false;

        //Les actions que veut faire le joueur avec le bateau qu'il a sélectionné
        if (game.isAnyBateauSelectionne()) {

            ArrayList<Case> casesPorteeTir;
            casesPorteeTir = game.getMap().getCasesPortees(game.getBateauSelectionne());

            if(game.getMap().casePossedeBateau(c,game.getCurrentPlayer())){
                game.setBateauSelectionne(game.getMap().bateauSurCase(c));
                actionFaite = true;
            }

            //Si la case sélectionnée est à portée de tir
             else if (casesPorteeTir.contains(c)) {
                if (game.getMap().casePossedeBateau(c, game.getOtherPlayer())) {
                    game.getBateauSelectionne().shoot(game.getMap().bateauSurCase(c));
                    game.setAnyBateauSelectionne(false);
                    actionFaite = true;
                }
            }

            if (!actionFaite) {
                ArrayList<Case> casesDispo;
                casesDispo = game.getMap().getCasesDisponibles(game.getBateauSelectionne().getPosition(),1);

                //Si la case sélectionnée est à portée de déplacement
                if (casesDispo.contains(c) && game.getBateauSelectionne().getMoveAvailable() > 0) {
                    game.getBateauSelectionne().moveBoat(c);
                    if (c.isPhare()) {
                        game.getMap().prendPhare(c, game.getCurrentPlayer());
                    }
                } else {
                    game.setAnyBateauSelectionne(false);
                }
            }


        } else {
            //Si le joueur sélectionne un de ses bateaux
            if (game.getMap().casePossedeBateau(c, game.getCurrentPlayer())) {
                game.setBateauSelectionne(game.getMap().bateauSurCase(c));
            }

        }
    }

    /**
     * Procédure qui finit le tour du joueur quand on appuie sur le bouton fin de tour
     */
    public boolean endTurn() {
        boolean isOver = false;
        game.finPartie();

        if (!game.isFin()) {
            game.setAnyBateauSelectionne(false);
            isOver = game.endTurn();
        }
        return isOver;
    }

    public void changercanon() {
        game.getBateauSelectionne().setCanonSelectionne(3 - game.getBateauSelectionne().getCanonSelectionne());

    }
    public void save(String nom){
        FileHandle fichier = Gdx.files.internal("saves/"+nom+".ser");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(String.valueOf(fichier)))) {
            oos.writeObject(game);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
