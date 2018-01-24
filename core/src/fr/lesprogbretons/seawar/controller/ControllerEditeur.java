package fr.lesprogbretons.seawar.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import fr.lesprogbretons.seawar.model.Editeur;
import fr.lesprogbretons.seawar.model.Orientation;
import fr.lesprogbretons.seawar.model.boat.Amiral;
import fr.lesprogbretons.seawar.model.boat.Fregate;
import fr.lesprogbretons.seawar.model.cases.CaseEau;
import fr.lesprogbretons.seawar.model.cases.CaseTerre;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static fr.lesprogbretons.seawar.SeaWar.editeur;
import static fr.lesprogbretons.seawar.SeaWar.partie;

public class ControllerEditeur {

    public void creerCarte(int x, int y) {
        editeur = new Editeur(x, y);
    }

    public void creerCaseEau(int x, int y) throws ArrayIndexOutOfBoundsException {
        if (editeur.getMap().casePossedeBateau(editeur.getMap().getCase(x, y), editeur.getMap().getJoueur1())) {
            editeur.getMap().getBateaux1().remove(editeur.getMap().bateauSurCase(editeur.getMap().getCase(x, y)));
        } else if (editeur.getMap().casePossedeBateau(editeur.getMap().getCase(x, y), editeur.getMap().getJoueur2())) {
            editeur.getMap().getBateaux2().remove(editeur.getMap().bateauSurCase(editeur.getMap().getCase(x, y)));
        }
        editeur.getMap().setCase(new CaseEau(x, y));


    }

    public void creerCasePhare(int x, int y) throws ArrayIndexOutOfBoundsException {
        if (editeur.getMap().casePossedeBateau(editeur.getMap().getCase(x, y), editeur.getMap().getJoueur1())) {
            editeur.getMap().getBateaux1().remove(editeur.getMap().bateauSurCase(editeur.getMap().getCase(x, y)));
        } else if (editeur.getMap().casePossedeBateau(editeur.getMap().getCase(x, y), editeur.getMap().getJoueur2())) {
            editeur.getMap().getBateaux2().remove(editeur.getMap().bateauSurCase(editeur.getMap().getCase(x, y)));
        }
        editeur.getMap().setCase(new CaseEau(x, y));
        editeur.getMap().getCase(x, y).setPhare(true);


    }

    public void creerCaseTerre(int x, int y) throws ArrayIndexOutOfBoundsException {
        if (editeur.getMap().casePossedeBateau(editeur.getMap().getCase(x, y), editeur.getMap().getJoueur1())) {
            editeur.getMap().getBateaux1().remove(editeur.getMap().bateauSurCase(editeur.getMap().getCase(x, y)));
        } else if (editeur.getMap().casePossedeBateau(editeur.getMap().getCase(x, y), editeur.getMap().getJoueur2())) {
            editeur.getMap().getBateaux2().remove(editeur.getMap().bateauSurCase(editeur.getMap().getCase(x, y)));
        }
        editeur.getMap().setCase(new CaseTerre(x, y));


    }

    public void ajouterAmiralJoueur1(int x, int y, Orientation orientation) throws ArrayIndexOutOfBoundsException {
        creerCaseEau(x, y);
        Amiral boat = new Amiral(editeur.getMap().getCase(x, y), editeur.getMap().getJoueur1());
        boat.setOrientation(orientation);
        if (editeur.getMap().casePossedeBateaux(editeur.getMap().getCase(x, y))) {
            editeur.getMap().getBateaux1().remove(editeur.getMap().bateauSurCase(editeur.getMap().getCase(x, y)));
            editeur.getMap().ajouterBateauJoueur1(boat);
        } else {
            editeur.getMap().ajouterBateauJoueur1(boat);
        }
    }

    public void ajouterFregateJoueur1(int x, int y, Orientation orientation) throws ArrayIndexOutOfBoundsException {
        creerCaseEau(x, y);
        Fregate boat = new Fregate(editeur.getMap().getCase(x, y), editeur.getMap().getJoueur1());
        boat.setOrientation(orientation);
        if (editeur.getMap().casePossedeBateaux(editeur.getMap().getCase(x, y))) {
            editeur.getMap().getBateaux1().remove(editeur.getMap().bateauSurCase(editeur.getMap().getCase(x, y)));
            editeur.getMap().ajouterBateauJoueur1(boat);
        } else {
            editeur.getMap().ajouterBateauJoueur1(boat);
        }
    }

    public void ajouterAmiralJoueur2(int x, int y, Orientation orientation) throws ArrayIndexOutOfBoundsException {
        creerCaseEau(x, y);
        Amiral boat = new Amiral(editeur.getMap().getCase(x, y), editeur.getMap().getJoueur2());
        boat.setOrientation(orientation);
        if (editeur.getMap().casePossedeBateaux(editeur.getMap().getCase(x, y))) {
            editeur.getMap().getBateaux2().remove(editeur.getMap().bateauSurCase(editeur.getMap().getCase(x, y)));
            editeur.getMap().ajouterBateauJoueur2(boat);
        } else {
            editeur.getMap().ajouterBateauJoueur2(boat);
        }
    }

    public void ajouterFregateJoueur2(int x, int y, Orientation orientation) throws ArrayIndexOutOfBoundsException {
        creerCaseEau(x, y);
        Fregate boat = new Fregate(editeur.getMap().getCase(x, y), editeur.getMap().getJoueur2());
        boat.setOrientation(orientation);
        if (editeur.getMap().casePossedeBateaux(editeur.getMap().getCase(x, y))) {
            editeur.getMap().getBateaux2().remove(editeur.getMap().bateauSurCase(editeur.getMap().getCase(x, y)));
            editeur.getMap().ajouterBateauJoueur2(boat);
        } else {
            editeur.getMap().ajouterBateauJoueur2(boat);
        }

    }

    public void saveGrille(String nom) {
        FileHandle dossier = Gdx.files.local("saves/cartes");
        if (!dossier.exists()) {
            dossier.mkdirs();
        }
        FileHandle fichier = Gdx.files.internal("saves/cartes/" + nom + ".ser");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(String.valueOf(fichier)))) {
            oos.writeObject(editeur.getMap());
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
