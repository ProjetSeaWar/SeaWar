package fr.lesprogbretons.seawar.model.boat;

import fr.lesprogbretons.seawar.model.cases.Case;
import fr.lesprogbretons.seawar.model.Player;

/**
 * Classe Fregate : sous-classe de Boat
 */
public class Fregate extends Boat {

    public Fregate(Case c, Player p) {
        super(c, p);
        move = 7;
        moveAvailable = move;
        hp = 50;
        dmgMainCanon = 30;
        reloadMainCanon = 1;
        dmgSecCanon = 10;
        reloadSecCanon = 0;
    }

    @Override
    public String toString() {
        return "Fregate{" +
                "position=" + position +
                '}';
    }
}
