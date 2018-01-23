package fr.lesprogbretons.seawar.controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import fr.lesprogbretons.seawar.model.Editeur;
import fr.lesprogbretons.seawar.model.Orientation;
import fr.lesprogbretons.seawar.model.boat.Amiral;
import fr.lesprogbretons.seawar.model.boat.Fregate;
import fr.lesprogbretons.seawar.model.cases.CaseEau;
import fr.lesprogbretons.seawar.model.cases.CaseTerre;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import fr.lesprogbretons.seawar.model.Partie;
import fr.lesprogbretons.seawar.model.cases.Case;
import fr.lesprogbretons.seawar.model.map.DefaultMap;
import fr.lesprogbretons.seawar.model.map.Grille;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static fr.lesprogbretons.seawar.SeaWar.editeur;
import static fr.lesprogbretons.seawar.SeaWar.logger;
import static fr.lesprogbretons.seawar.SeaWar.partie;

public class ControllerEditeur {

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

    public void savegrille(String nom) {
        FileHandle dossier = Gdx.files.local("saves/cartes");
        if (!dossier.exists()) {
            dossier.mkdirs();
        }
        FileHandle fichier = Gdx.files.internal("saves/cartes/" + nom + ".ser");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(String.valueOf(fichier)))) {
            oos.writeObject(partie.getMap());
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
