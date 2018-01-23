package fr.lesprogbretons.seawar.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import fr.lesprogbretons.seawar.model.Orientation;
import fr.lesprogbretons.seawar.model.cases.Case;
import fr.lesprogbretons.seawar.screen.manager.EditeurMapManager;
import fr.lesprogbretons.seawar.screen.manager.GameMapManager;
import fr.lesprogbretons.seawar.screen.ui.GameUi;
import fr.lesprogbretons.seawar.screen.manager.MapManager;
import fr.lesprogbretons.seawar.screen.ui.Ui;
import fr.lesprogbretons.seawar.screen.ui.UiEditeur;
import fr.lesprogbretons.seawar.utils.TiledCoordinates;
import fr.lesprogbretons.seawar.utils.Utils;

import java.util.ArrayList;

import static fr.lesprogbretons.seawar.SeaWar.editeur;
import static fr.lesprogbretons.seawar.SeaWar.partie;


/**
 * Classe qui permet d'afficher une carte prédéfinie
 * <p>
 * Inspiré par PixelScientist et Libgdx
 * </p>
 */
public class SeaWarMapScreen extends ScreenAdapter {

    private static final int WIDTH_HEXA = 112;
    private static final int HEIGHT_HEXA = 97;

    public static final String MAP_LAYER_NAME = "map";
    public static final String SELECT_LAYER_NAME = "select";
    public static final String SHIP_LAYER_NAME = "ship";
    public static final String ORIENTATION_LAYER_NAME = "orientation";

    //Map
    private TiledMap map;
    private MapLayers layers;
    private TiledMapTile[] tiles;
    //Size
    private int widthMap;
    private int heightMap;

    //Camera et rendu
    private OrthographicCamera camera;
    private MapOrthoCamController cameraController;
    private HexagonalTiledMapRenderer renderer;
    private TextureAtlas hexture;

    //Ui
    private Ui myUi;
    private MapManager manager;

    //Sélection
    public static TiledCoordinates selectedTile;

    public SeaWarMapScreen(GameMapManager manager) {
        this.manager = manager;
        widthMap = partie.getMap().getLargeur();
        heightMap = partie.getMap().getHauteur();
        manager.setMyMapScreen(this);
    }

    public SeaWarMapScreen(EditeurMapManager manager){
        this.manager = manager;
        widthMap = editeur.getMap().getLargeur();
        heightMap = editeur.getMap().getHauteur();
        manager.setMyMapScreen(this);
    }

    public MapOrthoCamController getCameraController() {
        return cameraController;
    }

    public void setCameraController(MapOrthoCamController cameraController) {
        this.cameraController = cameraController;
    }

    public static TiledCoordinates getSelectedTile() {
        return selectedTile;
    }

    public static void setSelectedTile(TiledCoordinates selectedTile) {
        SeaWarMapScreen.selectedTile = selectedTile;
    }

    @Override
    public void show() {
        //Redimentionner l'écran pour faire rentrer la map
        int width = 800;
        int height = 800;
        Gdx.graphics.setWindowedMode(width, height);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * width, height);

        cameraController = new MapOrthoCamController(camera, widthMap, heightMap, WIDTH_HEXA, HEIGHT_HEXA, width, height);
        camera.update();

        hexture = (TextureAtlas) SeaWar.assets.get(Assets.hexes);

        map = new TiledMap();
        map.getProperties().put("staggerindex", "even");
        layers = map.getLayers();

