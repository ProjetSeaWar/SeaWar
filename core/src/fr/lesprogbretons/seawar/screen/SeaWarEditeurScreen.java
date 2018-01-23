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
import fr.lesprogbretons.seawar.model.boat.Boat;
import fr.lesprogbretons.seawar.model.cases.Case;
import fr.lesprogbretons.seawar.model.map.Grille;
import fr.lesprogbretons.seawar.screen.ui.UiEditeur;
import fr.lesprogbretons.seawar.utils.TiledCoordinates;
import fr.lesprogbretons.seawar.utils.Utils;

import java.util.ArrayList;

import static fr.lesprogbretons.seawar.SeaWar.*;


/**
 * Classe qui permet d'afficher l'éditeur de carte
 * <p>
 * Inspiré par PixelScientist et Libgdx
 * </p>
 */
public class SeaWarEditeurScreen extends ScreenAdapter {

    private static final int WIDTH_MAP = 13;
    private static final int HEIGHT_MAP = 11;
    private static final int WIDTH_HEXA = 112;
    private static final int HEIGHT_HEXA = 97;

    private static final String MAP_LAYER_NAME = "map";
    private static final String SELECT_LAYER_NAME = "select";
    private static final String SHIP_LAYER_NAME = "ship";
    private static final String ORIENTATION_LAYER_NAME = "orientation";

    //Map
    private TiledMap map;
    private MapLayers layers;
    private TiledMapTile[] tiles;

    private OrthographicCamera camera;
    private MapOrthoCamController cameraController;
    private HexagonalTiledMapRenderer renderer;
    private TextureAtlas hexture;

    //GameUi
    private UiEditeur myUi;

    //Sélection
    private TiledCoordinates selectedTile = new TiledCoordinates(0, 0);
    private TiledCoordinates previousSelectedTile = new TiledCoordinates(0, 0);

    //Modèle
    private Grille g = editeur.getMap();


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
        Utils.generateMap((TiledMapTileLayer) layers.get(MAP_LAYER_NAME), tiles);

        addNewLayer(SELECT_LAYER_NAME);
        addNewLayer(SHIP_LAYER_NAME);
        addNewLayer(ORIENTATION_LAYER_NAME);
        //endregion


        //region GameUi

        myUi = new UiEditeur();

        //endregion

        renderer = new HexagonalTiledMapRenderer(map);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(myUi);
        multiplexer.addProcessor(cameraController);
        Gdx.input.setInputProcessor(multiplexer);

    }

    private void addNewLayer(String layerName) {
        TiledMapTileLayer layer = new TiledMapTileLayer(WIDTH_MAP, HEIGHT_MAP, WIDTH_HEXA, HEIGHT_HEXA);
        layer.setName(layerName);
        layers.add(layer);
    }

    @Override
    public void resize(int width, int height) {
        //A changer en fonction de comment est géré le stage
        // i.e. si c'est une classe que l'on redéfinit ou pas
    }

    private void update() {
        //On regénère la map
        Utils.generateMap((TiledMapTileLayer) layers.get(MAP_LAYER_NAME), tiles);
        //On met à jour l'interface

        removeLayerMark(SHIP_LAYER_NAME);
        removeLayerMark(ORIENTATION_LAYER_NAME);

        ArrayList<Boat> bateaux;

        if (partie.getCurrentPlayer().getNumber() == 1) {
            bateaux = g.getBateaux1();
        } else {
            bateaux = g.getBateaux2();
        }

        for (Boat b : bateaux) {
            markShipTile(b.getPosition().getY(), b.getPosition().getX());
        }

        for (Boat b : g.getBateaux1()) {
            markOrientationTile(b.getPosition().getY(), b.getPosition().getX(), b.getOrientation());
        }

        for (Boat b : g.getBateaux2()) {
            markOrientationTile(b.getPosition().getY(), b.getPosition().getX(), b.getOrientation());
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
            Utils.getSelectedHexagon(cameraController.touchX, cameraController.touchY, selectedTile);

            if (selectedTile.row >= 0 && selectedTile.row < HEIGHT_MAP
                    && selectedTile.column >= 0 && selectedTile.column < WIDTH_MAP) {

                if (!cameraController.rightClicked) {
                    //Clic gauche

                    //Envoie au contrôleur
                    seaWarController.selection(selectedTile.row, selectedTile.column);

                    //Retirer les sélections précédentes
                    removeLayerMark(SELECT_LAYER_NAME);

                    Case aCase = g.getCase(selectedTile.row, selectedTile.column);

                    //Si pas de bateau sélectionné
                    if (partie.isAnyBateauSelectionne()) {
                        if (g.casePossedeBateaux(aCase)) {
                            Boat boat = g.bateauSurCase(aCase);
                            if (boat.getJoueur().equals(partie.getCurrentPlayer())) {
                                if (boat.getMoveAvailable() > 0) batchSelectionMark(g.getCasesDisponibles(aCase, 1));
                                batchAttackMark(g.getBoatInRange(boat, partie.getOtherPlayer()));
                            } else {
                                batchSelectionMark(g.getCasesDisponibles(aCase, boat.getMoveAvailable()));
                            }
                            //La sélection des cases ne sélectionne pas la case courante
                            markSelectedTile(selectedTile.column, selectedTile.row);
                        }
                    } else {
                        removeLayerMark(SELECT_LAYER_NAME);
                        if (!g.casePossedeBateaux(aCase)) {
                            markSelectedTile(selectedTile.column, selectedTile.row);
                        } else {
                            if (!g.casePossedeBateau(aCase, partie.getCurrentPlayer())) {
                                Boat boat = g.bateauSurCase(aCase);
                                batchSelectionMark(g.getCasesDisponibles(aCase, boat.getMove()));
                                markSelectedTile(selectedTile.column, selectedTile.row);
                            }
                        }
                    }
                    previousSelectedTile.setCoords(selectedTile);
                } else {
                    //Clic droit
                    //TODO Affichage des turnInfos
                }
            }
            //Le click est consomé
            cameraController.clicked = false;
        }
    }

    //region Layer Edit

    private void batchSelectionMark(ArrayList<Case> cases) {
        for (Case c : cases) {
            markSelectedTile(c.getY(), c.getX());
        }
    }

    private void batchAttackMark(ArrayList<Case> cases) {
        for (Case c : cases) {
            markAttackTile(c.getY(), c.getX());
        }
    }

    private void markSelectedTile(int x, int y) {
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

    private void markShipTile(int x, int y) {
        //Selectionner le bon layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(SHIP_LAYER_NAME);

        //Mettre la tile de selection
        Cell selected = new Cell();
        selected.setTile(tiles[8]);
        layer.setCell(x, y, selected);
    }

    private void markOrientationTile(int x, int y, Orientation o) {
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

    private void removeLayerMark(String layerName) {
        //Selectionner le bon layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);
        for (int y = 0; y < HEIGHT_MAP; y++) {
            for (int x = 0; x < WIDTH_MAP; x++) {
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