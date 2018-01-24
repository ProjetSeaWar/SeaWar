package fr.lesprogbretons.seawar.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static fr.lesprogbretons.seawar.SeaWar.logger;

public class MapOrthoCamController extends InputAdapter {

    private final OrthographicCamera camera;

    private final Vector3 curr = new Vector3();
    private final Vector3 last = new Vector3(-1, -1, -1);
    private final Vector3 delta = new Vector3();

    private final Vector2 finalPos = new Vector2();

    private boolean fullSize = false;
    private boolean zooming = false;
    private boolean displacement = false;
    private float minZoom;
    private float maxZoom = 1f;
    private float targetZoom;
    private float displacementSpeed = 20f;

    private float downBoundX;
    private float downBoundY;
    private float upBoundX;
    private float upBoundY;

    public boolean clicked;
    public boolean rightClicked;
    public float touchX;
    public float touchY;


    public MapOrthoCamController(OrthographicCamera camera, int widthMap, int heightMap, int widthHexa, int heightHexa, int camMapWidth, int camMapHeight) {
        this.camera = camera;

        //Nombre de tiles en lageur / 2 -> Nb de full tiles + Nombre de tiles en lageur / 4 -> nb moitiés de tiles
        // + 0.25f pour le décalage
        float xMap = (widthMap / 2f + (widthMap / 4f) + 0.25f) * widthHexa;
        //Nombre de tiles en hauteur + 0.5f pour le décalage entre les colones
        float yMap = (heightMap + 0.5f) * heightHexa;

        //Diviser par 2 pour position
        finalPos.x = xMap / 2f;
        finalPos.y = yMap / 2f;

        //TODO Changer par un truc qui se base sur la largeur/hauteur de la map < au truc visible
        if (finalPos.x > finalPos.y) {
            minZoom = MathUtils.ceil(1.06f * widthMap) / 10f;
        } else {
            minZoom = MathUtils.ceil(1.24f * heightMap) / 10f;
        }

        targetZoom = minZoom;
        camera.zoom = targetZoom;

        downBoundX = camMapWidth / 2f;
        downBoundY = camMapHeight / 2f;
        upBoundX = xMap - camMapWidth / 2f;
        upBoundY = yMap - camMapHeight / 2f;

        logger.debug("Zoom : " + minZoom + " | final : " + finalPos.toString());

        camera.position.set(finalPos, 0);
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        //On ne veut pas cliquer lors du déplacement de la map
        if (camera.zoom != minZoom && !zooming && !displacement) {
            camera.unproject(curr.set(x, y, 0));
            if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
                camera.unproject(delta.set(last.x, last.y, 0));
                delta.sub(curr);
                camera.position.add(delta.x, delta.y, 0);
                clicked = false;
            }
            last.set(x, y, 0);
        }


        if (camera.position.x < downBoundX) camera.position.x = downBoundX;
        if (camera.position.x > upBoundX) camera.position.x = upBoundX;
        if (camera.position.y < downBoundY) camera.position.y = downBoundY;
        if (camera.position.y > upBoundY) camera.position.y = upBoundY;

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        zooming = true;
        if (amount == 1) {
            fullSize = true;
            targetZoom = minZoom;
            displacement = true;
        } else if (amount == -1) {
            fullSize = false;
            targetZoom = maxZoom;
        }

        if (camera.zoom == targetZoom) {
            zooming = false;
        }
        logger.debug("Actuel : " + camera.position.x + ":" + camera.position.y);
        return false;
    }

    public void changeView(float delta) {
        //Manage zoom
        if (camera.zoom != targetZoom) {
            float zoom = delta * displacementSpeed * 0.08f;
            if (fullSize) {
                if (camera.zoom > targetZoom) {
                    camera.zoom = targetZoom;
                    logger.debug("Done zoom to min");
                    zoom = 0;
                    zooming = false;
                }
            } else {
                zoom = -zoom;
                if (camera.zoom < targetZoom) {
                    camera.zoom = targetZoom;
                    logger.debug("Done zoom to max");
                    zoom = 0;
                    zooming = false;
                }
            }
            camera.zoom += zoom;
        }

        if (displacement) {
            float dist = displacementSpeed;
            float distX = 0, distY = 0;
            if (camera.position.x > (finalPos.x + dist)) {
                distX = -dist;
            } else if (camera.position.x < (finalPos.x - dist)) {
                distX = dist;
            } else {
                camera.position.x = finalPos.x;
            }

            if (camera.position.y > (finalPos.y + dist)) {
                distY = -dist;
            } else if (camera.position.y < (finalPos.y - dist)) {
                distY = dist;
            } else {
                camera.position.y = finalPos.y;
            }
            camera.translate(distX, distY);

            if (camera.position.x == finalPos.x && camera.position.y == finalPos.y) {
                displacement = false;
                logger.debug("Done displacement");
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = camera.unproject(clickCoordinates);

        touchX = position.x;
        touchY = position.y;

        rightClicked = button != Input.Buttons.LEFT;
        clicked = true;
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        last.set(-1, -1, -1);
//        clicked = true;
        return false;
    }
}