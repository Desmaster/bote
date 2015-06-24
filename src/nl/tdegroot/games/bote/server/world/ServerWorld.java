package nl.tdegroot.games.bote.server.world;

import nl.tdegroot.games.bote.common.entity.EntityPacket;
import nl.tdegroot.games.bote.common.entity.EntityState;
import nl.tdegroot.games.bote.common.event.EventDispatcher;
import nl.tdegroot.games.bote.common.event.EventHandler;
import nl.tdegroot.games.bote.common.event.EventListener;
import nl.tdegroot.games.bote.common.input.event.CommandEvent;
import nl.tdegroot.games.bote.server.GameServer;
import nl.tdegroot.games.bote.server.entity.ServerEntity;
import nl.tdegroot.games.pixxel.util.Random;

import java.util.ArrayList;

public class ServerWorld implements EventHandler {

    private ArrayList<ServerEntity> entities = new ArrayList<ServerEntity>();
    private final GameServer server;

    public ServerWorld(GameServer server) {
        this.server = server;

        System.out.println("Server world!");
        EventDispatcher.register(this);
    }

    @EventListener("command:players")
    public void playerEvent(CommandEvent command) {
        System.out.println(entities.size());
    }

    public ArrayList<ServerEntity> getEntities() {
        System.out.println(entities.size());
        return entities;
    }

    public void update() {
        for (ServerEntity entity : entities) {
            EntityState state = new EntityState();
            state.id = entity.getId();
            state.position = entity.getPosition();
            state.entityClass = entity.getEntityClass();

            EntityPacket packet = new EntityPacket();
            packet.state = state;

            server.getServer().sendToAllExceptUDP(entity.getId(), packet);
        }
    }

    public ServerEntity getEntity(int id) {
        for(ServerEntity entity : entities) {
            if (entity.getId() == id)
                return entity;
        }
        return null;
    }

    public void registerEntity(int id, String name, Class entityClass) {
        ServerEntity entity = new ServerEntity(id, name);
        entity.setEntityClass(entityClass);
        entities.add(entity);
    }
}
