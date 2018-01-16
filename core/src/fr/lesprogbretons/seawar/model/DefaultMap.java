package fr.lesprogbretons.seawar.model;

public class DefaultMap extends Grille {

    public DefaultMap(){
        super(11,13);

        bateaux1.add(new Amiral(tableau[10][0],joueur1));
        bateaux1.add(new Fregate(tableau[10][1],joueur1));
        bateaux2.add(new Amiral(tableau[0][12],joueur2));
        bateaux2.add(new Fregate(tableau[1][12],joueur2));
        tableau[4][3]=new CaseTerre(4,3);
        tableau[4][4]=new CaseTerre(4,4);
        tableau[3][6]=new CaseTerre(3,6);
        tableau[2][7]=new CaseTerre(2,7);
        tableau[5][2]=new CaseTerre(5,2);
        tableau[6][5]=new CaseTerre(6,5);
        tableau[6][6]=new CaseTerre(6,6);
        tableau[5][7]=new CaseTerre(5,7);
        tableau[5][8]=new CaseTerre(5,8);
        tableau[4][9]=new CaseTerre(4,9);
        tableau[5][6].setPhare(true);
        tableau[2][2].setPhare(true);
        tableau[8][10].setPhare(true);
    }



}