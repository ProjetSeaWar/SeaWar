package fr.lesprogbretons.seawar;

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
}
