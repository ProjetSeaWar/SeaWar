package fr.lesprogbretons.seawar.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import fr.lesprogbretons.seawar.SeaWar;
import fr.lesprogbretons.seawar.assets.Assets;
import fr.lesprogbretons.seawar.utils.OrthoCamController;
import fr.lesprogbretons.seawar.utils.Utils;

import static fr.lesprogbretons.seawar.SeaWar.logger;


/**
 * Classe qui permet d'afficher une carte prédéfinie
 * <p>
 * Inspiré par PixelScientist et Libgdx
 * </p>
 */
public class SeaWarCarteScreen extends ScreenAdapter {

    private TiledMap map;
    private TiledMapTileLayer layer;
    private TiledMapTile[] tiles;
    private TiledMapTile[] tilesSelected;

    private OrthographicCamera camera;
    private OrthoCamController cameraController;
    private HexagonalTiledMapRenderer renderer;
    private Texture hexture;
    private Texture hextureSel;


    private TiledCoordinates selectedTile = new TiledCoordinates(0, 0);

    @Override
    public void show() {
        //Redimentionner l'écran pour faire rentrer la map
        Gdx.graphics.setWindowedMode(800, 800);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 800, 800);
        camera.position.set(1200, 1200, 0);
        camera.update();

        cameraController = new OrthoCamController(camera);

        hexture = (Texture) SeaWar.assets.get(Assets.hexes);
        hextureSel = (Texture) SeaWar.assets.get(Assets.hexes2);

        TextureRegion[][] hexes = TextureRegion.split(hexture, 112, 97);
        TextureRegion[][] hexes2 = TextureRegion.split(hextureSel, 112, 97);

        map = new TiledMap();
        MapLayers layers = map.getLayers();


        tiles = new TiledMapTile[3];
        tiles[0] = new StaticTiledMapTile(new TextureRegion(hexes[0][0]));
        tiles[1] = new StaticTiledMapTile(new TextureRegion(hexes[0][1]));
        tiles[2] = new StaticTiledMapTile(new TextureRegion(hexes[1][0]));

        tilesSelected = new TiledMapTile[3];
        tilesSelected[0] = new StaticTiledMapTile(new TextureRegion(hexes2[0][0]));
        tilesSelected[1] = new StaticTiledMapTile(new TextureRegion(hexes2[0][1]));
        tilesSelected[2] = new StaticTiledMapTile(new TextureRegion(hexes2[1][0]));


        for (int l = 0; l < 1; l++) {
            layer = new TiledMapTileLayer(13, 11, 112, 97);
            for (int y = 0; y < 11; y++) {
                for (int x = 0; x < 13; x++) {
                    int id = (int) (Math.random() * 3);
                    Cell cell = new Cell();
                    cell.setTile(tiles[id]);
                    layer.setCell(x, y, cell);
                }
            }
            layers.add(layer);
        }

        renderer = new HexagonalTiledMapRenderer(map);

        //For moving cam and getting cells
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(cameraController);
        Gdx.input.setInputProcessor(multiplexer);

        logger.debug(camera.position.x + ";" + camera.position.y);
    }

    @Override
    public void resize(int width, int height) {
        //A changer en fonction de comment est géré le stage
        // i.e. si c'est une classe que l'on redéfinit ou pas
    }

    @Override
    public void render(float delta) {
        //TODO
        Utils.clearScreen();
        cameraController.updateZoom(delta);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        if (cameraController.click) {
            TiledCoordinates coords = getSelectedHexagon(cameraController.touchX, cameraController.touchY);
            if (!coords.equals(selectedTile)) {
                invertSelection(selectedTile, tilesSelected, tiles);
                if (coords.row >= 0 && coords.row <= 11 && coords.column >= 0 && coords.column <= 12) {
                    invertSelection(coords, tiles, tilesSelected);
                    selectedTile = coords;
                }
            }
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
        hexture.dispose();
        hextureSel.dispose();
        map.dispose();
    }

    private void invertSelection(TiledCoordinates coords, TiledMapTile[] tiledSet, TiledMapTile[] tilesToSet) {
        Cell c = layer.getCell(coords.column, coords.row);
        if (c.getTile() == tiledSet[0]) c.setTile(tilesToSet[0]);
        else if (c.getTile() == tiledSet[1]) c.setTile(tilesToSet[1]);
        else if (c.getTile() == tiledSet[2]) c.setTile(tilesToSet[2]);
    }

    private TiledCoordinates getSelectedHexagon(float x, float y) {

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
        if (columnIsOdd)// yes: Offset x to match the indent of the row
            row = (int) ((y - hexHalfHeight) / hexHeight);
        else// No: Calculate normally
            row = (int) (y / hexHeight);
        // Work out the position of the point relative to the box it is in
        double relX = x - (column * hexThreeQuartersWidth);
        double relY;

        if (columnIsOdd)
            relY = (y - (row * hexHeight)) - hexHalfHeight;
        else
            relY = y - (row * hexHeight);

        // Work out if the point is above either of the hexagon's top edges
        if (relX < (-m * relY) + hexQuarterWidth) // LEFT edge
        {
            column--;
            if (!columnIsOdd)
                row--;
        } else if (relX < (m * relY) - hexQuarterWidth) // RIGHT edge
        {
            column--;
            if (columnIsOdd)
                row++;
        }

        logger.debug("col : " + column + " row : " + row);
        return new TiledCoordinates(column, row);
    }
}

class TiledCoordinates {
    public int column;
    public int row;

    TiledCoordinates(int column, int row) {
        this.column = column;
        this.row = row;
    }
}