package fr.lesprogbretons.seawar.screen.manager;

import fr.lesprogbretons.seawar.screen.SeaWarMapScreen;
import fr.lesprogbretons.seawar.screen.ui.Ui;
import fr.lesprogbretons.seawar.screen.ui.UiType;

public interface MapManager {

    void setMyMapScreen(SeaWarMapScreen myMapScreen);

    void setMyUi(Ui myUi);

    UiType getMyUiType();

    void start();

    void update();

    boolean updateSelection(boolean clicked, boolean rightClicked, float touchX, float touchY);

    void end();

    void dispose();
}
