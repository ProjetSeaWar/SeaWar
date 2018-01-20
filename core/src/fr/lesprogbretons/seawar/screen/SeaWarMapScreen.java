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
import fr.lesprogbretons.seawar.model.boat.Amiral;
import fr.lesprogbretons.seawar.model.boat.Boat;
import fr.lesprogbretons.seawar.model.boat.Fregate;
import fr.lesprogbretons.seawar.model.cases.Case;
import fr.lesprogbretons.seawar.model.cases.CaseEau;
import fr.lesprogbretons.seawar.model.cases.CaseTerre;
import fr.lesprogbretons.seawar.model.map.Grille;
import fr.lesprogbretons.seawar.utils.TiledCoordinates;
import fr.lesprogbretons.seawar.utils.Utils;

import java.util.ArrayList;

import static fr.lesprogbretons.seawar.SeaWar.*;


/**
 * Classe qui permet d'afficher une carte prédéfinie
 * <p>
 * Inspiré par PixelScientist et Libgdx
 * </p>
 */
public class SeaWarMapScreen extends ScreenAdapter {

    private static final int WIDTH_MAP = 13;
    private static final int HEIGHT_MAP = 11;
    private static final int WIDTH_HEXA = 112;
    private static final int HEIGHT_HEXA = 97;

    private static final String MAP_LAYER_NAME = "map";
    private static final String SELECT_LAYER_NAME = "select";
    private static final String START_LAYER_NAME = "start";

    //Map
    private TiledMap map;
    private MapLayers layers;
    private TiledMapTile[] tiles;

    private OrthographicCamera camera;
    private MapOrthoCamController cameraController;
    private HexagonalTiledMapRenderer renderer;
    private TextureAtlas hexture;

    //Ui
    private Ui myUi;

    //Sélection
    private TiledCoordinates selectedTile = new TiledCoordinates(0, 0);
    private TiledCoordinates previousSelectedTile = new TiledCoordinates(0, 0);

    //Modèle
    private Grille g = partie.getMap();


    @Override
    public void show() {
        //Redimentionner l'écran pour faire rentrer la map
        //Prendre en compte la taille de l'UI
        int width = 800;
        int height = 800;
        Gdx.graphics.setWindowedMode(width, height);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * width, height);

        cameraController = new MapOrthoCamController(camera, WIDTH_MAP, HEIGHT_MAP, WIDTH_HEXA, HEIGHT_HEXA, width, height);
        camera.update();

        hexture = (TextureAtlas) SeaWar.assets.get(Assets.hexes);

        map = new TiledMap();
        map.getProperties().put("staggerindex", "even");
        layers = map.getLayers();

