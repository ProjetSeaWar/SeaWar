package fr.lesprogbretons.seawar.model;

public class Player {
    private int number;

    private int pharesPossedes = 0;


    //////////////////////////////////////////////////////////////////////////////
    public Player(int number) {
        this.number = number;
    }


    ////////////////////////////////////////////////////////////////////////////////////:
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
