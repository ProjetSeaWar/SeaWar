package fr.lesprogbretons.seawar.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.lesprogbretons.seawar.SeaWar;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//Mettre n'importe quel nombre tant que c'est du 16/9
		config.addIcon("icon/wheel-128.png", Files.FileType.Internal);
		config.addIcon("icon/wheel-32.png", Files.FileType.Internal);
		config.addIcon("icon/wheel-16.png", Files.FileType.Internal);
		config.title = "SeaWars";
		config.width = 800;
		config.height = 480;
		config.x = 500;
		config.y = 0;
		config.resizable = false;
		new LwjglApplication(new SeaWar(), config);
	}
}