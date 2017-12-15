package fr.lesprogbretons.seawar.model;

import java.util.Arrays;

public class Grille {
    Case tableau[][];
    public Grille(){
        int i,j;
        tableau=new Case[11][13];
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
        cas=getCase(x,(y+1));
        return cas;
    }
    public Case getCaseBas(Case c){
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        cas=getCase(x,(y-1));
        return cas;
    }
    public Case getCaseDroiteh(Case c){
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        if (y%2==0){
            cas=getCase((x+1),y);
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
            cas=getCase((x+1),(y-1));
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

    @Override
    public String toString() {
        return "Grille{" +
                "tableau=" + Arrays.toString(tableau) +
                '}';
    }

    public static void main(String[] args){
        Grille grille =new Grille();
        System.out.println(grille.toString());
        Case a=grille.getCase(4,5);
        System.out.println(a.toString());
        System.out.println(grille.getCaseBas(a));
        System.out.println(grille.getCaseGaucheb(a));
        System.out.println(grille.getCaseGaucheh(a));
        System.out.println(grille.getCaseHaut(a));
        System.out.println(grille.getCaseDroiteh(a));
        System.out.println(grille.getCaseDroiteb(a));



    }
}
