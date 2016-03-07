package ch.leozulfiu.dynamicTree;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

import static processing.core.PConstants.CENTER;

/**
 * @author Leo Zulfiu
 * @since 13.09.2014
 */
public class Teleport extends Box2dObject {
    float x, y, w, h;

    public Teleport(PApplet parent, Box2DProcessing box2d, float x, float y, int w, int h) {
        super(parent, box2d);
        this.x = x+w/2;
        this.y = y+h/2;
        this.w = w;
        this.h = h;
        createBox2dObject();
        body.setUserData(this);
    }

    @Override
    public void createBox2dObject() {
        BodyDef bd = new BodyDef();
        bd.setType(BodyType.DYNAMIC);
        bd.position.set(box2d.coordPixelsToWorld(x, y));

        body = box2d.createBody(bd);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(box2d.scalarPixelsToWorld(w / 2), box2d.scalarPixelsToWorld(h / 2));


        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 1;
        fd.friction = 0.3f;
        fd.restitution = 0.5f;
        fd.isSensor = true;
        fd.filter.categoryBits = 0x04;
        fd.filter.maskBits = 0xffff;

        body.createFixture(fd);
    }

    public void update() {

    }

    @Override
    public void draw() {
        Vec2 bodyPixelCoord = box2d.getBodyPixelCoord(body);
        float angle = body.getAngle();

        parent.pushMatrix();
        parent.translate(bodyPixelCoord.x, bodyPixelCoord.y);
        parent.rotate(-angle);
        parent.noStroke();
        parent.fill(0);
        parent.rectMode(CENTER);
        parent.rect(0, 0, w, h, 7);
        parent.popMatrix();
    }


}
