package fr.lesprogbretons.seawar.model.boat;

import fr.lesprogbretons.seawar.model.cases.Case;
import fr.lesprogbretons.seawar.model.Player;

import java.io.Serializable;

/**
 * Classe Amiral : sous-classe de Boat
 */
public class Amiral extends Boat implements Serializable {

    public Amiral(Case c, Player p) {
        super(c, p);
        move = 3;
        moveAvailable = move;
        maxHp = 100;
        hp = maxHp;
        dmgMainCanon = 50;
        reloadMainCanon = 4;
        dmgSecCanon = 30;
        reloadSecCanon = 2;
    }

    @Override
    public String toString() {
        return "Amiral";
    }
}
