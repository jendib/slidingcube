package com.slidingcube.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.slidingcube.constant.ConfigConstants;
import com.slidingcube.entity.Player;

import java.util.List;

/**
 * Handling of the game camera.
 *
 * @author bgamard
 */
public class CameraHandler {
    private OrthographicCamera camera; // Scene camera
    private List<Player> playerList; // List of active players

    /**
     * Create a new camera handler.
     *
     * @param camera Scene camera
     * @param playerList List of players
     */
    public CameraHandler(OrthographicCamera camera, List<Player> playerList) {
        this.camera = camera;
        this.playerList = playerList;
    }

    /**
     * Update the camera position and viewport.
     */
    public void update() {
        // camera positionned in the middle on all players
        float sumX = 0;
        float sumY = 0;
        for (Player player : playerList) {
            Vector2 position = player.getPosition();
            sumX += position.x;
            sumY += position.y;
        }
        camera.position.set(sumX / playerList.size(), sumY / playerList.size(), 0);

        // camera viewport containing all players
        float[] bbPlayers = getPlayerBoundingBox();
        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth(); // 0.5625 for 16:9 screen
        float width = Math.abs(bbPlayers[0] - bbPlayers[2]) * 2f // base viewport
                + Gdx.graphics.getWidth() * ConfigConstants.HORIZONTAL_CAMERA_MARGIN; // add some margin
        float height = Math.abs(bbPlayers[1] - bbPlayers[3]) * 2f // base viewport
                + Gdx.graphics.getHeight() * ConfigConstants.VERTICAL_CAMERA_MARGIN; // add some margin
        if (width * aspectRatio > height) {
            camera.viewportWidth = width;
            camera.viewportHeight = width * aspectRatio;
        } else {
            camera.viewportWidth = height * (1 / aspectRatio);
            camera.viewportHeight = height;
        }
        camera.update();
    }

    /**
     * Returns players bounding box array.
     *
     * @return array of the bounding box coordinates (x1,y1,x2,y2)
     */
    private float[] getPlayerBoundingBox() {
        float[] output = new float[] { Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
                Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY };
        for (Player player : playerList) {
            Vector2 position = player.getPosition();
            if (position.x < output[0]) {
                output[0] = position.x;
            }
            if (position.y < output[1]) {
                output[1] = position.y;
            }
            if (position.x > output[2]) {
                output[2] = position.x;
            }
            if (position.y > output[3]) {
                output[3] = position.y;
            }
        }
        return output;
    }
}