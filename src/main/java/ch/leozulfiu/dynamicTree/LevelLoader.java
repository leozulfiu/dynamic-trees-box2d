package ch.leozulfiu.dynamicTree;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.MapObject;
import tiled.core.ObjectGroup;
import tiled.io.TMXMapReader;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Leo Zulfiu
 * @since 12.09.2014
 */
public class LevelLoader {
    Map map;
    Main parent;

    public LevelLoader(Main parent) {
        this.parent = parent;
        TMXMapReader tmxMapReader = new TMXMapReader();
        try {
            map = tmxMapReader.readMap(getClass().getResourceAsStream("/maps/Start.tmx"));
            this.parent.createCamera(map.getWidth(), map.getHeight());

            createLayerObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createLayerObjects() {
        Vector<MapLayer> layers = map.getLayers();
        for (MapLayer layer : layers) {
            Iterator<MapObject> objectsIterator = ((ObjectGroup) layer).getObjects();
            while (objectsIterator.hasNext()) {
                MapObject object = objectsIterator.next();
                if (layer.getName().equals("player")) {
                    this.parent.createPlayer(object);
                }
                if (layer.getName().equals("static")) {
                    this.parent.createStaticObject(object);
                }
                if (layer.getName().equals("trees")) {
                    this.parent.createTree(object);
                }
                if (layer.getName().equals("coins")) {
                    this.parent.createCoin(object);
                }
                if (layer.getName().equals("teleportations")) {
                    this.parent.createTeleport(object);
                }
            }
        }
    }
}
