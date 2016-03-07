package ch.leozulfiu.dynamicTree;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tiled.core.MapObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Leo Zulfiu
 * @since 27.07.2014
 */
public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[] {"ch.leozulfiu.dynamicTree.Main"});
    }

    static boolean[] keys = new boolean[255];

    Box2DProcessing box2d;
    Camera camera;
    MouseSpring spring;
    LevelLoader loader;

    ArrayList<Tree> trees = new ArrayList<>();
    ArrayList<StaticObject> boundaries = new ArrayList<>();
    ArrayList<Coin> coins = new ArrayList<>();
    HashMap<Teleport, Teleport> teleportions = new HashMap<>();

    Player player;

    public void setup() {
        frame.setTitle("Dynamic Trees with box2d");
        size(Camera.VIEWPORT_SIZE_X, Camera.VIEWPORT_SIZE_Y);
        box2d = new Box2DProcessing(this);
        box2d.createWorld(new Vec2(0,0));
        box2d.listenForCollisions();

        loader = new LevelLoader(this);
        spring = new MouseSpring(this, box2d);
    }

    public void draw() {
        background(86, 186, 172);

        camera.update();

        box2d.step();

        spring.update(mouseX,mouseY);
        spring.draw();

        for (Coin coin : coins) {
            coin.update();
            coin.draw();
        }

        for (Tree plant : trees) {
            plant.update();
            plant.draw();
        }

        for (StaticObject boundary : boundaries) {
            boundary.draw();
        }

        player.update(keys);
        player.draw();
        fill(255);
        text("Use arrows to steer", 20, 12);
    }


    public void keyPressed() {
        keys[keyCode] = true;
    }

    public void keyReleased() {
        keys[keyCode] = false;
    }

    public void mouseReleased() {
        spring.destroy();
    }

    public void mousePressed() {
        for (Tree tree : trees) {
            tree.getParts().stream().filter(object -> object.contains(mouseX, mouseY)).forEach(object -> {
                spring.bind(mouseX, mouseY, object);
            });
        }
    }

    public void createPlayer(MapObject object) {
        player = new Player(this, box2d, object.getX(), object.getY(), object.getWidth(), object.getHeight());
    }

    public void createStaticObject(MapObject object) {
        boundaries.add(new StaticObject(this, box2d, object.getX(), object.getY(), object.getWidth(), object.getHeight()));
    }

    public void createTree(MapObject object) {
        trees.add(new Tree(this, box2d, object));
    }

    public void createCamera(int width, int height) {
        camera = new Camera(this, box2d, width, height);
    }

    public void createCoin(MapObject object) {
        coins.add(new Coin(this, box2d, object.getX(), object.getY(), 20));
    }

    public void createTeleport(MapObject object) {
        //teleportions.add(new Teleport(this, box2d, object.getX(), object.getY(), object.getWidth(), object.getHeight()));
    }

    public void beginContact(Contact cp) {
        Fixture f1 = cp.getFixtureA();
        Fixture f2 = cp.getFixtureB();

        Body b1 = f1.getBody();
        Body b2 = f2.getBody();

        Object o1 = b1.getUserData();
        Object o2 = b2.getUserData();

        if (o1 != null && o2 != null) {
            if (o1.getClass() == Player.class && o2.getClass() == Coin.class) {
                Coin coin = (Coin) o2;
                coin.destroy();
            } else if (o1.getClass() == Coin.class && o2.getClass() == Player.class) {
                Coin coin = (Coin) o1;
                coin.destroy();
            }
        }
    }

    public void endContact(Contact cp) {
    }
}
