package fr.lesprogbretons.seawar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.lesprogbretons.seawar.SeaWar;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//Configuration a compléter
		config.title = "SeaWars";
		config.width = 640;
		config.height = 360;
		config.x = 100;
		config.y = 100;
		config.resizable = true;
		new LwjglApplication(new SeaWar(), config);
	}
}
