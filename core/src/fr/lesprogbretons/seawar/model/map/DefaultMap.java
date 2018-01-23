package fr.lesprogbretons.seawar.model.map;

import fr.lesprogbretons.seawar.model.Orientation;
import fr.lesprogbretons.seawar.model.boat.Amiral;
import fr.lesprogbretons.seawar.model.boat.Fregate;
import fr.lesprogbretons.seawar.model.cases.CaseTerre;

import java.io.Serializable;

/**
 * Classe DefaultMap : il s'agit de la carte par défaut implémentée dans le jeu
 */
public class DefaultMap extends Grille implements Serializable {

    public DefaultMap() {
        super(11, 13);

        bateaux1.add(new Amiral(tableau[10][0], getJoueur1()));
        bateaux1.get(0).setOrientation(Orientation.SUDEST);
        bateaux1.add(new Fregate(tableau[10][1], getJoueur1()));
        bateaux1.get(1).setOrientation(Orientation.SUDEST);


        bateaux2.add(new Amiral(tableau[1][12], getJoueur2()));
        bateaux2.get(0).setOrientation(Orientation.NORDOUEST);
        bateaux2.add(new Fregate(tableau[0][12], getJoueur2()));
        bateaux2.get(1).setOrientation(Orientation.NORDOUEST);

        tableau[2][8] = new CaseTerre(2, 8);
        tableau[3][8] = new CaseTerre(3, 8);
        tableau[5][8] = new CaseTerre(5, 8);
        tableau[6][8] = new CaseTerre(6, 8);
        tableau[8][8] = new CaseTerre(8, 8);
        tableau[8][9] = new CaseTerre(8, 9);
        tableau[5][10] = new CaseTerre(5, 10);
        tableau[4][11] = new CaseTerre(4, 11);
        tableau[5][11] = new CaseTerre(5, 11);
        tableau[5][12] = new CaseTerre(5, 12);
        tableau[5][0] = new CaseTerre(5, 0);
        tableau[4][1] = new CaseTerre(4, 1);
        tableau[5][1] = new CaseTerre(5, 1);
        tableau[5][2] = new CaseTerre(5, 2);
        tableau[6][2] = new CaseTerre(6, 2);
        tableau[5][3] = new CaseTerre(5, 3);
        tableau[2][4] = new CaseTerre(2, 4);
        tableau[3][4] = new CaseTerre(3, 4);
        tableau[8][4] = new CaseTerre(8, 4);
        tableau[9][4] = new CaseTerre(9, 4);
        tableau[3][5] = new CaseTerre(3, 5);
        tableau[7][5] = new CaseTerre(7, 5);
        tableau[9][5] = new CaseTerre(9, 5);
        tableau[10][5] = new CaseTerre(10, 5);
        tableau[4][6] = new CaseTerre(4, 6);
        tableau[7][6] = new CaseTerre(7, 6);
        tableau[0][7] = new CaseTerre(0, 7);
        tableau[1][7] = new CaseTerre(1, 7);
        tableau[3][7] = new CaseTerre(3, 7);
        tableau[7][7] = new CaseTerre(7, 7);

        tableau[8][5].setPhare(true);
        tableau[2][7].setPhare(true);
        tableau[5][7].setPhare(true);
    }
}