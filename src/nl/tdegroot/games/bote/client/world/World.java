package nl.tdegroot.games.bote.client.world;

import nl.tdegroot.games.bote.client.entity.Entity;
import nl.tdegroot.games.pixxel.Display;
import nl.tdegroot.games.pixxel.GameException;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.map.tiled.TiledMap;
import nl.tdegroot.games.pixxel.util.Random;

import java.util.*;

public class World {
    public HashMap<String, Entity> entities = new HashMap<String, Entity>();

    TiledMap map;

    public World() {

    }

    public void init() throws GameException {
        map = new TiledMap("/res/tiledmap.tmx");
    }

    public void tick(int delta) {
        for (Map.Entry<String, Entity> uuidEntityEntry : entities.entrySet()) {
            uuidEntityEntry.getValue().tick(delta);
        }
    }

    public void render(Screen screen) {
        map.render(0, 0, screen);

        for (Map.Entry<String, Entity> uuidEntityEntry : entities.entrySet()) {
            uuidEntityEntry.getValue().render(screen);
        }
    }

    public HashMap<String, Entity> getEntities() {
        return entities;
    }

    public Entity getEntity(String id) {
        return entities.get(id);
    }

    public void addEntity(Entity entity) {
        entities.put(entity.id, entity);

        entity.init();
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.id);
    }

    public void removeEntity(String id) {
        entities.remove(id);
    }

}
