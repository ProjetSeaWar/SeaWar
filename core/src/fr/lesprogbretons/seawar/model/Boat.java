package fr.lesprogbretons.seawar.model;

public abstract class Boat {

    protected boolean alive = true;

    protected int move;
    protected int moveAvailable;                        // Déplacement encore disponible pendant le tour
    protected Case position;

    protected int hp;
    protected int dmgMainCanon;
    protected int dmgSecCanon;
    protected int shootTaken = 0;                       // Le nombre de tir qu'à déjà encaissé le bateau pendant le tour
    protected int canonSelectionne = 1;                 // 1 pour le principal et 2 pour le secondaire

    protected int reloadMainCanon;

    protected int reloadSecCanon;
    protected int mainCD = 0;                           // Nombre de tour avant la prochaine utilisation du canon principal
    protected int secCD = 0;

    protected Player joueur;

    protected Orientation orientation;                        //0 : Nord, 1 : Nord-Est,   2: Sud-Est, 3 : Sud,    4 : Sud-Ouest,  5 : Nord-Ouest


    /////////////////////////////////////////////////////////////////////////////////////////////:


    public Boat(Case position,Player p){
        this.position = position;
        this.joueur = p;
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

    public Player getJoueur() {
        return joueur;
    }

    public Case getPosition(){
        return position;
    }

    public int getCanonSelectionne() {
        return canonSelectionne;
    }

    public void setCanonSelectionne(int canonSelectionne) {
        this.canonSelectionne = canonSelectionne;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "Boat{" +
                "position=" + position +
                '}';
    }


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

    public void endTurn(){
        moveAvailable = move;

        if(mainCD > 0){
            mainCD --;
        }

        if(secCD > 0){
            secCD --;
        }

        shootTaken = 0;
    }

    public void moveBoat(Case destination){                         //Le bateau ne peut se déplacer que d'une case à la fois
        moveAvailable--;

        if(destination.getX() == position.getX()+1 && destination.getY() == position.getY()){
            orientation = Orientation.NORD;
        }

        if(destination.getX() == position.getX()-1 && destination.getY() == position.getY()){
            orientation = Orientation.SUD;
        }

        if(destination.getX()==position.getX() && destination.getY()==position.getY()+1){
            if(position.getY()%2==0){
                orientation = Orientation.NORDEST;
            }

            else {
                orientation = Orientation.SUDEST;
            }
        }

        if(destination.getX()== position.getX() && destination.getY()==position.getY()-1){
            if(position.getY()%2==0){
                orientation = Orientation.NORDOUEST;
            }

            else {
                orientation = Orientation.SUDOUEST;
            }
        }

        if(destination.getY()==position.getY()+1 && destination.getX() == position.getX()+1){
            orientation = Orientation.NORDEST;
        }

        if(destination.getX()==position.getX()-1 && destination.getY() == destination.getY()-1){
            orientation = Orientation.SUDOUEST;
        }

        if(destination.getY()==position.getY()+1 && destination.getX() == position.getX()-1){
            orientation = Orientation.SUDEST;
        }

        if(destination.getX()==position.getX()+1 && destination.getY()==position.getY()-1){
            orientation = Orientation.NORDOUEST;
        }

        this.position = destination;
    }

    public static void main(String[] args){
       // Fregate fregate = new Fregate();
       // Amiral amiral = new Amiral();

       // System.out.println("Fregate a " +  fregate.hp + " hp.");
       // System.out.println("Amiral a "+amiral.hp+ " hp.");

       // fregate.shootMainCanon(amiral);
        //System.out.println("Fregate attaque amiral avec son canon principal");
       // System.out.println("Amiral : - " + fregate.dmgMainCanon + " hp.");
       // System.out.println("Amiral a " + amiral.hp +" hp.");
    }

}

