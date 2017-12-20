package fr.lesprogbretons.seawar.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Grille {

    private Case tableau[][];
    private int hauteur;
    private int largeur;

    public Grille(){
        largeur = 13;
        hauteur = 11;

        int i,j;

        tableau=new Case[hauteur][largeur];

        for(i=0;i<11;i++){
            for(j=0;j<13;j++){
                tableau[i][j]=new CaseEau(i,j);
            }
        }

        tableau[5][6]=new CaseTerre((5),(6));
    }

    public Grille(int hauteur,int largeur){
        tableau = new Case[hauteur][largeur];
    }

    public Case getCase(int hauteur,int largeur){
        return tableau[hauteur][largeur];
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

    public void getCasesDisponible(Case c, int range, ArrayList<Case> tab){
        if(!(tab.contains(c))) {
            tab.add(c);
        }

        if (range != 0) {
            if(c.getX()>0) {
                getCasesDisponible(getCaseBas(c), (range - 1), tab);
            }
            if(c.getY()<largeur-1) {
                if((c.getY()%2==0 && c.getX()>0)|| c.getY()%2==1) {
                    getCasesDisponible(getCaseDroiteb(c), (range - 1), tab);
                }
            }
            if(c.getY()<largeur-1) {
                if ((c.getY() % 2 == 1 && c.getX() < hauteur-1)|| c.getY()%2==0) {
                    getCasesDisponible(getCaseDroiteh(c), (range - 1), tab);
                }
            }
            if(c.getY()>0) {
                if ((c.getY() % 2 == 0 && c.getX() > 0)|| c.getY()%2==1) {
                    getCasesDisponible(getCaseGaucheb(c), (range - 1), tab);
                }
            }
            if(c.getY()>0) {
                if ((c.getY() % 2 == 1 && c.getX() <hauteur-1) || c.getY()%2==0 ) {
                    getCasesDisponible(getCaseGaucheh(c), (range - 1), tab);
                }
            }
            if(c.getX()<hauteur-1) {
                getCasesDisponible(getCaseHaut(c), (range - 1), tab);
            }
        }
    }
    public void getCasesDisponibles(Case c, int range, ArrayList<Case> tab){
        getCasesDisponible(c,range,tab);
        tab.remove(0);
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

        Case a = grille.getCase(4, 5);


      /*System.out.println(a);
        System.out.println(grille.getCaseBas(a));
        System.out.println(grille.getCaseGaucheb(a));
        System.out.println(grille.getCaseGaucheh(a));
        System.out.println(grille.getCaseHaut(a));
        System.out.println(grille.getCaseDroiteh(a));
        System.out.println(grille.getCaseDroiteb(a));*/

        ArrayList<Case> tab = new ArrayList<>();

        grille.getCasesDisponibles(a, 1, tab);

        for(int i=0; i<6;i++){
            System.out.println(tab.get(i));
        }

    }
}
