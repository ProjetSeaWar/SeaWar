package fr.lesprogbretons.seawar.screen.manager;

import fr.lesprogbretons.seawar.model.boat.Boat;
import fr.lesprogbretons.seawar.model.cases.Case;
import fr.lesprogbretons.seawar.model.map.Grille;
import fr.lesprogbretons.seawar.screen.SeaWarMapScreen;
import fr.lesprogbretons.seawar.screen.ui.Ui;
import fr.lesprogbretons.seawar.screen.ui.UiEditeur;
import fr.lesprogbretons.seawar.screen.ui.UiType;
import fr.lesprogbretons.seawar.utils.TiledCoordinates;

import java.util.ArrayList;

import static fr.lesprogbretons.seawar.SeaWar.partie;

public class EditeurMapManager implements MapManager {

    //Ui
    private UiEditeur myUi;
    //Vue
    private SeaWarMapScreen myMapScreen;

    //Elements d'UI qui ont des actions sur la carte
    //Sélection
    private TiledCoordinates selectedTile;
    //Modèle
    private Grille g = partie.getMap();


    public EditeurMapManager() {
        //Pas de sélection pour le début
        selectedTile = new TiledCoordinates(-1, -1);
    }

    @Override
    public void setMyMapScreen(SeaWarMapScreen myMapScreen) {
        this.myMapScreen = myMapScreen;
    }

    @Override
    public void setMyUi(Ui myUi) {
        this.myUi = (UiEditeur) myUi;
    }

    @Override
    public UiType getMyUi() {
        return UiType.EDITOR;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }

    @Override
    public boolean updateSelection(boolean clicked, boolean rightClicked, float touchX, float touchY) {
        return false;
    }

    public TiledCoordinates getSelectedTile() {
        return selectedTile;
    }
}
