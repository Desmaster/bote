package nl.tdegroot.games.bote.common.entity;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.bote.server.entity.ServerEntity;
import nl.tdegroot.games.bote.server.world.ServerWorld;

public class PlayerPacket implements Packet {
    public EntityState state;

    public void onServer(Connection connection, ServerListener serverListener) {
//        serverListener.getWorld().registerEntity(state);
        ServerWorld world = serverListener.getWorld();
        ServerEntity entity = world.getEntity(connection.getID());
        entity.setPosition(state.position);
//        connection.sendTCP(this);
    }

    public void onClient(Connection connection, ClientListener clientListener) {
        clientListener.getWorld().getPlayer().setState(state);

        //start entity sync between client and server
//        connection.sendTCP(new EntityPacket());
    }
}
