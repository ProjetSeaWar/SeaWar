package fr.lesprogbretons.seawar.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Grille {
    protected ArrayList<Boat> bateaux1;
    protected ArrayList<Boat> bateaux2;
    protected Case tableau[][];
    protected int hauteur;
    protected int largeur;

    public Grille(int hauteur,int largeur){
        this.hauteur = hauteur;
        this.largeur = largeur;
        bateaux1=new ArrayList<>();
        bateaux2=new ArrayList<>();
        tableau=new Case[hauteur][largeur];

        for(int i=0;i<hauteur;i++){
            for(int j=0;j<largeur;j++){
                tableau[i][j]=new CaseEau(i,j);
            }
        }
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
        if(!(tab.contains(c)) &&!(c  instanceof CaseTerre)) {
            tab.add(c);
        }

        if (range != 0 && !(c  instanceof CaseTerre)) {
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

    public ArrayList<Boat> getBateaux1() {
        return bateaux1;
    }

    public void setBateaux1(ArrayList<Boat> bateaux1) {
        this.bateaux1 = bateaux1;
    }

    public ArrayList<Boat> getBateaux2() {
        return bateaux2;
    }

    public void setBateaux2(ArrayList<Boat> bateaux2) {
        this.bateaux2 = bateaux2;
    }

    public static void main(String[] args) {
        Grille grille = new DefaultMap();
        //System.out.println(grille);

        Case a = grille.getCase(0, 1);


      /*System.out.println(a);
        System.out.println(grille.getCaseBas(a));
        System.out.println(grille.getCaseGaucheb(a));
        System.out.println(grille.getCaseGaucheh(a));
        System.out.println(grille.getCaseHaut(a));
        System.out.println(grille.getCaseDroiteh(a));
        System.out.println(grille.getCaseDroiteb(a));*/

        ArrayList<Case> tab = new ArrayList<>();

        grille.getCasesDisponibles(a, 3, tab);

        for(int i=0; i<13;i++){
            System.out.println(tab.get(i));
        }

    }
}
