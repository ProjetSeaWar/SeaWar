package fr.lesprogbretons.seawar.model;

import static java.lang.Math.abs;

public abstract class Case {

    private int x;
    private int y;

    public boolean isPhare() {
        return phare;
    }

    public void setPhare(boolean phare) {
        this.phare = phare;
    }

    protected boolean phare;

    public Case(int xe, int ye){
        x=xe;
        y=ye;
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
