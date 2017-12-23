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
    private final float downBound = 400;
    private final float upBound = 715;

    private boolean fullSize = false;
    private boolean zooming = false;
    private boolean displacement = false;
    private float minZoom;
    private float maxZoom = 1f;
    private float targetZoom;
    private float displacementSpeed = 20f;

    public boolean clicked;
    private boolean dragged;
    public float touchX;
    public float touchY;


    public MapOrthoCamController(OrthographicCamera camera, int widthMap, int heigthMap, int widthHexa, int heigthHexa) {
        this.camera = camera;

        finalPos.x = MathUtils.floor((widthHexa * widthMap) / 2.6f);
        finalPos.y = MathUtils.floor((heigthHexa * heigthMap) / 1.9f) - 1;


        minZoom = MathUtils.ceil(((widthHexa * widthMap) / (heigthHexa * heigthMap)) * 100f) / 100f;
//        minZoom = MathUtils.floor((((widthHexa * widthMap * heigthHexa * heigthMap)) / 1109680f) * 10f) / 10f;
        minZoom = 6.2f;

        logger.debug("Zoom : " + minZoom + " | final : " + finalPos.toString());


        targetZoom = minZoom;
        camera.zoom = targetZoom;

        camera.position.set(finalPos, 0);
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        //On ne veut pas cliquer lors du déplacement de la map
        dragged = true;
//        if (camera.zoom != minZoom && !zooming && !displacement) {
        camera.unproject(curr.set(x, y, 0));
        if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
            camera.unproject(delta.set(last.x, last.y, 0));
            delta.sub(curr);
            camera.position.add(delta.x, delta.y, 0);
        } else {
            dragged = false;
        }
        last.set(x, y, 0);
//        }


//        if (camera.position.x < downBound) camera.position.x = downBound;
//        if (camera.position.x > upBound) camera.position.x = upBound;
//        if (camera.position.y < downBound) camera.position.y = downBound;
//        if (camera.position.y > upBound) camera.position.y = upBound;

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
        if (button == Input.Buttons.LEFT) {
            Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
            Vector3 position = camera.unproject(clickCoordinates);

            touchX = position.x;
            touchY = position.y;
        }
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        last.set(-1, -1, -1);
        if (!dragged) clicked = true;
        dragged = false;
        return false;
    }
}