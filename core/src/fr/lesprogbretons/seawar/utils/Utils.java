package fr.lesprogbretons.seawar.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class Utils {

    public static void clearScreen() {
        Gdx.gl20.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}