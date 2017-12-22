package fr.lesprogbretons.seawar.model;

public class Fregate extends Boat{

    public Fregate(){
        super();
        move = 7;
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

    public Fregate(Case c){
        super(c);
        move = 7;
        hp = 50;
        dmgMainCanon = 30;
        reloadMainCanon = 1;
        dmgSecCanon = 10;
        reloadSecCanon = 0;
    }
}
