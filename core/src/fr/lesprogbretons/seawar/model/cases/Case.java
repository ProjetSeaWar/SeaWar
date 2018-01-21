package fr.lesprogbretons.seawar.model.cases;

import fr.lesprogbretons.seawar.model.Player;

import java.io.Serializable;
import java.util.Objects;

import static java.lang.Math.abs;

/**
 * Classe Case
 */
public abstract class Case implements Serializable{

    //Coordonnées
    protected int x;
    protected int y;

    //Phares
    protected boolean phare = false;
    protected Player possedePhare = null;         //1 pour joueur1 et 2 pour joueur2

    /**
     * Constructeur
     * @param xe : colonne
     * @param ye : ligne
     */

    /*-------------------------------------------------------------------*/
    //Getters & Setters

    public Case(int xe, int ye) {
        x = xe;
        y = ye;
    }

    public boolean isPhare() {
        return phare;
    }

    public void setPhare(boolean phare) {
        this.phare = phare;
    }

    public Player getPossedePhare() {
        return possedePhare;
    }

    public void setPossedePhare(Player possedePhare) {
        this.possedePhare = possedePhare;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {

        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    /*------------------------------------------------------------------------*/

    /**
     * Compare deux Case
     * @param o : Case à comparer
     * @return true si les cases sont les mêmes, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Case)) return false;
        Case aCase = (Case) o;
        return x == aCase.x &&
                y == aCase.y &&

                phare == aCase.phare;
    }

    /**
     * toString
     * @return
     */
    @Override
    public String toString() {
        return "Case{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
