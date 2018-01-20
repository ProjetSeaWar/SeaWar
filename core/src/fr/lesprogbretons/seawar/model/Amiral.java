package fr.lesprogbretons.seawar.model;

public class Amiral extends Boat {

    public Amiral(Case c, Player p) {
        super(c, p);
        move = 3;
        hp = 100;
        dmgMainCanon = 50;
        reloadMainCanon = 3;
        dmgSecCanon = 30;
        reloadSecCanon = 1;
    }

    @Override
    public String toString() {
        return "Amiral{" +
                "position=" + position +
                '}';
    }
}