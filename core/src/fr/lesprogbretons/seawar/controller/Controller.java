package fr.lesprogbretons.seawar.controller;

/* Classe Controller qui va gère l'interraction avec l'utilisateur */

import fr.lesprogbretons.seawar.model.Partie;
import fr.lesprogbretons.seawar.model.cases.Case;

import java.util.ArrayList;

public class Controller {


    Partie game = new Partie();

    public Controller(Partie game) {
        this.game = game;
    }


    //Méthode qui gère la sélection d'une case à la souris
    public void selection(int x, int y) {
        Case c = game.getMap().getCase(x, y);

        boolean actionFaite = false;

        if (game.isAnyBateauSelectionne()) {
            ArrayList<Case> casesPorteeTir;
            casesPorteeTir = game.getMap().getCasesPortees(game.getBateauSelectionne());

            if (casesPorteeTir.contains(c)) {
                if (game.getMap().casePossedeBateau(c, game.getOtherPlayer())) {
                    game.getBateauSelectionne().shoot(game.getMap().bateauSurCase(c));
                    game.setAnyBateauSelectionne(false);
                    actionFaite = true;
                }
            }

            if (!actionFaite) {
                ArrayList<Case> casesDispo = new ArrayList<>();
                game.getMap().getCasesDisponible(game.getBateauSelectionne().getPosition(), 1, casesDispo);

                if (casesDispo.contains(c) && game.getBateauSelectionne().getMoveAvailable() > 0) {
                    game.getBateauSelectionne().moveBoat(c);
                    if (c.isPhare()) {
                        game.getMap().prendPhare(c, game.getCurrentPlayer());
                    }
                    game.setAnyBateauSelectionne(false);
                } else {
                    game.setAnyBateauSelectionne(false);
                }
            }
        } else {
            if (game.getMap().casePossedeBateau(c, game.getCurrentPlayer())) {
                game.setBateauSelectionne(game.getMap().bateauSurCase(c));

            }

        }
    }

    //Méthode qui finit un tour quand on appuie sur le bon bouton
    public void endTurn() {
        game.finPartie();

        if (!game.isFin()) {
            game.setAnyBateauSelectionne(false);
            game.endTurn();
        }
    }
    public void changercanon(){
        game.getBateauSelectionne().setCanonSelectionne(3-game.getBateauSelectionne().getCanonSelectionne());

    }

}
