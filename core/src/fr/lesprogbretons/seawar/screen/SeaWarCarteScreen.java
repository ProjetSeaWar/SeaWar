package fr.lesprogbretons.seawar.screen;

import com.badlogic.gdx.Gdx;
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

    TiledMap map;

    //TODO Faire autrement
    TiledMapTileLayer layer;
    TiledMapTile[] tiles;

    OrthographicCamera camera;
    OrthoCamController cameraController;
    HexagonalTiledMapRenderer renderer;
    Texture hexture;

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
        Gdx.input.setInputProcessor(cameraController);

        hexture = (Texture) SeaWar.assets.get(Assets.hexes);
        TextureRegion[][] hexes = TextureRegion.split(hexture, 115, 100);

        map = new TiledMap();
        MapLayers layers = map.getLayers();

        tiles = new TiledMapTile[4];
        tiles[0] = new StaticTiledMapTile(new TextureRegion(hexes[0][0]));
        tiles[1] = new StaticTiledMapTile(new TextureRegion(hexes[0][1]));
        tiles[2] = new StaticTiledMapTile(new TextureRegion(hexes[1][0]));
        tiles[3] = new StaticTiledMapTile(new TextureRegion(hexes[1][1]));

        for (int l = 0; l < 1; l++) {
            layer = new TiledMapTileLayer(13, 11, 112, 97);
            for (int y = 0; y < 11; y++) {
                for (int x = 0; x < 13; x++) {
                    int id = (int) (Math.random() * 2) + 1;
                    Cell cell = new Cell();
                    cell.setTile(tiles[id]);
                    layer.setCell(x, y, cell);
                }
            }
            layers.add(layer);
        }

        renderer = new HexagonalTiledMapRenderer(map);

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
            //Format colone/ligne
            int x = (int) (cameraController.touchX / layer.getTileWidth());
            int y = (int) (cameraController.touchY / layer.getTileHeight());
            logger.debug("x : " + x + " y : " + y);
//            for (int i = 0; i < 13; i++) {
//                for (int j = 0; j < 11; j++) {
//                    layer.getCell(i,j).setTile(tiles[i%3]);
//                }
//            }
            layer.getCell(x, y).setTile(tiles[0]);

        }


    }

    @Override
    public void dispose() {
        renderer.dispose();
        hexture.dispose();
        map.dispose();
    }
}
