package nl.tdegroot.games.bote.common.entity;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.client.entity.Entity;
import nl.tdegroot.games.bote.client.entity.Player;
import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.bote.server.world.ServerWorld;

import java.util.ArrayList;

public class EntityPacket implements Packet {
    ArrayList<EntityState> entities = new ArrayList<EntityState>();

    public void onServer(Connection connection, ServerListener serverListener) {
        ServerWorld serverWorld = serverListener.getWorld();
        ArrayList<EntityState> serverEntities = serverWorld.getEntityStates();


        //Should servers have entities with a Player class?
        //TODO: Security.
        for(EntityState entity : entities) {
            if (entity.entityClass == Player.class) {
                if (serverWorld.getEntity(entity.getId()) == null) {
                    entities.set(entities.indexOf(entity), serverWorld.registerEntity(entity));
                } else {
                    serverEntities.set(serverEntities.indexOf(serverWorld.getEntity(entity.getId())), entity);
                }
            }
        }

        entities = serverEntities;

        connection.sendTCP(this);
    }

    public void onClient(Connection connection, ClientListener clientListener) {
        World world = clientListener.getWorld();

        for(EntityState entityState : entities) {
            Entity entity = world.getEntity(entityState.getId());

            if (entity != null) {
                entity.setState(entityState);
            } else {
//                world.createEntityFromState(entityState);
            }
        }

        entities.clear();
        entities.add(world.getPlayer().getState());

        connection.sendTCP(this);
    }
}
