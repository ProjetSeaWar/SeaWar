package fr.lesprogbretons.seawar.model.boat;

import fr.lesprogbretons.seawar.model.cases.*;
import fr.lesprogbretons.seawar.model.Player;
import fr.lesprogbretons.seawar.model.Orientation;

import static fr.lesprogbretons.seawar.SeaWar.logger;

/**
 * Classe Boat
 */
public abstract class Boat {

    //En vie ou non
    protected boolean alive = true;


    //Position et déplacement
    protected int move;
    protected int moveAvailable;                        // Déplacement encore disponible pendant le tour
    protected Case position;
    protected Orientation orientation;                        //0 : Nord, 1 : Nord-Est,   2: Sud-Est, 3 : Sud,    4 : Sud-Ouest,  5 : Nord-Ouest

    //Combat
    protected int hp;
    protected int dmgMainCanon;
    protected int dmgSecCanon;
    protected int shootTaken = 0;                       // Le nombre de tir qu'à déjà encaissé le bateau pendant le tour
    protected int canonSelectionne = 1;                 // 1 pour le principal et 2 pour le secondaire
    protected int reloadMainCanon;
    protected int reloadSecCanon;
    protected int mainCD = 0;                           // Nombre de tour avant la prochaine utilisation du canon principal
    protected int secCD = 0;

    //Joueur possédant ce bateau
    protected Player joueur;


    /**
     * Constructeur
     * @param position : case initial
     * @param p : joueur possédant ce bateau
     */
    public Boat(Case position,Player p){
        this.position = position;
        this.joueur = p;
    }

    /*----------------------------------------------------------------------------*/
    //Getters & Setters
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

    public int getShootTaken() {
        return shootTaken;
    }

    public void setShootTaken(int shootTaken) {
        this.shootTaken = shootTaken;
    }

    /*---------------------------------------------------------------------------------------------*/
    //toString
    @Override
    public String toString() {
        return "Boat{" +
                "position=" + position +
                '}';
    }


    /**
     * Procédure qui s'occupe de réduire les HP du bateau
     * @param dmg : dommages qu'encaisse le bateau
     */
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

        logger.debug("Shot taken " + hp);

    }

    /**
     * Procédure de tir
     * @param target : Bateau cible
     */
    public void shoot(Boat target){
        //Si le canon sélectionné est le canon principal
        if(this.canonSelectionne==1){
            this.shootMainCanon(target);
        }

        //Si le canon sélectionné est le canon secondaire
        else{
            this.shootSecCanon(target);
        }
    }

    /**
     * procédure de tir avec le canon principal
     * @param target : bateau cible
     */
    public void shootMainCanon(Boat target) {
        if (mainCD == 0) {
            target.loseHP(dmgMainCanon);
            mainCD = reloadMainCanon;
        } else {
            // TODO : Message indiquant que le canon principal est en cooldown
        }

    }

    /**
     * procédure de tir avec le canon secondaire
     * @param target
     */
    public void shootSecCanon(Boat target) {
        if (secCD == 0) {
            target.loseHP(dmgSecCanon);
            secCD = reloadSecCanon;
        } else {
            // TODO : Message indiquant que le canon secondaire est en cooldown
        }
    }

    /**
     * Procédure de fin de tour d'un bateau : prépare les caractéristiques du bateau pour le prochain tour
     */
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

    /**
     * procédure de déplacement du bateau
     * @param destination : case cible
     */
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
        Boat b = new Amiral(new CaseEau(2,2),new Player(1));

        System.out.println(b.getPosition());

        b.moveBoat(new CaseEau(2,3));
        System.out.println(b.getPosition());
    }
}

