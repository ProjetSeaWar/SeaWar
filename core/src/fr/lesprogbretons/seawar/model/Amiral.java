package fr.lesprogbretons.seawar.model;

public class Amiral extends Boat{

    @Override
    public String toString() {
        return "Amiral{" +
                "position=" + position +
                '}';
    }

    public Amiral(){
        super();
        move = 3;
        hp = 100;
        dmgMainCanon = 50;
        reloadMainCanon = 3;
        dmgSecCanon = 30;
        reloadSecCanon = 1;
    }
    public Amiral(Case c){
        super(c);
        move = 3;
        hp = 100;
        dmgMainCanon = 50;
        reloadMainCanon = 3;
        dmgSecCanon = 30;
        reloadSecCanon = 1;
    }
}
