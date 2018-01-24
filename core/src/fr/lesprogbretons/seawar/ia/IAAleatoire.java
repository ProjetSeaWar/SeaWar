package fr.lesprogbretons.seawar.ia;

import fr.lesprogbretons.seawar.controller.Controller;
import fr.lesprogbretons.seawar.model.Partie;

import java.util.Random;

import static fr.lesprogbretons.seawar.SeaWar.partie;
import static fr.lesprogbretons.seawar.SeaWar.seaWarController;

public class IAAleatoire extends AbstractIA{

    public void getCoup() {
        Random rnd = new Random();

        int x = rnd.nextInt(partie.getMap().getHauteur());
        int y = rnd.nextInt(partie.getMap().getLargeur());

        seaWarController.selection(x,y);
    }
}
