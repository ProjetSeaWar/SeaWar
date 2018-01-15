package fr.lesprogbretons.seawar.model;

/* Classe Controller qui va gère l'interraction avec l'utilisateur */
// TODO : Ajouter les ActionListener

import java.util.ArrayList;

public class Controller {

    Partie game = new Partie();

    public Controller(Partie game){
        this.game = game;
    }

    public void selection(Case c){
        boolean actionFaite = false;

        if(game.isAnyBateauSelectionne()){
            ArrayList<Case> casesPorteeTir = new ArrayList<>();
            game.getMap().getCasesPortees(game.getBateauSelectionne().getPosition(),game.getBateauSelectionne(),casesPorteeTir);

            if(casesPorteeTir.contains(c)){
                if(game.getMap().casePossedeBateau(c,game.getOtherPlayer())){
                    game.getBateauSelectionne().shoot(game.getMap().bateauSurCase(c));
                    game.setAnyBateauSelectionne(false);
                    actionFaite = true;
                }
            }

            if(!actionFaite){
                ArrayList<Case> casesDispo = new ArrayList<>();
                game.getMap().getCasesDisponibles(game.getBateauSelectionne().getPosition(),game.getBateauSelectionne().getMoveAvailable(),casesDispo);

                if(casesDispo.contains(c)){
                    game.getBateauSelectionne().moveBoat(c);
                    if(c.isPhare()){
                        //////// TODO bite
                    }
                }
            }
        }

        else {
            if(game.getMap().casePossedeBateau(c,game.getCurrentPlayer())){
                game.setBateauSelectionne() = game.getMap().bateauSurCase(c);
            }

        }
    }

}
