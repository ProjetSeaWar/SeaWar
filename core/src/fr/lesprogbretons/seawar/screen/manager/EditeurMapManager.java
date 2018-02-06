package fr.lesprogbretons.seawar.screen.manager;

import fr.lesprogbretons.seawar.screen.SeaWarMapScreen;
import fr.lesprogbretons.seawar.screen.ui.Ui;
import fr.lesprogbretons.seawar.screen.ui.UiType;
import fr.lesprogbretons.seawar.utils.TiledCoordinates;
import fr.lesprogbretons.seawar.utils.Utils;

import static fr.lesprogbretons.seawar.screen.SeaWarMapScreen.selectedTile;

public class EditeurMapManager implements MapManager {

    //Vue
    private SeaWarMapScreen myMapScreen;

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
    }

    @Override
    public UiType getMyUiType() {
        return UiType.EDITOR;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        myMapScreen.removeLayerMark(SeaWarMapScreen.SELECT_LAYER_NAME);
        myMapScreen.markSelectedTile(selectedTile.column, selectedTile.row);
    }

    @Override
    public boolean updateSelection(boolean clicked, boolean rightClicked, float touchX, float touchY) {
        if (clicked) {
            Utils.getSelectedHexagon(touchX, touchY, selectedTile);
            //Le click est consomé
            return false;
        }
        //Sinon on change rien
        return false;
    }

    @Override
    public void end() {

    }

    @Override
    public void dispose() {

    }
}
