package fr.lesprogbretons.seawar.model;

public class Boat {

    protected boolean alive = true;

    protected int move;
    protected int moveAvalaible;                        // Déplacement encore disponible pendant le tour

    protected int hp;
    protected int dmgMainCanon;
    protected int dmgSecCanon;
    protected int shootTaken = 0;                       // Le nombre de tir qu'à déjà encaissé le bateau pendant le tour

    protected int reloadMainCanon;
    protected int reloadSecCanon;
    protected int mainCD = 0;                           // Nombre de tour avant la prochaine utilisation du canon principal
    protected int secCD = 0;

    public void loseHP(int dmg) {

        if (shootTaken == 1) {                          // Si le bateau a deja pris des degats pendant le tour
            if (hp - (3 * dmg) <= 0) {
                hp = 0;
                alive = false;
            } else {
                hp = hp - (3 * dmg);
            }

        } else {
            if (hp - dmg <= 0) {
                hp = 0;
                alive = false;
            } else {
                hp = hp - dmg;
            }
        }

        shootTaken++;

    }

    public void shootMainCanon(Boat target) {
        if (mainCD == 0) {
            target.loseHP(dmgMainCanon);
            mainCD = reloadMainCanon;
        } else {
            // TODO : Message indiquant que le canon principal est en cooldown
        }

    }

    public void shootSecCanon(Boat target) {
        if (secCD == 0) {
            target.loseHP(dmgSecCanon);
            secCD = reloadSecCanon;
        } else {
            // TODO : Message indiquant que le canon secondaire est en cooldown
        }
    }

    public void newTurn(){
        if(alive = false){
            this.endTurn();
        }

        moveAvalaible = move;

        if(mainCD > 0){
            mainCD --;
        }

        if(secCD > 0){
            secCD --;
        }
    }

    private void endTurn() {
        // TODO : Débuter le tour d'un autre bateau
    }

}

