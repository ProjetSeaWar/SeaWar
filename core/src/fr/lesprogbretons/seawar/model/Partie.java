package fr.lesprogbretons.seawar.model;

public class Partie {

    private Grille map = new DefaultMap();

    private Player joueur1 = map.getJoueur1();
    private Player joueur2 = map.getJoueur2();

    private Player currentPlayer = joueur1;

    private Boat bateauSelectionne;
    private boolean isAnyBateauSelectionne = false;

    private boolean fin = false;

    private Player winner;

    private int nbTours = 1;

    ////////////////////////////////////////////////////////////////////////////:
    public Partie(){

    }

    private void start() {
        while(!fin){

        }
    }



    /////////////////////////////GETTER ET SETTER//////////////////////////////////////////
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


    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    /////////////////////////////////////////////////////////////////////////////:
    public Player getOtherPlayer(){
        if(getCurrentPlayer().getNumber()==1){
            return joueur2;
        }

        else{
            return joueur1;
        }
    }

    public void finPartie() {
        if (getJoueur1().getPharesPossedes() == 3) {
            setFin(true);
            setWinner(getJoueur1());
        } else if (getJoueur2().getPharesPossedes() == 3) {
            setFin(true);
            setWinner(getJoueur2());
        }

        else if(getCurrentPlayer().equals(getJoueur1())){
            boolean fin = true;

            for(int i=0; i<map.bateaux1.size();i++){
                if(map.bateaux1.get(i).isAlive()){
                    fin = false;
                }
            }

            if(fin){
                setFin(true);
                setWinner(joueur1);
            }
        }

        else if(getCurrentPlayer().equals(getJoueur2())){
            boolean fin = true;

            for(int i=0; i<map.bateaux2.size();i++){
                if(map.bateaux2.get(i).isAlive()){
                    fin = false;
                }
            }

            if(fin){
                setFin(true);
                setWinner(joueur2);
            }
        }
    }

    public void endTurn(){
        boolean bateauxDeplaces = true;

        if(getCurrentPlayer().equals(joueur1)){
            for(int i = 0 ; i < map.bateaux1.size() ; i++ ){
                if(map.bateaux1.get(i).getMoveAvailable() == map.bateaux1.get(i).getMove()){
                    bateauxDeplaces = false;
                }
            }
        }

        else {
            for(int i = 0 ; i < map.bateaux2.size() ; i++ ){
                if(map.bateaux2.get(i).getMoveAvailable() == map.bateaux2.get(i).getMove()){
                    bateauxDeplaces = false;
                }
            }
        }

        if(bateauxDeplaces) {
            if (getCurrentPlayer().equals(joueur1)) {
                for (int i = 0; i < map.bateaux1.size(); i++) {
                    map.bateaux1.get(i).endTurn();
                    map.bateaux2.get(i).setShootTaken(0);
                }
            } else {
                for (int i = 0; i < map.bateaux2.size(); i++) {
                    map.bateaux2.get(i).endTurn();
                    map.bateaux1.get(i).setShootTaken(0);
                    map
                }
            }

            setCurrentPlayer(getOtherPlayer());
        }
    }
}
