package fr.lesprogbretons.seawar.model;

import java.io.Serializable;

/**
 * Classe joueur
 */
public class Player implements Serializable{

    //Numéro du joueur
    private int number;

    //Nombre de phares qu'il possede
    private int pharesPossedes = 0;


    /**
     * Constructeur
     * @param number
     */
    public Player(int number) {
        this.number = number;
    }


    //Getters & Setters
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPharesPossedes() {
        return pharesPossedes;
    }

    public void setPharesPossedes(int pharesPossedes) {
        this.pharesPossedes = pharesPossedes;
    }

    @Override
    public String toString() {
        return "Player " + number;
    }
}
