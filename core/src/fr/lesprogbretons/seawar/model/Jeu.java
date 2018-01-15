package fr.lesprogbretons.seawar.model;

import java.util.*;

public class Jeu {
    private Grille grille;
    private int joueur;             // donne le numéro de joueur dont fait le tour

    public Jeu(){
        grille = new DefaultMap();

        joueur = 1;
    }

    public Grille getGrille() {
        return grille;
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }

    public int getJoueur() {
        return joueur;
    }

    public void setJoueur(int joueur) {
        this.joueur = joueur;
    }

    public void moveBoat(Grille map,Boat boat, Case destination){

        if(destination instanceof CaseEau) {
            ArrayList<Case> casesDispo = new ArrayList<>();

            map.getCasesDisponibles(boat.getPosition(), boat.getMoveAvailable(), casesDispo);

            if (casesDispo.contains(destination)) {
                boat.moveBoat(destination);
                System.out.println("Votre bateau s'est déplacé");

            } else {
                // TODO : indiquer au joueur qu'il veut aller trop loin
                System.out.println("Cette case est trop loin, vous ne pouvez y aller");
            }
        }

        else {
            // TODO : indiquer au joueur qu'il s'agit d'une case terre
            System.out.println("Vous ne pouvez vous déplacer sur une case terre");
        }
    }

    public void start (Grille g){
        boolean jeuFini=false,tourFini=false;
        int joueur=1;
        int mode;    //1:selection 2:deplacement

        Iterator<Boat> it1 = g.bateaux1.iterator();
        Iterator<Boat> it2 = g.bateaux2.iterator();

        while(!jeuFini){

            while(joueur==1){

            }
        }
    }


    public static void main(String[] args){
        Jeu jeu = new Jeu();

        jeu.grille.bateaux1.get(1).newTurn();

        jeu.moveBoat(jeu.grille,jeu.grille.bateaux1.get(1),jeu.grille.tableau[9][0]);

    }
}
