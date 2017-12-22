package fr.lesprogbretons.seawar.model;

public class DefaultMap extends Grille {

    public DefaultMap(){
        super(11,13);

        bateaux1.add(new Amiral(tableau[10][0]));
        bateaux1.add(new Fregate(tableau[10][1]));
        bateaux2.add(new Amiral(tableau[0][12]));
        bateaux2.add(new Fregate(tableau[1][12]));
        tableau[2][2]=new CaseTerre(4,3);
        tableau[1][3]=new CaseTerre(4,4);
        tableau[1][3]=new CaseTerre(3,6);
        tableau[1][3]=new CaseTerre(2,7);
        tableau[1][3]=new CaseTerre(5,2);
        tableau[1][3]=new CaseTerre(6,5);
        tableau[1][3]=new CaseTerre(6,6);
        tableau[1][3]=new CaseTerre(5,7);
        tableau[1][3]=new CaseTerre(5,8);
        tableau[1][3]=new CaseTerre(4,9);
        tableau[5][6].setPhare(true);
        tableau[2][2].setPhare(true);
        tableau[8][10].setPhare(true);
    }



}