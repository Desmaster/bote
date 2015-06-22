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
    ArrayList<EntityState> entityStates = new ArrayList<EntityState>();

    @Override
    public void onServer(Connection connection, ServerListener serverListener) {
        ServerWorld world = serverListener.getWorld();
        this.entityStates = world.getEntityStates();

        connection.sendTCP(this);
    }

    @Override
    public void onClient(Connection connection, ClientListener clientListener) {
        World world = clientListener.getWorld();
        for(Entity entity : world.getEntities()) {
            for(EntityState entityState : entityStates) {
                if (entityState.getId() == entity.getId()) {
                    entity.setState(entityState);
                    break;
                }
            }
        }

        connection.sendTCP(this);
    }
}
