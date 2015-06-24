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

    public EntityState state;

    public void onServer(Connection connection, ServerListener serverListener) {

    }

    public void onClient(Connection connection, ClientListener clientListener) {
//        if (clientListener.getWorld() == null) return;

        Entity entity = clientListener.getWorld().getEntity(state.id);

        if (entity == null) {
            clientListener.getWorld().createEntityFromState(state);
        } else {
            entity.setPosition(state.position);
        }
    }

}
