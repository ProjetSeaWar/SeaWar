package fr.lesprogbretons.seawar.model;

public class DefaultMap extends Grille {

    public DefaultMap(){
        super(11,13);
        bateaux1.add(new Amiral(tableau[10][0]));
        bateaux1.add(new Fregate(tableau[10][1]));
        bateaux2.add(new Amiral(tableau[0][12]));
        bateaux2.add(new Amiral(tableau[1][12]));
    }
}