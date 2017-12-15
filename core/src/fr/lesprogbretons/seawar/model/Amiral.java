package fr.lesprogbretons.seawar.model;

public class Amiral extends Boat{

    public Amiral(){
        super();
        move = 3;
        hp = 100;
        dmgMainCanon = 50;
        reloadMainCanon = 3;
        dmgSecCanon = 30;
        reloadSecCanon = 1;
    }
}
