package fr.lesprogbretons.seawar.model;

import java.util.Objects;

import static java.lang.Math.abs;

public abstract class Case {

    protected int x;
    protected int y;

    protected boolean phare;
    protected Player possedePhare = null;         //1 pour joueur1 et 2 pour joueur2

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Case)) return false;
        Case aCase = (Case) o;
        return x == aCase.x &&
                y == aCase.y &&

                phare == aCase.phare;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y, phare);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "Case{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int getY() {

        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int diff(Case c){
        return (abs(this.x - c.getX()) + abs(this.y - c.getY()));
    }
}
