package nl.tdegroot.games.bote.client.world;

import nl.tdegroot.games.bote.client.entity.Entity;
import nl.tdegroot.games.bote.client.entity.OtherPlayer;
import nl.tdegroot.games.bote.client.entity.Player;
import nl.tdegroot.games.bote.common.entity.EntityState;
import nl.tdegroot.games.pixxel.Display;
import nl.tdegroot.games.pixxel.GameException;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.map.tiled.TiledMap;
import nl.tdegroot.games.pixxel.util.Random;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class World {
    public ArrayList<Entity> entities = new ArrayList<Entity>();

    TiledMap map;
    Player player;

    public World() {

    }

    public void init() throws GameException {
        map = new TiledMap("/res/tiledmap.tmx");
    }

    public void tick() {
        for(Entity entity : entities) {
            entity.tick();
        }
    }

    public void render(Screen screen) {
        map.render(0, 0, screen);

        for(Entity entity : entities) {
            entity.render(screen);
        }

        screen.drawString(screen.getWidth() - 90, 5, "Entities: " + entities.size());
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Entity getEntity(int id) {
        for(Entity entity : entities) {
            if (entity.getId() == id) return entity;
        }

        return null;
    }

    public void addEntity(Entity entity) {
        entity.setWorld(this);
        entities.add(entity);


        if (entity.getClass() == Player.class) {
            player = (Player) entity;
        }

        entity.init();
    }

    public void createEntityFromState(EntityState entityState) {
        try {
            Class entityClass = entityState.getEntityClass();

            if (entityClass == Player.class && entityState.getId() != player.getId()) {
                entityClass = OtherPlayer.class;
            }

            Entity entity = (Entity) entityClass.getConstructor(World.class).newInstance(this);
            entity.setState(entityState);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public void removeEntity(int id) {
        removeEntity(getEntity(id));
    }

    public Player getPlayer() {
        return player;
    }
}
