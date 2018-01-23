package fr.lesprogbretons.seawar.model.boat;

import fr.lesprogbretons.seawar.model.Player;
import fr.lesprogbretons.seawar.model.cases.Case;

import java.io.Serializable;

/**
 * Classe Fregate : sous-classe de Boat
 */
public class Fregate extends Boat implements Serializable {

    public Fregate(Case c, Player p) {
        super(c, p);
        move = 7;
        moveAvailable = move;
        maxHp = 50;
        hp = maxHp;
        dmgMainCanon = 30;
        reloadMainCanon = 1;
        dmgSecCanon = 10;
        reloadSecCanon = 0;
    }

    @Override
    public String toString() {
        return "Fregate";
    }
}
