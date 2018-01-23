package fr.lesprogbretons.seawar.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class Utils {

    public static void clearScreen() {
        Gdx.gl20.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static void getSelectedHexagon(float x, float y, TiledCoordinates selectedTile) {
        float hexWidth = 112f;
        float hexQuarterWidth = hexWidth / 4f;
        float hexHeight = 97f;
        float hexHalfHeight = hexHeight / 2f;
        float hexThreeQuartersWidth = hexWidth * 0.75f;


        float m = hexQuarterWidth / hexHalfHeight;
        // Find the row and column of the box that the point falls in.
        int column = (int) (x / hexThreeQuartersWidth);
        int row;

        boolean columnIsOdd = column % 2 == 0;

        // Is the column an odd number?
        if (!columnIsOdd)// no: Offset x to match the indent of the row
            row = (int) ((y - hexHalfHeight) / hexHeight);
        else// Yes: Calculate normally
            row = (int) (y / hexHeight);
        // Work out the position of the point relative to the box it is in
        double relX = x - (column * hexThreeQuartersWidth);
        double relY;

        if (!columnIsOdd)
            relY = (y - (row * hexHeight)) - hexHalfHeight;
        else
            relY = y - (row * hexHeight);

        // Work out if the point is above either of the hexagon's top edges
        if (relX < (-m * relY) + hexQuarterWidth) // LEFT edge
        {
            column--;
            if (columnIsOdd)
                row--;
        } else if (relX < (m * relY) - hexQuarterWidth) // RIGHT edge
        {
            column--;
            if (!columnIsOdd)
                row++;
        }

        selectedTile.column = column;
        selectedTile.row = row;
    }
}
