package fr.lesprogbretons.seawar.model;

public class Partie {

    private Grille map = new DefaultMap();

    private Player joueur1 = new Player(1);
    private Player joueur2 = new Player(2);

    private Player currentPlayer = joueur1;

    private Boat bateauSelectionne;
    private boolean isAnyBateauSelectionne = false;

    private boolean fin = false;

    public Partie(){
        this.start();
    }

    private void start() {
        while(!fin){

        }
    }

    public Grille getMap() {
        return map;
    }

    public void setMap(Grille map) {
        this.map = map;
    }

    public Player getJoueur1() {
        return joueur1;
    }

    public void setJoueur1(Player joueur1) {
        this.joueur1 = joueur1;
    }

    public Player getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Player joueur2) {
        this.joueur2 = joueur2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Boat getBateauSelectionne() {
        return bateauSelectionne;
    }

    public void setBateauSelectionne(Boat bateauSelectionne) {
        this.bateauSelectionne = bateauSelectionne;
    }

    public boolean isAnyBateauSelectionne() {
        return isAnyBateauSelectionne;
    }

    public void setAnyBateauSelectionne(boolean anyBateauSelectionne) {
        isAnyBateauSelectionne = anyBateauSelectionne;
    }

    public boolean isFin() {
        return fin;
    }

    public Player getOtherPlayer(){
        if(getCurrentPlayer().getNumber()==1){
            return joueur2;
        }

        else{
            return joueur1;
        }
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }
}
