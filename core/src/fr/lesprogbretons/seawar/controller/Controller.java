package fr.lesprogbretons.seawar.controller;



/* Classe Controller qui va gère l'interraction avec l'utilisateur */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import fr.lesprogbretons.seawar.ia.AbstractIA;
import fr.lesprogbretons.seawar.ia.IAAleatoire;
import fr.lesprogbretons.seawar.model.Partie;
import fr.lesprogbretons.seawar.model.boat.Boat;
import fr.lesprogbretons.seawar.model.cases.Case;
import fr.lesprogbretons.seawar.model.map.Grille;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static fr.lesprogbretons.seawar.SeaWar.logger;
import static fr.lesprogbretons.seawar.SeaWar.partie;

/**
 * Classe Controller
 */
public class Controller {

    //Stocker une référence à l'IA afin de pouvoir l'arrêter
    private AbstractIA ia;

    public void nouvellePartie() {
        partie = new Partie();
    }

    public void nouvellePartie(Grille g) {
        partie = new Partie(g);
    }

    public void startIA() {
        ia = new IAAleatoire();
        ia.start();
    }

    public void stopIA() {
        if (ia != null) {
            ia.interrupt();
            ia = null;
        }
    }

    /**
     * Procédure qui gère la sélection d'une case quelconque à la souris
     *
     * @param x : colonne de la case sélectionnée
     * @param y : ligne de la case sélectionnée
     */
    public void selection(int x, int y) {
        Case c = partie.getMap().getCase(x, y);

        boolean actionFaite = false;

        //Les actions que veut faire le joueur avec le bateau qu'il a sélectionné
        if (partie.isAnyBateauSelectionne()) {

            ArrayList<Case> casesPorteeTir;
            casesPorteeTir = partie.getMap().getCasesPortees(partie.getBateauSelectionne());


            if (partie.getMap().casePossedeBateau(c, partie.getCurrentPlayer()) && !(partie.getMap().bateauSurCase(c).equals(partie.getBateauSelectionne()))) {
                partie.setBateauSelectionne(partie.getMap().bateauSurCase(c));
                actionFaite = true;
            }

            //Si la case sélectionnée est à portée de tir
            else if (casesPorteeTir.contains(c)) {
                if (partie.getMap().casePossedeBateau(c, partie.getOtherPlayer())) {
                    partie.getBateauSelectionne().shoot(partie.getMap().bateauSurCase(c));
                    if (!partie.getMap().bateauSurCase(c).isAlive()) {
                        c.setBateauDetruit(true);
                        if (partie.getCurrentPlayer().getNumber() == 1) {
                            partie.getMap().getBateaux2().remove(partie.getMap().bateauSurCase(c));
                        } else {
                            partie.getMap().getBateaux1().remove(partie.getMap().bateauSurCase(c));
                        }
                    }
                    partie.setAnyBateauSelectionne(false);
                    actionFaite = true;
                }
            }

            if (!actionFaite) {
                ArrayList<Case> casesDispo = partie.getMap().getCasesDisponibles(partie.getBateauSelectionne().getPosition(), 1);

                //Si la case sélectionnée est à portée de déplacement
                if (partie.getBateauxDejaDeplaces().size() == 0 && casesDispo.contains(c) && partie.getBateauSelectionne().getMoveAvailable() > 0) {
                    partie.getBateauSelectionne().moveBoat(c);
                    partie.ajouterBateauxDejaDeplaces(partie.getBateauSelectionne());
                   /* if (c.isPhare()) {
                        partie.getMap().prendPhare(c, partie.getCurrentPlayer());
                    }*/
                } else if (casesDispo.contains(c) && partie.getBateauSelectionne().getMoveAvailable() > 0 && partie.getBateauxDejaDeplaces().get(partie.getBateauxDejaDeplaces().size() - 1).equals(partie.getBateauSelectionne())) {
                    partie.getBateauSelectionne().moveBoat(c);
                    partie.ajouterBateauxDejaDeplaces(partie.getBateauSelectionne());
                   /* if (c.isPhare()) {
                        partie.getMap().prendPhare(c, partie.getCurrentPlayer());
                    }*/
                } else if (casesDispo.contains(c) && partie.getBateauSelectionne().getMoveAvailable() > 0 && !(partie.getBateauxDejaDeplaces().contains(partie.getBateauSelectionne()))) {
                    partie.getBateauxDejaDeplaces().get(partie.getBateauxDejaDeplaces().size() - 1).setMoveAvailable(0);
                    partie.getBateauSelectionne().moveBoat(c);
                    partie.ajouterBateauxDejaDeplaces(partie.getBateauSelectionne());
                   /* if (c.isPhare()) {
                        partie.getMap().prendPhare(c, partie.getCurrentPlayer());
                    }*/
                } else {
                    partie.setAnyBateauSelectionne(false);
                }

            }


        } else {
            //Si le joueur sélectionne un de ses bateaux
            if (partie.getMap().casePossedeBateau(c, partie.getCurrentPlayer())) {
                partie.setBateauSelectionne(partie.getMap().bateauSurCase(c));
            }

        }
    }

    /**
     * Procédure qui finit le tour du joueur quand on appuie sur le bouton fin de tour
     */
    public boolean endTurn() {
        boolean isOver = false;
        partie.finPartie();

        if (!partie.isFin()) {
            partie.setAnyBateauSelectionne(false);
            isOver = partie.endTurn();
        }

        return isOver;
    }

    public void changerCanon() {
        partie.getBateauSelectionne().setCanonSelectionne(3 - partie.getBateauSelectionne().getCanonSelectionne());

    }

    public void changerCanon(Boat b) {
        b.setCanonSelectionne(3 - b.getCanonSelectionne());

    }

    public void save(String nom) {
        FileHandle dossier = Gdx.files.local("saves/parties");
        if (!dossier.exists()) {
            dossier.mkdirs();
        }
        FileHandle fichier = Gdx.files.internal("saves/parties/" + nom + ".ser");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(String.valueOf(fichier)))) {
            oos.writeObject(partie);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void load(Partie restoredPartie) {
        partie = restoredPartie;
        logger.debug("Restored save");
    }

}
