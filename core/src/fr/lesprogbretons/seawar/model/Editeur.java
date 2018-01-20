package fr.lesprogbretons.seawar.model;

import fr.lesprogbretons.seawar.model.map.*;

public class Editeur {
    Grille map;

    int compteurClique = 0;

    public Grille getMap() {
        return map;
    }

    public void setMap(Grille map) {
        this.map = map;
    }

    public Editeur(int x, int y){
        if(x>1 && y>1){
            map = new Grille(x,y);
        }
    }
}
