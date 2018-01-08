package fr.lesprogbretons.seawar.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import fr.lesprogbretons.seawar.SeaWar;
import fr.lesprogbretons.seawar.assets.Assets;
import fr.lesprogbretons.seawar.utils.TiledCoordinates;
import fr.lesprogbretons.seawar.utils.Utils;

import java.util.concurrent.ThreadLocalRandom;

import static fr.lesprogbretons.seawar.SeaWar.logger;
import static fr.lesprogbretons.seawar.SeaWar.shapeRenderer;


/**
 * Classe qui permet d'afficher une carte prédéfinie
 * <p>
 * Inspiré par PixelScientist et Libgdx
 * </p>
 */
public class SeaWarMapScreen extends ScreenAdapter {

//    private static final int WIDTH_MAP = 13;
        private static final int WIDTH_MAP = 40;
//    private static final int HEIGHT_MAP = 11;
    private static final int HEIGHT_MAP = 50;

    private TiledMap map;
    private TiledMapTileLayer layer;
    private TiledMapTile[] tiles;
//    private TiledMapTile[] tilesSelected;

    private OrthographicCamera camera;
    private MapOrthoCamController cameraController;
    private HexagonalTiledMapRenderer renderer;
    private TextureAtlas hexture;


    private TiledCoordinates selectedTile = new TiledCoordinates(0, 0);

    @Override
    public void show() {
        //Redimentionner l'écran pour faire rentrer la map
        Gdx.graphics.setWindowedMode(800, 800);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 800, 800);

        cameraController = new MapOrthoCamController(camera, WIDTH_MAP, HEIGHT_MAP, 112, 97);
        camera.update();

        hexture = (TextureAtlas) SeaWar.assets.get(Assets.hexes);

        map = new TiledMap();
        MapLayers layers = map.getLayers();


        tiles = new TiledMapTile[7];
        tiles[0] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("hexblue")));
        tiles[1] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("hexgreen")));
        tiles[2] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("hexphare")));
        tiles[3] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship1")));
        tiles[4] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship2")));
        tiles[5] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship3")));
        tiles[6] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship4")));

        for (int l = 0; l < 1; l++) {
            layer = new TiledMapTileLayer(WIDTH_MAP, HEIGHT_MAP, 112, 97);
            for (int y = 0; y < HEIGHT_MAP; y++) {
                for (int x = 0; x < WIDTH_MAP; x++) {
                    int id = ThreadLocalRandom.current().nextInt(6);
                    Cell cell = new Cell();
                    cell.setTile(tiles[id]);
                    layer.setCell(x, y, cell);
                }
            }
            layers.add(layer);
        }

        renderer = new HexagonalTiledMapRenderer(map);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(cameraController);
        Gdx.input.setInputProcessor(multiplexer);

        logger.debug("Rect | " + renderer.getViewBounds().toString() + " Pos : " + camera.position.toString());
    }

    @Override
    public void resize(int width, int height) {
        //A changer en fonction de comment est géré le stage
        // i.e. si c'est une classe que l'on redéfinit ou pas
    }

    @Override
    public void render(float delta) {
        Utils.clearScreen();
        //Update view if needed
        cameraController.changeView(delta);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        logger.debug("Rect | " + renderer.getViewBounds().toString() + " Pos : " + camera.position.toString());
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        float t = WIDTH_MAP / 2f;
        float t1 = t + (t / 2f) + 0.25f;
        shapeRenderer.rect(0, 0, t1 * 112, (HEIGHT_MAP + 0.5f) * 97);
        shapeRenderer.end();
//        if (cameraController.clicked) {
//            TiledCoordinates coords = getSelectedHexagon(cameraController.touchX, cameraController.touchY);
//            if (coords.row >= 0 && coords.row < 11 && coords.column >= 0 && coords.column < 13) {
//                if (!coords.equals(selectedTile)) {
////                    invertSelection(selectedTile, tilesSelected, tiles);
//                }
////                invertSelection(coords, tiles, tilesSelected);
//                selectedTile = coords;
//            }
//            //Le click est consomé
//            cameraController.clicked = false;
//        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
        hexture.dispose();
//        hextureSel.dispose();
        map.dispose();
    }

    private void invertSelection(TiledCoordinates coords, TiledMapTile[] tiledSet, TiledMapTile[] tilesToSet) {
        Cell c = layer.getCell(coords.column, coords.row);
        if (c.getTile() == tiledSet[0]) c.setTile(tilesToSet[0]);
        else if (c.getTile() == tiledSet[1]) c.setTile(tilesToSet[1]);
        else if (c.getTile() == tiledSet[2]) c.setTile(tilesToSet[2]);
    }

    private void getSelectedHexagon(float x, float y) {

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
        selectedTile.column = column;
        selectedTile.row = row;
    }
}