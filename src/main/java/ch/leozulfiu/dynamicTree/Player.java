package ch.leozulfiu.dynamicTree;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

import static processing.core.PConstants.*;
import static processing.core.PConstants.CENTER;

/**
 * @author Leo Zulfiu
 * @since 10.09.2014
 */
public class Player extends Box2dObject {
    float MAX_VEL_X = 50;
    float MAX_VEL_Y = 50;
    int SPEED = 1000;

    float w, h, x, y;

    public Player(PApplet parent, Box2DProcessing box2d, int x, int y, int w, int h) {
        super(parent, box2d);
        //convert from center to left upper edge
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
        parent.fill(244, 239, 209);
        parent.rectMode(CENTER);
        parent.rect(0, 0, w, h);
        parent.popMatrix();
    }

    public void update(boolean[] keys) {
        if (keys[RIGHT]) {
            body.applyForce(new Vec2(SPEED, 0), body.getWorldCenter());
        }
        if (keys[LEFT]) {
            body.applyForce(new Vec2(-SPEED, 0), body.getWorldCenter());
        }
        if (keys[UP]) {
            body.applyForce(new Vec2(0, SPEED), body.getWorldCenter());
        }
        if (keys[DOWN]) {
            body.applyForce(new Vec2(0, -SPEED), body.getWorldCenter());
        }

        if (body.getLinearVelocity().x > MAX_VEL_X) {
            body.setLinearVelocity(new Vec2(MAX_VEL_X, body.getLinearVelocity().y));
        }
        if (body.getLinearVelocity().x < -MAX_VEL_X) {
            body.setLinearVelocity(new Vec2(-MAX_VEL_X, body.getLinearVelocity().y));
        }
        if (body.getLinearVelocity().y > MAX_VEL_Y) {
            body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, MAX_VEL_Y));
        }
        if (body.getLinearVelocity().y < -MAX_VEL_Y) {
            body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, -MAX_VEL_Y));
        }
    }
}
