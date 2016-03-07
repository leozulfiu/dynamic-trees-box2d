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
 * @since 07.08.2014
 */
public class TreePart extends Box2dObject {
    float w, h, x, y, angle;
    boolean isBase;

    public TreePart(PApplet parent, Box2DProcessing box2d, float x, float y, float w, float h, float angle, boolean isBase) {
        super(parent, box2d);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.angle = angle;
        this.isBase = isBase;
        createBox2dObject();
    }

    @Override
    public void createBox2dObject() {
        BodyDef bodyDef = new BodyDef();
        if (isBase) {
            bodyDef.setType(BodyType.STATIC);
        } else {
            bodyDef.setType(BodyType.DYNAMIC);
        }
        bodyDef.position.set(box2d.coordPixelsToWorld(x, y));
        bodyDef.angle = (float) -Math.toRadians(angle); //rotates clockwise!

        body = box2d.createBody(bodyDef);

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
        parent.fill(219, 199, 137);
        parent.rectMode(CENTER);
        parent.rect(0, 0, w, h);
        parent.popMatrix();
    }
}
