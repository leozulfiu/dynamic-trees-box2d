package ch.leozulfiu.dynamicTree;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

import static processing.core.PConstants.CENTER;

/**
 * @author Leo Zulfiu
 * @since 30.07.2014
 */
public class StaticObject extends Box2dObject {
    float w, h, x, y;

    public StaticObject(PApplet parent, Box2DProcessing box2d, int x, int y, int w, int h) {
        super(parent, box2d);
        this.x = x+w/2;
        this.y = y+h/2;
        this.w = w;
        this.h = h;
        createBox2dObject();
    }

    public StaticObject(PApplet parent, Box2DProcessing box2d, double x, double y, double width, double height) {
        super(parent, box2d);
        this.x = (float) x+w/2;
        this.y = (float) y+h/2;
        this.w = (float) width;
        this.h = (float) height;
        createBox2dObject();
    }

    @Override
    public void createBox2dObject() {
        BodyDef bd = new BodyDef();
        bd.setType(BodyType.STATIC);
        bd.position.set(box2d.coordPixelsToWorld(x, y));

        body = box2d.createBody(bd);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(box2d.scalarPixelsToWorld(w/2), box2d.scalarPixelsToWorld(h/2));

        body.createFixture(ps, 1);
    }

    @Override
    public void draw() {
        Vec2 bodyPixelCoord = box2d.getBodyPixelCoord(body);
        float angle = body.getAngle();

        parent.pushMatrix();
        parent.translate(bodyPixelCoord.x, bodyPixelCoord.y);
        parent.rotate(-angle);
        parent.noStroke();
        parent.fill(138, 116, 92);
        parent.rectMode(CENTER);
        parent.rect(0, 0, w, h);
        parent.popMatrix();
    }
}