        //Construction de la carte
        tiles = new TiledMapTile[9];
        tiles[0] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("hexblue")));
        tiles[1] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("hexgreen")));
        tiles[2] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("hexphare")));
        tiles[3] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship1")));
        tiles[4] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship2")));
        tiles[5] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship3")));
        tiles[6] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("ship4")));
        tiles[7] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("select")));
        tiles[8] = new StaticTiledMapTile(new TextureRegion(hexture.findRegion("start")));


        //region Génération map
        TiledMapTileLayer layer = new TiledMapTileLayer(WIDTH_MAP, HEIGHT_MAP, WIDTH_HEXA, HEIGHT_HEXA);
        layer.setName(MAP_LAYER_NAME);
        generateMap(layer);
        layers.add(layer);

        TiledMapTileLayer layerStart = new TiledMapTileLayer(WIDTH_MAP, HEIGHT_MAP, WIDTH_HEXA, HEIGHT_HEXA);
        layerStart.setName(START_LAYER_NAME);
        layers.add(layerStart);

        TiledMapTileLayer layerSelect = new TiledMapTileLayer(WIDTH_MAP, HEIGHT_MAP, WIDTH_HEXA, HEIGHT_HEXA);
        layerSelect.setName(SELECT_LAYER_NAME);
        layers.add(layerSelect);
        //endregion


        //region Ui

        myUi = new Ui();

        //endregion

        renderer = new HexagonalTiledMapRenderer(map);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(myUi);
        multiplexer.addProcessor(cameraController);
        Gdx.input.setInputProcessor(multiplexer);

    }

    private void generateMap(TiledMapTileLayer layer) {
        for (int l = 0; l < 1; l++) {

            for (int y = 0; y < HEIGHT_MAP; y++) {
                for (int x = 0; x < WIDTH_MAP; x++) {
                    Cell cell = new Cell();
                    Case aCase = g.getCase(y, x);

                    if (g.casePossedeBateaux(aCase)) {
                        Boat boat = g.bateauSurCase(aCase);
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

    @Override
    public void resize(int width, int height) {
        //A changer en fonction de comment est géré le stage
        // i.e. si c'est une classe que l'on redéfinit ou pas
    }

    public void update() {
        //On regénère la map
        generateMap((TiledMapTileLayer) layers.get(MAP_LAYER_NAME));
        //On met à jour l'interface
        myUi.setTurn(partie.getTurnCounter());
        myUi.setPlayer(partie.getCurrentPlayer().toString());

        removeStartMark();

        ArrayList<Boat> bateaux;

        if (partie.getCurrentPlayer().getNumber() == 1) {
            bateaux = partie.getMap().getBateaux1();
        } else {
            bateaux = partie.getMap().getBateaux2();
        }

        for (Boat b : bateaux) {
            markStartTile(b.getPosition().getY(), b.getPosition().getX());
        }
    }

    @Override
    public void render(float delta) {
        Utils.clearScreen();
        myUi.act(delta);
        //Update view if needed
        cameraController.changeView(delta);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        myUi.draw();
        //Update game
        update();

        if (cameraController.clicked) {
            getSelectedHexagon(cameraController.touchX, cameraController.touchY);

            if (selectedTile.row >= 0 && selectedTile.row < HEIGHT_MAP
                    && selectedTile.column >= 0 && selectedTile.column < WIDTH_MAP) {

                if (!cameraController.rightClicked) {
                    //Clic gauche

                    //Envoie au contrôleur
                    seaWarController.selection(selectedTile.row, selectedTile.column);

                    //Retirer les sélections précédentes
                    removeSelectionMark();

                    //Si pas de bateau sélectionné
                    if (!partie.isAnyBateauSelectionne()) {

                        Case aCase = g.getCase(selectedTile.row, selectedTile.column);
                        if (g.casePossedeBateaux(aCase)) {
                            Boat boat = g.bateauSurCase(aCase);

                            batchSelectionMark(g.getCasesDisponibles(aCase, boat.getMove()));
                        }
                    }

                    //La sélection des cases ne sélectionne pas la case courante
                    markSelectedTile(selectedTile.column, selectedTile.row);

                    previousSelectedTile.setCoords(selectedTile);
                } else {
                    //Clic droit
                    //TODO Affichage des infos
                }
            }
            //Le click est consomé
            cameraController.clicked = false;
        }
    }

    public void batchSelectionMark(ArrayList<Case> cases) {
        for (Case c : cases) {
            markSelectedTile(c.getY(), c.getX());
        }
    }

    private void removeSelectionMark() {
        //Selectionner le bon layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(SELECT_LAYER_NAME);
        for (int y = 0; y < HEIGHT_MAP; y++) {
            for (int x = 0; x < WIDTH_MAP; x++) {
                layer.setCell(x, y, null);
            }
        }
    }

    private void removeStartMark() {
        //Selectionner le bon layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(START_LAYER_NAME);
        for (int y = 0; y < HEIGHT_MAP; y++) {
            for (int x = 0; x < WIDTH_MAP; x++) {
                layer.setCell(x, y, null);
            }
        }
    }

    private void markStartTile(int x, int y) {
        //Selectionner le bon layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(START_LAYER_NAME);

        //Mettre la tile de selection
        Cell selected = new Cell();
        selected.setTile(tiles[8]);
        layer.setCell(x, y, selected);
    }

    private void markSelectedTile(int x, int y) {
        //Selectionner le bon layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(SELECT_LAYER_NAME);

        //Mettre la tile de selection
        Cell selected = new Cell();
        selected.setTile(tiles[7]);
        layer.setCell(x, y, selected);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        hexture.dispose();
        map.dispose();
        myUi.dispose();
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

        logger.debug("col : " + column + " row : " + row);
        selectedTile.column = column;
        selectedTile.row = row;
    }
}