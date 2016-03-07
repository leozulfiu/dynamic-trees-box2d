package ch.leozulfiu.dynamicTree;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

/**
 * @author Leo Zulfiu
 * @since 30.07.2014
 */
public abstract class Box2dObject implements Createable {
    PApplet parent;
    Box2DProcessing box2d;
    Body body;

    public Box2dObject(PApplet parent, Box2DProcessing box2d) {
        this.parent = parent;
        this.box2d = box2d;
    }

    boolean contains(float x, float y) {
        Vec2 worldPoint = box2d.coordPixelsToWorld(x, y);
        Fixture f = body.getFixtureList();
        return f.testPoint(worldPoint);
    }
}
