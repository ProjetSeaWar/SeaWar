package fr.lesprogbretons.seawar.model;

/* Classe Controller qui va gère l'interraction avec l'utilisateur */
// TODO : Ajouter les ActionListener

import java.util.ArrayList;

public class Controller {

    Partie game = new Partie();

    public Controller(Partie game){
        this.game = game;
    }


    //Méthode qui gère la sélection d'une case à la souris
    public void selection(int x, int y){
            Case c = game.getMap().getCase(x,y);

            boolean actionFaite = false;

            if(game.isAnyBateauSelectionne()){
                ArrayList<Case> casesPorteeTir = new ArrayList<>();
                game.getMap().getCasesPortees(game.getBateauSelectionne(),casesPorteeTir);

                if(casesPorteeTir.contains(c)){
                    if(game.getMap().casePossedeBateau(c,game.getOtherPlayer())){
                        game.getBateauSelectionne().shoot(game.getMap().bateauSurCase(c));
                        game.setAnyBateauSelectionne(false);
                        actionFaite = true;
                    }
                }

                if(!actionFaite){
                    ArrayList<Case> casesDispo = new ArrayList<>();
                    game.getMap().getCasesDisponibles(game.getBateauSelectionne().getPosition(),1,casesDispo);

                    if(casesDispo.contains(c) && game.getBateauSelectionne().getMoveAvailable()>0){
                        game.getBateauSelectionne().moveBoat(c);
                        if(c.isPhare()){
                            game.getMap().prendPhare(c,game.getCurrentPlayer());
                        }
                        game.setAnyBateauSelectionne(false);
                    }

                    else {
                        game.setAnyBateauSelectionne(false);
                    }
                }
            }

            else {
                if(game.getMap().casePossedeBateau(c,game.getCurrentPlayer())){
                    game.setBateauSelectionne(game.getMap().bateauSurCase(c));
                }

            }
    }

    //Méthode qui finit un tour quand on appuie sur le bon bouton
    public void endTurn(){
        game.getCurrentPlayer().newTurn();
    }

}
