package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.client.entity.Entity;
import nl.tdegroot.games.bote.client.entity.OtherPlayer;
import nl.tdegroot.games.bote.client.states.GameState;
import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.bote.common.entity.EntityState;
import nl.tdegroot.games.bote.server.world.ServerWorld;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WorldPacket implements Packet {
    public ConcurrentHashMap<String, EntityState> entities = new ConcurrentHashMap<String, EntityState>();

    @Override
    public void onServer(Connection connection, ServerListener serverListener) {
        ServerWorld world = serverListener.getWorld();
        ConcurrentHashMap<String, EntityState> serverEntities = world.getEntities();

        //Compare entities to clients
        for (Map.Entry<String, EntityState> uuidEntityEntry : entities.entrySet()) {
            String id = uuidEntityEntry.getKey();
            EntityState state = uuidEntityEntry.getValue();

            if (state.position != serverEntities.get(id).position) {
                System.out.println("Adding entity!");
                serverEntities.put(id, state);
            }
        }

        entities = serverEntities;

        connection.sendTCP(this);
    }

    @Override
    public void onClient(Connection connection, ClientListener clientListener) {
        World world = clientListener.getWorld();

        //Sync all entities except player
        for (Map.Entry<String, EntityState> uuidEntityEntry : entities.entrySet()) {
            EntityState state = uuidEntityEntry.getValue();
            Entity entity = world.getEntity(uuidEntityEntry.getKey());

            if (entity == null) {
                entity = new OtherPlayer(world, state.position.getX(), state.position.getY());
                world.addEntity(entity);

                EntityState entityState = new EntityState();
                entityState.position = entity.pos;

                entities.put(entity.id, entityState);
            } else {
                entity.pos = state.position;
            }
        }

        syncClientToServer(clientListener.getState());

        connection.sendTCP(this);
    }

    //TODO: Change GameState
    public void syncClientToServer(GameState client) {
        //Update player state to server
        Entity entity = client.world.getEntity(client.playerid);

        EntityState state = new EntityState();
        state.position = entity.pos;

        if (entities.get(entity.id) == null || !entities.get(entity.id).equals(state)) {
            entities.put(entity.id, state);
        }
    }
}
