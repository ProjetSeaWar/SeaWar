package fr.lesprogbretons.seawar.model;

public class Fregate extends Boat{



    @Override
    public String toString() {
        return "Fregate{" +
                "position=" + position +
                '}';
    }

    public Fregate(Case c, Player p){
        super(c,p);
        move = 7;
        hp = 50;
        dmgMainCanon = 30;
        reloadMainCanon = 1;
        dmgSecCanon = 10;
        reloadSecCanon = 0;
    }
}
