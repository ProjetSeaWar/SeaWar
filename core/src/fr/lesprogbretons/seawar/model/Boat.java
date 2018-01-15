package fr.lesprogbretons.seawar.model;

public class Boat {

    protected boolean alive = true;

    protected int move;
    protected int moveAvailable;                        // Déplacement encore disponible pendant le tour
    protected Case position;

    protected int hp;
    protected int dmgMainCanon;
    protected int dmgSecCanon;
    protected int shootTaken = 0;                       // Le nombre de tir qu'à déjà encaissé le bateau pendant le tour
    protected int canonSelectionne = 0;                 // 1 pour le principal et 2 pour le secondaire

    protected int reloadMainCanon;

    public int getCanonSelectionne() {
        return canonSelectionne;
    }

    public void setCanonSelectionne(int canonSelectionne) {
        this.canonSelectionne = canonSelectionne;
    }

    protected int reloadSecCanon;
    protected int mainCD = 0;                           // Nombre de tour avant la prochaine utilisation du canon principal
    protected int secCD = 0;

    protected boolean tourTermine = false;

    public Boat(){
    }

    @Override
    public String toString() {
        return "Boat{" +
                "position=" + position +
                '}';
    }

    public Boat(Case position){
        this.position = position;
    }

    ////////////////// GETTER POUR PERMETTRE DE VOIR LES STATS EN JEU ////////////////////////////////////
    public int getMove() {
        return move;
    }

    public int getMoveAvailable() {
        return moveAvailable;
    }

    public int getHp() {
        return hp;
    }

    public int getDmgMainCanon() {
        return dmgMainCanon;
    }

    public int getDmgSecCanon() {
        return dmgSecCanon;
    }

    public int getReloadMainCanon() {
        return reloadMainCanon;
    }

    public int getReloadSecCanon() {
        return reloadSecCanon;
    }

    public int getMainCD() {
        return mainCD;
    }

    public int getSecCD() {
        return secCD;
    }

    public Case getPosition(){
        return position;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    public void shoot(Boat target){
        if(this.canonSelectionne==1){
            this.shootMainCanon(target);
        }
        else{
            this.shootSecCanon(target);
        }
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
        tourTermine = false;

        if(alive = false){
            this.endTurn();
        }

        moveAvailable = move;

        if(mainCD > 0){
            mainCD --;
        }

        if(secCD > 0){
            secCD --;
        }

        shootTaken = 0;
    }

    private void endTurn() {
        tourTermine = true;
        // TODO : Débuter le tour d'un autre bateau
    }

    public void moveBoat(Case destination){
        this.position = destination;
    }

    public static void main(String[] args){
        Fregate fregate = new Fregate();
        Amiral amiral = new Amiral();

        System.out.println("Fregate a " +  fregate.hp + " hp.");
        System.out.println("Amiral a "+amiral.hp+ " hp.");

        fregate.shootMainCanon(amiral);
        System.out.println("Fregate attaque amiral avec son canon principal");
        System.out.println("Amiral : - " + fregate.dmgMainCanon + " hp.");
        System.out.println("Amiral a " + amiral.hp +" hp.");
    }

}

