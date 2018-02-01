package fr.lesprogbretons.seawar.model;

import fr.lesprogbretons.seawar.model.map.Grille;

import java.io.Serializable;

public class Editeur implements Serializable {
    Grille map;

    public Grille getMap() {
        return map;
    }

    public void setMap(Grille map) {
        this.map = map;
    }

    public Editeur(int x, int y) {
        if (x > 1 && y > 1) {
            map = new Grille(x, y);
        }
    }

    public Editeur() {
        map = new Grille(11, 13);
    }
}
