package fr.lesprogbretons.seawar.screen.manager;

import fr.lesprogbretons.seawar.screen.SeaWarMapScreen;
import fr.lesprogbretons.seawar.screen.ui.Ui;
import fr.lesprogbretons.seawar.screen.ui.UiType;
import fr.lesprogbretons.seawar.utils.TiledCoordinates;

public class EditeurMapManager implements MapManager{

    //Vue
    private SeaWarMapScreen myMapScreen;

    //Elements d'UI qui ont des actions sur la carte
    //Sélection
    private TiledCoordinates selectedTile;


    public EditeurMapManager() {
        //Pas de sélection pour le début
        selectedTile = new TiledCoordinates(-1, -1);
    }

    @Override
    public void setMyMapScreen(SeaWarMapScreen myMapScreen) {
        this.myMapScreen=myMapScreen;
    }

    @Override
    public void setMyUi(Ui myUi) {

    }

    @Override
    public UiType getMyUi() {
        return null;
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
}
