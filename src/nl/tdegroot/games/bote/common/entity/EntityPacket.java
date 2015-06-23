package nl.tdegroot.games.bote.common.entity;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.client.entity.Entity;
import nl.tdegroot.games.bote.client.entity.OtherPlayer;
import nl.tdegroot.games.bote.client.entity.Player;
import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.bote.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.Iterator;

public class EntityPacket implements Packet {
    ArrayList<EntityState> entities = new ArrayList<EntityState>();

    public void onServer(Connection connection, ServerListener serverListener) {
        ServerWorld serverWorld = serverListener.getWorld();
        ArrayList<EntityState> serverEntities = serverWorld.getEntityStates();

        int playerID = 0;

        //TODO: GO GET A DRINK ONCE THIS WORKS
        //TODO: MAKE IT A DOUBLE
        //On recieve of the entityPacket, the packet only contains one Entity. The client's Player entity.
        if (entities.size() == 1) {
            EntityState entity = entities.get(0);

            if (entity.entityClass == Player.class) {
                //Entity is player confirmed.
                if (entity.getId() == 0) {
                    //Player hasn't been registered yet.

                    entity = serverWorld.registerEntity(entity);
                } else {
                    for (EntityState serverEntity : serverEntities) {
                        if (serverEntity.getId() == entity.getId()) {
                            //Update Player state
                            serverEntities.set(serverEntities.indexOf(serverEntity), entity);
                        }
                    }
                }

                playerID = entity.getId();
            }
        }

        entities = new ArrayList<EntityState>(serverEntities);
        //Make sure the server doesn't send back the client's own player
        for(EntityState entity : new ArrayList<EntityState>(entities)) {
            if (entity.getId() == playerID) {
                entities.remove(entity);
            }
        }

        connection.sendTCP(this);
    }

    public void onClient(Connection connection, ClientListener clientListener) {
        World world = clientListener.getWorld();

        for(EntityState entityState : entities) {
            Entity entity = world.getEntity(entityState.getId());

            if (entity != null) {
                entity.setState(entityState);
            } else {
                world.createEntityFromState(entityState);
            }
        }

        entities.clear();
        entities.add(world.getPlayer().getState());

        connection.sendTCP(this);
    }
}
