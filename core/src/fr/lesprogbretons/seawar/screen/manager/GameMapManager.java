package fr.lesprogbretons.seawar.screen.manager;

import fr.lesprogbretons.seawar.model.boat.Boat;
import fr.lesprogbretons.seawar.model.cases.Case;
import fr.lesprogbretons.seawar.model.map.Grille;
import fr.lesprogbretons.seawar.screen.SeaWarMapScreen;
import fr.lesprogbretons.seawar.screen.ui.GameUi;
import fr.lesprogbretons.seawar.screen.ui.Ui;
import fr.lesprogbretons.seawar.screen.ui.UiType;
import fr.lesprogbretons.seawar.utils.TiledCoordinates;
import fr.lesprogbretons.seawar.utils.Utils;

import java.util.ArrayList;

import static fr.lesprogbretons.seawar.SeaWar.*;

public class GameMapManager implements MapManager {

    //Ui
    private GameUi myUi;
    //Vue
    private SeaWarMapScreen myMapScreen;
    //Modèle
    private Grille g = partie.getMap();

    //Elements d'UI qui ont des actions sur la carte
    //Sélection
    private TiledCoordinates selectedTile;

    public GameMapManager() {
        //Pas de sélection pour le début
        selectedTile = new TiledCoordinates(-1, -1);
    }

    @Override
    public UiType getMyUi() {
        return UiType.GAME;
    }

    @Override
    public void setMyMapScreen(SeaWarMapScreen myMapScreen) {
        this.myMapScreen = myMapScreen;
    }

    @Override
    public void setMyUi(Ui myUi) {
        this.myUi = (GameUi) myUi;
    }

    @Override
    public void start() {
        //Montrer le message de début de tour
        myUi.startTurnMessage();
    }

    @Override
    public void update() {

        //On met à jour l'interface
        myUi.setTurn(partie.getTurnCounter());
        myUi.setPlayer(partie.getCurrentPlayer().toString());

        //Retirer les sélections précédentes
        myMapScreen.removeLayerMark(SeaWarMapScreen.SELECT_LAYER_NAME);
        myMapScreen.removeLayerMark(SeaWarMapScreen.SHIP_LAYER_NAME);
        myMapScreen.removeLayerMark(SeaWarMapScreen.ORIENTATION_LAYER_NAME);

        ArrayList<Boat> bateaux;

        if (partie.getCurrentPlayer().getNumber() == 1) {
            bateaux = g.getBateaux1();
        } else {
            bateaux = g.getBateaux2();
        }

        for (Boat b : bateaux) {
            myMapScreen.markShipTile(b.getPosition().getY(), b.getPosition().getX());
        }

        //region Orientatiom
        for (Boat b : g.getBateaux1()) {
            myMapScreen.markOrientationTile(b.getPosition().getY(), b.getPosition().getX(), b.getOrientation());
        }

        for (Boat b : g.getBateaux2()) {
            myMapScreen.markOrientationTile(b.getPosition().getY(), b.getPosition().getX(), b.getOrientation());
        }
        //endregion

        //region Gestion case sélectionnée
        //Try catch car premier positionnement en -1,-1
        try {
            //Récupérer case sélectionnée
            Case aCase = g.getCase(selectedTile.row, selectedTile.column);

            //Si un bateau est sélectionné
            if (partie.isAnyBateauSelectionne()) {
                //Si il est sur la case sélectionnée
                if (g.casePossedeBateaux(aCase)) {
                    //Récupérer bateau
                    Boat boat = g.bateauSurCase(aCase);
                    //Si il appartient au joueur qui joue
                    if (boat.getJoueur().equals(partie.getCurrentPlayer())) {
                        //Mettre infos bateau sélectionné
                        myUi.setInfoSelected(boat.infosCurrentPlayer());
                        //Mettre cases dispo déplacements
                        if (boat.getMoveAvailable() > 0)
                            myMapScreen.batchSelectionMark(g.getCasesDisponibles(aCase, 1));
                        //Mettre cases dispo attaques
                        if (boat.canShoot())
                            myMapScreen.batchAttackMark(g.getBoatInRange(boat, partie.getOtherPlayer()));
                    } else {
                        //Sinon montrer sa portée de déplacement
                        myMapScreen.batchSelectionMark(g.getCasesDisponibles(aCase, boat.getMoveAvailable()));
                    }
                    //La sélection des cases ne sélectionne pas la case courante
                    myMapScreen.markSelectedTile(selectedTile.column, selectedTile.row);
                }
            } else {
                //Si aucun bateau sélectionné
                if (!g.casePossedeBateaux(aCase)) {
                    //Si une case de base sélectionnée
                    myMapScreen.markSelectedTile(selectedTile.column, selectedTile.row);
                    myUi.setInfoSelected(aCase.toString());
                } else {
                    //Si c'est un bateau de l'adversaire (impossible à sélectionner)
                    if (!g.casePossedeBateau(aCase, partie.getCurrentPlayer())) {
                        Boat boat = g.bateauSurCase(aCase);
                        myUi.setInfoSelected(boat.infosOtherPlayer());
                        myMapScreen.batchSelectionMark(g.getCasesDisponibles(aCase, boat.getMove()));
                        myMapScreen.markSelectedTile(selectedTile.column, selectedTile.row);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        //endregion
    }

    @Override
    public boolean updateSelection(boolean clicked, boolean rightClicked, float touchX, float touchY) {
        if (clicked) {
            Utils.getSelectedHexagon(touchX, touchY, selectedTile);
            logger.debug("col : " + selectedTile.column + " row : " + selectedTile.row);

            if (selectedTile.row >= 0 && selectedTile.row < partie.getMap().getHauteur()
                    && selectedTile.column >= 0 && selectedTile.column < partie.getMap().getLargeur()) {

                if (!rightClicked) {
                    //Clic gauche
                    //Envoie au contrôleur
                    seaWarController.selection(selectedTile.row, selectedTile.column);
                } else {
                    //Clic droit
                    //TODO Affichage des infos
                }
            }
            //Le click est consomé
            return false;
        }
        //Sinon on change rien
        return false;
    }
}
