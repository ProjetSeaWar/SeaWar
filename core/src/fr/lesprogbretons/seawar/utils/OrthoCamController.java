package fr.lesprogbretons.seawar.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import static fr.lesprogbretons.seawar.SeaWar.logger;

public class OrthoCamController extends InputAdapter {

    private final OrthographicCamera camera;
    private final Vector3 curr = new Vector3();
    private final Vector3 last = new Vector3(-1, -1, -1);
    private final Vector3 delta = new Vector3();
    private final Vector3 finalPosNoZoom = new Vector3(565, 565, 0);

    private boolean toggleZoom = false;
    private float minZoom = 1.5f;
    private float maxZoom = 1.0f;
    private float displacementSpeed = 20f;

    public boolean click;
    public float touchX;
    public float touchY;


    public OrthoCamController(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        click = false;
        if (camera.zoom != 1.5f) {
            camera.unproject(curr.set(x, y, 0));
            if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
                camera.unproject(delta.set(last.x, last.y, 0));
                delta.sub(curr);
                camera.position.add(delta.x, delta.y, 0);
            }
            last.set(x, y, 0);
        }
        logger.debug(camera.position.x + ":" + camera.position.y);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        //TODO Delete
        camera.zoom += 0.1f * amount;
        if (camera.zoom > minZoom) {
            camera.zoom = minZoom;
        } else if (camera.zoom < maxZoom) camera.zoom = maxZoom;
        return false;
    }

    public void updateZoom(float delta) {
        if (toggleZoom) {
            camera.zoom += delta * displacementSpeed * 0.08f;
            if (camera.zoom > minZoom) {
                camera.zoom = minZoom;
                logger.debug("Done zoom");
            }

            if (camera.position.x < finalPosNoZoom.x - displacementSpeed) {
                camera.position.x += displacementSpeed;
            } else if (camera.position.x > finalPosNoZoom.x + displacementSpeed) {
                camera.position.x -= displacementSpeed;
            } else {
                camera.position.x = finalPosNoZoom.x;
                logger.debug("Done x");
            }

            if (camera.position.y < finalPosNoZoom.y - displacementSpeed) {
                camera.position.y += displacementSpeed;
            } else if (camera.position.y > finalPosNoZoom.y + displacementSpeed) {
                camera.position.y -= displacementSpeed;
            } else {
                camera.position.y = finalPosNoZoom.y;
                logger.debug("Done y");
            }

            if (camera.zoom == minZoom && camera.position.x == 565 && camera.position.y == 565) toggleZoom = false;
        }


    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = camera.unproject(clickCoordinates);

        touchX = position.x;
        touchY = position.y;

        click = true;

        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        last.set(-1, -1, -1);
        click = false;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.Z:
                toggleZoom = true;
                logger.debug("Actuel : " + camera.position.x + ":" + camera.position.y);
                break;
        }
        return false;
    }
}