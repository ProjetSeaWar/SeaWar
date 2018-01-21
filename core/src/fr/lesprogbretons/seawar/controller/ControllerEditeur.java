package fr.lesprogbretons.seawar.controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import fr.lesprogbretons.seawar.model.Editeur;
import fr.lesprogbretons.seawar.model.Orientation;
import fr.lesprogbretons.seawar.model.boat.Amiral;
import fr.lesprogbretons.seawar.model.boat.Fregate;
import fr.lesprogbretons.seawar.model.cases.CaseEau;
import fr.lesprogbretons.seawar.model.cases.CaseTerre;

public class ControllerEditeur {
    Editeur editeur;

    public ControllerEditeur(Editeur e){
        editeur = e;
    }

    public void creerCarte(int x, int y){
        editeur = new Editeur(x,y);
    }

    public void creerCaseEau(int x, int y){
        editeur.getMap().setCase(new CaseEau(x,y));
    }

    public void creerCasePhare(int x, int y){
        editeur.getMap().setCase(new CaseEau(x,y));
        editeur.getMap().getCase(x,y).setPhare(true);
    }

    public void creerCaseTerre(int x,int y){
        editeur.getMap().setCase(new CaseTerre(x,y));
    }

    public void ajouterAmiralJoueur1(int x, int y, Orientation orientation){
        if(!(editeur.getMap().getCase(x,y) instanceof CaseTerre)){
            Amiral boat = new Amiral(editeur.getMap().getCase(x,y),editeur.getMap().getJoueur1());
            boat.setOrientation(orientation);
            editeur.getMap().ajouterBateauJoueur1(boat);
        }
    }

    public void ajouterFregateJoueur1(int x, int y, Orientation orientation){
        if(!(editeur.getMap().getCase(x,y) instanceof CaseTerre)){
            Fregate boat = new Fregate(editeur.getMap().getCase(x,y),editeur.getMap().getJoueur1());
            boat.setOrientation(orientation);
            editeur.getMap().ajouterBateauJoueur1(boat);
        }
    }

    public void ajouterAmiralJoueur2(int x, int y, Orientation orientation){
        if(!(editeur.getMap().getCase(x,y) instanceof CaseTerre)){
            Amiral boat = new Amiral(editeur.getMap().getCase(x,y),editeur.getMap().getJoueur2());
            boat.setOrientation(orientation);
            editeur.getMap().ajouterBateauJoueur2(boat);
        }
    }

    public void ajouterFregateJoueur2(int x, int y, Orientation orientation){
        if(!(editeur.getMap().getCase(x,y) instanceof CaseTerre)){
            Fregate boat = new Fregate(editeur.getMap().getCase(x,y),editeur.getMap().getJoueur2());
            boat.setOrientation(orientation);
            editeur.getMap().ajouterBateauJoueur2(boat);
        }
    }

    public void save(String text) {
    }
}
