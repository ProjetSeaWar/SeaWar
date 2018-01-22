package fr.lesprogbretons.seawar.model;

import fr.lesprogbretons.seawar.model.map.*;
import fr.lesprogbretons.seawar.model.boat.*;

import java.io.Serializable;
import java.util.ArrayList;


public class Partie implements Serializable {

    //Carte
    private Grille map;

    //Joueurs
    private Player joueur1;

    private Player joueur2;

    //Joueur dont c'est le tour
    private Player currentPlayer;

    //Bateau sélectionné par le joueur
    private Boat bateauSelectionne;
    private boolean isAnyBateauSelectionne = false;

    //Pour savoir si la partie est finie
    private boolean fin = false;

    //Vainqueur
    private Player winner;

    //Type de victoire
    private VictoryType victoryType;

    //tours
    private int turnCounter = 1;
    private boolean isPlayer2 = false;

    //Liste de bateaux qui a deja joué pendant le tour
    private ArrayList<Boat> bateauxDejaDeplaces = new ArrayList<>();

    //Constructeurs
    public Partie() {
        map = new DefaultMap();
        joueur1 = map.getJoueur1();
        joueur2 = map.getJoueur2();
        currentPlayer = joueur1;
    }

    public Partie(Grille map) {
        this.map = map;
    }

    /*---------------------------------------------------------------------------------------*/
    //Getters & Setters
    public Grille getMap() {
        return map;
    }

    public void setMap(Grille map) {
        this.map = map;
    }

    public Player getJoueur1() {
        return joueur1;
    }

    public ArrayList<Boat> getBateauxDejaDeplaces() {
        return bateauxDejaDeplaces;
    }

    public void setBateauxDejaDeplaces(ArrayList<Boat> bateauxDejaDeplaces) {
        this.bateauxDejaDeplaces = bateauxDejaDeplaces;
    }

    public void ajouterBateauxDejaDeplaces(Boat b) {
        bateauxDejaDeplaces.add(b);
    }

    public Player getJoueur2() {
        return joueur2;
    }

    public int getTurnCounter() {
        return turnCounter;
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
        isAnyBateauSelectionne = true;
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

    public Player getWinner() {
        return winner;
    }

    private void setWinner(Player winner) {
        this.winner = winner;
    }

    private void setFin(boolean fin) {
        this.fin = fin;
    }

    public VictoryType getVictoryType() {
        return victoryType;
    }

    private void setVictoryType(VictoryType victoryType) {
        this.victoryType = victoryType;
    }

    /*----------------------------------------------------------------------------------------------------------*/


    /**
     * Fonction renvoyant le joueur dont ce n'est pas le tour
     *
     * @return Player dont ce n'est pas le tour
     */
    public Player getOtherPlayer() {
        if (getCurrentPlayer().getNumber() == 1) {
            return joueur2;
        } else {
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
            setVictoryType(VictoryType.TAKE);
        } else if (getJoueur2().getPharesPossedes() == 3) {
            setFin(true);
            setWinner(getJoueur2());
            setVictoryType(VictoryType.TAKE);
        }

        //Si tous les bateaux du joueur 2 sont détruits, c'est que le joueur 1 gagne
        else if (getCurrentPlayer().equals(getJoueur1())) {
            boolean fin = true;

            if(map.getBateaux2().size()>=0){
                fin = false;
            }

            if (fin) {
                setFin(true);
                setWinner(joueur1);
                setVictoryType(VictoryType.DESTROY);
            }
        }

        //Si tous les bateaux du joueur 1 sont détruits, c'est que le joueur 2 gagne
        else if (getCurrentPlayer().equals(getJoueur2())) {
            boolean fin = true;

            if(map.getBateaux1().size()>=0){
                fin = false;
            }

            if (fin) {
                setFin(true);
                setWinner(joueur2);
                setVictoryType(VictoryType.DESTROY);
            }
        }
    }


    /**
     * Fonction qui gere la fin d'un tour d'un joueur
     *
     * @return : false si le joueur n'a pas déplacé tous ses bateaux, true si le tour a changé
     */
    public boolean endTurn() {
        boolean bateauxDeplaces = true;

        //On vérifie que le joueur courrant a déplacé tous ses bateaux ou que ses bateaux ne sont pas bloques ou detruits
        if (getCurrentPlayer().equals(joueur1)) {
            for (int i = 0; i < map.getBateaux1().size(); i++) {
                if (map.getBateaux1().get(i).getMoveAvailable() == map.getBateaux1().get(i).getMove() && map.getCasesDisponibles(map.getBateaux1().get(i).getPosition(), 1).size() != 0) {
                    bateauxDeplaces = false;
                }
            }
        } else {
            for (int i = 0; i < map.getBateaux2().size(); i++) {
                if (map.getBateaux2().get(i).getMoveAvailable() == map.getBateaux2().get(i).getMove() && map.getCasesDisponibles(map.getBateaux2().get(i).getPosition(), 1).size() != 0) {
                    bateauxDeplaces = false;
                }
            }
        }

        //On remet les caractéristiques des bateaux pour le prochain tour
        if (bateauxDeplaces) {
            if (getCurrentPlayer().equals(joueur1)) {
                for (int i = 0; i < map.getBateaux1().size(); i++) {
                    map.getBateaux1().get(i).endTurn();

                }
                for (int i = 0; i < map.getBateaux2().size(); i++) {
                    map.getBateaux2().get(i).setShootTaken(0);
                }

            } else {
                for (int i = 0; i < map.getBateaux2().size(); i++) {
                    map.getBateaux2().get(i).endTurn();
                }
                for (int i = 0; i < map.getBateaux1().size(); i++) {
                    map.getBateaux1().get(i).setShootTaken(0);
                }
            }

            bateauxDejaDeplaces = new ArrayList<>();

            setCurrentPlayer(getOtherPlayer());
            if (isPlayer2) {
                turnCounter++;
                isPlayer2 = false;
            } else {
                isPlayer2 = true;
            }

            return true;
        }

        //Si le joueur n'a pas déplacé tous ses bateaux
        else {
            return false;
        }
    }
}
