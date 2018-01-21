package fr.lesprogbretons.seawar.model.map;

import fr.lesprogbretons.seawar.model.Orientation;
import fr.lesprogbretons.seawar.model.boat.Amiral;
import fr.lesprogbretons.seawar.model.boat.Fregate;
import fr.lesprogbretons.seawar.model.cases.CaseTerre;

/**
 * Classe DefaultMap : il s'agit de la carte par défaut implémentée dans le jeu
 */
public class DefaultMap extends Grille {

    public DefaultMap() {
        super(11, 13);

        bateaux1.add(new Amiral(tableau[10][0], getJoueur1()));
        bateaux1.get(0).setOrientation(Orientation.SUDEST);
        bateaux1.add(new Fregate(tableau[10][1], getJoueur1()));
        bateaux1.get(1).setOrientation(Orientation.SUDEST);
        bateaux2.add(new Amiral(tableau[0][12], getJoueur2()));
        bateaux2.get(0).setOrientation(Orientation.NORDOUEST);
        bateaux2.add(new Fregate(tableau[1][12], getJoueur2()));
        bateaux2.get(1).setOrientation(Orientation.NORDOUEST);

        tableau[4][3] = new CaseTerre(4, 3);
        tableau[4][4] = new CaseTerre(4, 4);
        tableau[3][6] = new CaseTerre(3, 6);
        tableau[2][7] = new CaseTerre(2, 7);
        tableau[5][2] = new CaseTerre(5, 2);
        tableau[6][5] = new CaseTerre(6, 5);
        tableau[6][6] = new CaseTerre(6, 6);
        tableau[5][7] = new CaseTerre(5, 7);
        tableau[5][8] = new CaseTerre(5, 8);
        tableau[4][9] = new CaseTerre(4, 9);
        tableau[5][6].setPhare(true);
        tableau[2][2].setPhare(true);
        tableau[8][10].setPhare(true);
    }
}