package fr.lesprogbretons.seawar.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Grille {

    private Case tableau[][];
    private int hauteur;
    private int largeur;

    public Grille(){
        largeur = 11;
        hauteur = 13;

        int i,j;

        tableau=new Case[largeur][hauteur];

        for(i=0;i<11;i++){
            for(j=0;j<13;j++){
                tableau[i][j]=new CaseEau(i,j);
            }
        }

        tableau[5][6]=new CaseTerre((5),(6));
    }

    public Grille(int largeur,int hauteur){
        tableau = new Case[largeur][hauteur];
    }

    public Case getCase(int largeur, int hauteur){
        return tableau[largeur][hauteur];
    }

    public Case getCaseHaut(Case c){
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        cas=getCase((x+1),y);
        return cas;
    }
    public Case getCaseBas(Case c){
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        cas=getCase((x-1),y);
        return cas;
    }
    public Case getCaseDroiteh(Case c){
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        if (y%2==0){
            cas=getCase(x,(y+1));
        }
        else {
            cas = getCase((x+1), (y + 1));
        }
        return cas;
    }
    public Case getCaseDroiteb(Case c){
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        if (y%2==0){
            cas=getCase((x-1),(y+1));
        }
        else {
            cas = getCase(x,(y+1) );
        }
        return cas;
    }
    public Case getCaseGaucheh(Case c){
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        if (y%2==0){
            cas=getCase(x,(y-1));
        }
        else {
            cas = getCase((x+1), (y-1));
        }
        return cas;
    }
    public Case getCaseGaucheb(Case c){
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        if (y%2==0){
            cas=getCase((x-1),(y-1));
        }
        else {
            cas = getCase(x,(y-1));
        }
        return cas;
    }

    public void getCasesDisponibles(Case c, int range, ArrayList<Case> tab){
        if(!(tab.contains(c))) {
            tab.add(c);
        }

        if (range != 0) {
            getCasesDisponibles(getCaseBas(c), (range - 1), tab);
            getCasesDisponibles(getCaseDroiteb(c), (range - 1), tab);
            getCasesDisponibles(getCaseDroiteh(c), (range - 1), tab);
            getCasesDisponibles(getCaseGaucheb(c), (range - 1), tab);
            getCasesDisponibles(getCaseGaucheh(c), (range - 1), tab);
            getCasesDisponibles(getCaseHaut(c), (range - 1), tab);
        }
    }

    /* TODO : toString de la grille
    @Override
    public String toString() {
        return "Grille{" +
                "tableau=" +
                '}';
    }*/

    public static void main(String[] args) {
        Grille grille = new Grille();
        //System.out.println(grille);
        Case a = grille.getCase(0, 0);
      /*System.out.println(a);
        System.out.println(grille.getCaseBas(a));
        System.out.println(grille.getCaseGaucheb(a));
        System.out.println(grille.getCaseGaucheh(a));
        System.out.println(grille.getCaseHaut(a));
        System.out.println(grille.getCaseDroiteh(a));
        System.out.println(grille.getCaseDroiteb(a));*/

        ArrayList<Case> tab = new ArrayList<>();

        grille.getCasesDisponibles(a, 2, tab);

        for(int i=0; i<19;i++){
            System.out.println(tab.get(i));
        }

    }
}
