package ch.leozulfiu.dynamicTree;

import shiffman.box2d.Box2DProcessing;

/**
 * @author Leo Zulfiu
 * @since 11.09.2014
 */
public class Camera {
    private final Main parent;
    private final Box2DProcessing box2d;
    public static final int VIEWPORT_SIZE_X = 640;
    public static final int VIEWPORT_SIZE_Y = 480;
    public static final int TILE_SIZE = 32;
    public int WORLD_SIZE_X;
    public int WORLD_SIZE_Y;

    float offsetMaxX;
    float offsetMaxY;
    float offsetMinX = 0;
    float offsetMinY = 0;

    float camX;
    float camY;

    public Camera(Main parent, Box2DProcessing box2d, int width, int height) {
        this.parent = parent;
        this.box2d = box2d;

        WORLD_SIZE_X = width * TILE_SIZE;
        WORLD_SIZE_Y = height * TILE_SIZE;

        offsetMaxX = WORLD_SIZE_X - VIEWPORT_SIZE_X;
        offsetMaxY = WORLD_SIZE_Y - VIEWPORT_SIZE_Y;
    }

    public void update() {
        camX = box2d.getBodyPixelCoord(parent.player.body).x - VIEWPORT_SIZE_X / 2;
        camY = box2d.getBodyPixelCoord(parent.player.body).y - VIEWPORT_SIZE_Y / 2;

        if (camX > offsetMaxX) {
            camX = offsetMaxX;
        } else if (camX < offsetMinX) {
            camX = offsetMinX;
        }

        if (camY > offsetMaxY) {
            camY = offsetMaxY;
        } else if (camY < offsetMinY) {
            camY = offsetMinY;
        }

        parent.translate(-camX, -camY);
    }
}
