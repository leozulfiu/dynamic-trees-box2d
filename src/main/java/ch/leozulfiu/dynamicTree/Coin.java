package ch.leozulfiu.dynamicTree;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

/**
 * @author Leo Zulfiu
 * @since 13.09.2014
 */
public class Coin extends Box2dObject {
    float x, y, r;
    boolean isTaken;
    float blink_R = 0;

    public Coin(PApplet parent, Box2DProcessing box2d, float x, float y, float r) {
        super(parent, box2d);
        this.x = x;
        this.y = y;
        this.r = r;
        createBox2dObject();
        body.setUserData(this);
    }

    @Override
    public void createBox2dObject() {
        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;

        bd.position = box2d.coordPixelsToWorld(x,y);
        body = box2d.createBody(bd);

        CircleShape cs = new CircleShape();
        cs.m_radius = box2d.scalarPixelsToWorld(r);

        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 1;
        fd.friction = 0.3f;
        fd.restitution = 0.5f;
        fd.isSensor = true;
        fd.filter.categoryBits = 0x04;
        fd.filter.maskBits = 0xffff;

        body.createFixture(fd);
    }

    public void update() {
        if (r < 0) {
            box2d.destroyBody(body);
            isTaken = false;
        }
        if (isTaken) {
            r-=3;
            body.getFixtureList().getShape().setRadius(r);
        }
        blink_R+=0.3;
        if (blink_R > 30) {
            blink_R = 0;
        }
    }

    @Override
    public void draw() {
        if (r > 0) {
            Vec2 pos = box2d.getBodyPixelCoord(body);
            float a = body.getAngle();
            parent.pushMatrix();
            parent.translate(pos.x, pos.y);
            parent.rotate(a);

            parent.noFill();
            parent.stroke(160);
            parent.strokeWeight(2);
            parent.ellipse(0, 0, blink_R * 2, blink_R * 2);

            parent.fill(244, 108, 104);
            parent.noStroke();
            parent.ellipse(0, 0, r * 2, r * 2);
            parent.popMatrix();
        }
    }

    public void destroy() {
        isTaken = true;
    }
}