        //Construction de la carte
        tiles = new TiledMapTile[16];
        tiles[0] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("hexblue")));
        tiles[1] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("hexgreen")));
        tiles[2] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("hexphare")));
        tiles[3] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship1")));
        tiles[4] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship2")));
        tiles[5] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship3")));
        tiles[6] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship4")));
        tiles[7] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("select")));
        tiles[8] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("myship")));
        tiles[9] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("attack")));
        tiles[10] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("arrownorth")));
        tiles[11] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("arrownortheast")));
        tiles[12] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("arrownorthwest")));
        tiles[13] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("arrowsouth")));
        tiles[14] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("arrowsoutheast")));
        tiles[15] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("arrowsouthwest")));


        //region Génération map
        addNewLayer(MAP_LAYER_NAME);
        addNewLayer(SELECT_LAYER_NAME);
        addNewLayer(SHIP_LAYER_NAME);
        addNewLayer(ORIENTATION_LAYER_NAME);
        //endregion

        //Ui
        switch (manager.getMyUi()) {
            case GAME:
                myUi = new GameUi();
                break;
            case EDITOR:
                myUi = new UiEditeur((EditeurMapManager) manager,this);
                break;
        }
        manager.setMyUi(myUi);

        renderer = new HexagonalTiledMapRenderer(map);


        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(myUi);
        multiplexer.addProcessor(cameraController);
        Gdx.input.setInputProcessor(multiplexer);

        manager.start();
    }

    private void addNewLayer(String layerName) {
        TiledMapTileLayer layer = new TiledMapTileLayer(widthMap, heightMap, WIDTH_HEXA, HEIGHT_HEXA);
        layer.setName(layerName);
        layers.add(layer);
    }

    @Override
    public void resize(int width, int height) {
        //A changer en fonction de comment est géré le stage
        // i.e. si c'est une classe que l'on redéfinit ou pas
    }

    @Override
    public void render(float delta) {
        Utils.clearScreen();
        //On regénère la map
        Utils.generateMap((TiledMapTileLayer) layers.get(MAP_LAYER_NAME), tiles);

        //Act GameUi
        myUi.act(delta);

        //Update view if needed
        cameraController.changeView(delta);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        //Draw GameUi
        myUi.draw();
        //Update Manager
        manager.update();

        //Mise à jour de la sélection
        cameraController.clicked = manager.updateSelection(cameraController.clicked,
                cameraController.rightClicked, cameraController.touchX, cameraController.touchY);
    }

    //region Layer Edit
    public void batchSelectionMark(ArrayList<Case> cases) {
        for (Case c : cases) {
            markSelectedTile(c.getY(), c.getX());
        }
    }

    public void batchAttackMark(ArrayList<Case> cases) {
        for (Case c : cases) {
            markAttackTile(c.getY(), c.getX());
        }
    }

    public void markSelectedTile(int x, int y) {
        //Selectionner le bon layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(SELECT_LAYER_NAME);

        //Mettre la tile de selection
        Cell selected = new Cell();
        selected.setTile(tiles[7]);
        layer.setCell(x, y, selected);
    }

    private void markAttackTile(int x, int y) {
        //Selectionner le bon layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(SELECT_LAYER_NAME);

        //Mettre la tile de selection
        Cell selected = new Cell();
        selected.setTile(tiles[9]);
        layer.setCell(x, y, selected);
    }

    public void markShipTile(int x, int y) {
        //Selectionner le bon layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(SHIP_LAYER_NAME);

        //Mettre la tile de selection
        Cell selected = new Cell();
        selected.setTile(tiles[8]);
        layer.setCell(x, y, selected);
    }

    public void markOrientationTile(int x, int y, Orientation o) {
        //Selectionner le bon layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(ORIENTATION_LAYER_NAME);

        //Mettre la tile de selection
        Cell selected = new Cell();

        switch (o) {
            case NORD:
                selected.setTile(tiles[10]);
                break;
            case SUD:
                selected.setTile(tiles[13]);
                break;
            case NORDEST:
                selected.setTile(tiles[11]);
                break;
            case NORDOUEST:
                selected.setTile(tiles[12]);
                break;
            case SUDEST:
                selected.setTile(tiles[14]);
                break;
            case SUDOUEST:
                selected.setTile(tiles[15]);
                break;
        }
        layer.setCell(x, y, selected);
    }

    public void removeLayerMark(String layerName) {
        //Selectionner le bon layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);
        for (int y = 0; y < heightMap; y++) {
            for (int x = 0; x < widthMap; x++) {
                layer.setCell(x, y, null);
            }
        }
    }
    //endregion

    @Override
    public void dispose() {
        renderer.dispose();
        hexture.dispose();
        map.dispose();
        myUi.dispose();
    }
}