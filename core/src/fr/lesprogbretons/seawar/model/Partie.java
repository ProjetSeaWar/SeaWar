package fr.lesprogbretons.seawar.model;

import fr.lesprogbretons.seawar.model.map.*;
import fr.lesprogbretons.seawar.model.boat.*;

/**
 * Classe Partie
 */
public class Partie {

    //Carte
    private Grille map;

    //Joueurs
    private Player joueur1 = map.getJoueur1();
    private Player joueur2 = map.getJoueur2();

    //Joueur dont c'est le tour
    private Player currentPlayer = joueur1;

    //Bateau sélectionné par le joueur
    private Boat bateauSelectionne;
    private boolean isAnyBateauSelectionne = false;

    //Pour savoir si la partie est finie
    private boolean fin = false;

    //Vainqueur
    private Player winner;


    //Constructeurs
    public Partie(){
        map = new DefaultMap();
    }

    public Partie(Grille map){
        this.map = map;
    }

    /*---------------------------------------------------------------------------------------*/
    //Getters & Setters
    public Grille getMap() {
        return map;
    }

    public Player getJoueur1() {
        return joueur1;
    }

    public Player getJoueur2() {
        return joueur2;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Boat getBateauSelectionne() {
        return bateauSelectionne;
    }

    public void setBateauSelectionne(Boat bateauSelectionne) {
        this.bateauSelectionne = bateauSelectionne;
    }

    public boolean isAnyBateauSelectionne() {
        return isAnyBateauSelectionne;
    }

    public void setAnyBateauSelectionne(boolean anyBateauSelectionne) {
        isAnyBateauSelectionne = anyBateauSelectionne;
    }

    public boolean isFin() {
        return fin;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }
    /*----------------------------------------------------------------------------------------------------------*/


    /**
     * Fonction renvoyant le joueur dont ce n'est pas le tour
     * @return Player dont ce n'est pas le tour
     */
    public Player getOtherPlayer(){
        if(getCurrentPlayer().getNumber()==1){
            return joueur2;
        }

        else{
            return joueur1;
        }
    }


    /**
     * Procédure qui vérifie si la partie est terminée ou non
     */
    public void finPartie() {

        //Si le joueur a 3 phares, il gagne
        if (getJoueur1().getPharesPossedes() == 3) {
            setFin(true);
            setWinner(getJoueur1());
        } else if (getJoueur2().getPharesPossedes() == 3) {
            setFin(true);
            setWinner(getJoueur2());
        }

        //Si tous les bateaux du joueur 2 sont détruits, c'est que le joueur 1 gagne
        else if(getCurrentPlayer().equals(getJoueur1())){
            boolean fin = true;

            for(int i = 0; i< map.getBateaux2().size(); i++){
                if(map.getBateaux2().get(i).isAlive()){
                    fin = false;
                }
            }

            if(fin){
                setFin(true);
                setWinner(joueur1);
            }
        }

        //Si tous les bateaux du joueur 1 sont détruits, c'est que le joueur 2 gagne
        else if(getCurrentPlayer().equals(getJoueur2())){
            boolean fin = true;

            for(int i = 0; i< map.getBateaux1().size(); i++){
                if(map.getBateaux1().get(i).isAlive()){
                    fin = false;
                }
            }

            if(fin){
                setFin(true);
                setWinner(joueur2);
            }
        }
    }


    /**
     * Fonction qui gere la fin d'un tour d'un joueur
     * @return : false si le joueur n'a pas déplacé tous ses bateaux, true si le tour a changé
     */
    public boolean endTurn(){
        boolean bateauxDeplaces = true;

        //On vérifie que le joueur courrant a déplacé tous ses bateaux
        if(getCurrentPlayer().equals(joueur1)){
            for(int i = 0; i < map.getBateaux1().size() ; i++ ){
                if(map.getBateaux1().get(i).getMoveAvailable() == map.getBateaux1().get(i).getMove()){
                    bateauxDeplaces = false;
                }
            }
        }

        else {
            for(int i = 0; i < map.getBateaux2().size() ; i++ ){
                if(map.getBateaux2().get(i).getMoveAvailable() == map.getBateaux2().get(i).getMove()){
                    bateauxDeplaces = false;
                }
            }
        }

        //On remet les caractéristiques des bateaux pour le prochain tour
        if(bateauxDeplaces) {
            if (getCurrentPlayer().equals(joueur1)) {
                for (int i = 0; i < map.getBateaux1().size(); i++) {
                    map.getBateaux1().get(i).endTurn();
                    map.getBateaux2().get(i).setShootTaken(0);
                }
            } else {
                for (int i = 0; i < map.getBateaux2().size(); i++) {
                    map.getBateaux2().get(i).endTurn();
                    map.getBateaux1().get(i).setShootTaken(0);

                }
            }

            setCurrentPlayer(getOtherPlayer());
            return true;
        }

        //Si le joueur n'a pas déplacé tous ses bateaux
        else {
            return false;
        }
    }
}
