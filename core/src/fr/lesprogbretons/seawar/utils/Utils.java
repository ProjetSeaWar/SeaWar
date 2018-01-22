package fr.lesprogbretons.seawar.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import fr.lesprogbretons.seawar.model.boat.Amiral;
import fr.lesprogbretons.seawar.model.boat.Boat;
import fr.lesprogbretons.seawar.model.boat.Fregate;
import fr.lesprogbretons.seawar.model.cases.Case;
import fr.lesprogbretons.seawar.model.cases.CaseEau;
import fr.lesprogbretons.seawar.model.cases.CaseTerre;
import fr.lesprogbretons.seawar.model.map.Grille;

import static fr.lesprogbretons.seawar.SeaWar.partie;

public class Utils {

    public static void clearScreen() {
        Gdx.gl20.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static void generateMap(TiledMapTileLayer layer, TiledMapTile[] tiles) {
        for (int l = 0; l < 1; l++) {
            Grille g = partie.getMap();
            for (int y = 0; y < g.getHauteur(); y++) {
                for (int x = 0; x < g.getLargeur(); x++) {
                    Cell cell = new Cell();
                    Case aCase = g.getCase(y, x);

                    Boat boat = g.bateauSurCase(aCase);
                    if (g.casePossedeBateaux(aCase) && boat.isAlive()) {
                        if (boat.getJoueur() == partie.getJoueur1()) {
                            if (boat instanceof Amiral) {
                                cell.setTile(tiles[3]);
                            } else if (boat instanceof Fregate) {
                                cell.setTile(tiles[4]);
                            }
                        } else {
                            if (boat instanceof Amiral) {
                                cell.setTile(tiles[5]);
                            } else if (boat instanceof Fregate) {
                                cell.setTile(tiles[6]);
                            }
                        }
                    } else {
                        if (aCase instanceof CaseEau) {
                            if (aCase.isPhare()) {
                                cell.setTile(tiles[2]);
                            } else {
                                cell.setTile(tiles[0]);
                            }
                        } else if (aCase instanceof CaseTerre) {
                            cell.setTile(tiles[1]);
                        }
                    }
                    layer.setCell(x, y, cell);
                }
            }
        }
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
