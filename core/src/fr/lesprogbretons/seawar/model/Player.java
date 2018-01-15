package fr.lesprogbretons.seawar.model;

import java.util.Objects;

public class Player {
    private int number;

    private boolean tourTermine = true;

    private int currentBoat = 1;

    private int pharesPossedes = 0;

    public int getPharesPossedes() {
        return pharesPossedes;
    }

    public void setPharesPossedes(int pharesPossedes) {
        this.pharesPossedes = pharesPossedes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return number == player.number &&
                tourTermine == player.tourTermine &&
                currentBoat == player.currentBoat;
    }

    @Override
    public int hashCode() {

        return Objects.hash(number, tourTermine, currentBoat);
    }

    public int getNumber() {

        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isTourTermine() {
        return tourTermine;
    }

    public void setTourTermine(boolean tourTermine) {
        this.tourTermine = tourTermine;
    }

    public int getCurrentBoat() {
        return currentBoat;
    }

    public void setCurrentBoat(int currentBoat) {
        this.currentBoat = currentBoat;
    }

    public Player(int number){
        this.number = number;
    }

    public void chooseBoat(){

    }

    public void newTurn(){
        tourTermine = false;
    }
}
