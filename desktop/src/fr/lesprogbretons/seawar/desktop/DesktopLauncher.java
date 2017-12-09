package fr.lesprogbretons.seawar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.lesprogbretons.seawar.SeaWar;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//Configuration a compléter
		config.title = "SeaWars";
		config.width = 800;
		config.height = 480;
		config.x = 500;
		config.y = 250;
		config.resizable = false;
		new LwjglApplication(new SeaWar(), config);
	}
}
