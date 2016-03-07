package ch.leozulfiu.dynamicTree;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tiled.core.MapObject;

import java.awt.*;
import java.awt.geom.PathIterator;
import java.util.ArrayList;

/**
 * @author Leo Zulfiu
 * @since 07.08.2014
 */
public class Tree extends Box2dObject {
    private static final double DEGTORAD = 3.14 / 180;
    private static final int MULTIPLIER = 150000;

    ArrayList<TreePart> parts;
    ArrayList<RevoluteJoint> joints;

    public Tree(PApplet parent, Box2DProcessing box2d, MapObject object) {
        super(parent, box2d);
        parts = new ArrayList<>();
        joints = new ArrayList<>();

        ArrayList<Vec2> points = extractPoints(object);

        createTreeParts(points);
        createRevoluteJoins(points);
    }

    private void createRevoluteJoins(ArrayList<Vec2> points) {
        for (int i = 0; i < parts.size() - 1; i++) {
            TreePart upper = parts.get(i + 1);
            TreePart lower = parts.get(i);

            RevoluteJointDef rdef = new RevoluteJointDef();

            rdef.initialize(lower.body, upper.body, box2d.coordPixelsToWorld(points.get(i)));
            rdef.enableLimit = true;
            rdef.lowerAngle = (float) (-20 * DEGTORAD);
            rdef.upperAngle = (float) (20 * DEGTORAD);
            rdef.enableMotor = false;
            RevoluteJoint joint = (RevoluteJoint) box2d.world.createJoint(rdef);
            joints.add(joint);
        }
    }

    private void createTreeParts(ArrayList<Vec2> points) {
        Object[] objects = points.toArray();
        for (int i = 0; i < objects.length; i++) {
            Vec2 first = (Vec2) objects[i];
            if (i + 1 < objects.length) {
                Vec2 second = (Vec2) objects[i + 1];
                Vec2 sub = second.sub(first);
                double v = Math.atan2(sub.y, sub.x) * 180 / Math.PI;
                v -= 90; //correctionhack...dont know why

                Vec2 middle = new Vec2((first.x + second.x) / 2, (first.y + second.y) / 2);

                if (i == 0) {
                    parts.add(new TreePart(this.parent, box2d, middle.x, middle.y, 10, sub.length(), (float) v, true));
                } else {
                    parts.add(new TreePart(this.parent, box2d, middle.x, middle.y, 10, sub.length(), (float) v, false));
                }
            }
        }
    }

    @Override
    public void createBox2dObject() {

    }

    public void update() {
        for (RevoluteJoint joint : joints) {
            double joint_angle = joint.getJointAngle() * (180 / 3.14);

            if (Math.abs(joint_angle) > 0.2) {
                joint.setMaxMotorTorque((float) (MULTIPLIER * Math.abs(joint_angle / 30)));
                joint.setMotorSpeed((float) (-joint_angle / 30));
                joint.enableMotor(true);
            }
        }
    }

    public void draw() {
        for (TreePart part : parts) {
            part.draw();
        }
    }

    private ArrayList<Vec2> extractPoints(MapObject object) {
        Shape shape = object.getShape();
        PathIterator pathIterator = shape.getPathIterator(null);
        ArrayList<Vec2> points = new ArrayList<>();
        while (!pathIterator.isDone()) {
            float[] coords = new float[12];
            pathIterator.currentSegment(coords);
            float xPoint = coords[0];
            float yPoint = coords[1];
            if (xPoint != 0 && yPoint != 0) {
                points.add(new Vec2(xPoint, yPoint));
            }
            pathIterator.next();
        }
        return points;
    }

    public ArrayList<TreePart> getParts() {
        return parts;
    }
}
