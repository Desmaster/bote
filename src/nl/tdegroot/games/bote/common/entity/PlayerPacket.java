package nl.tdegroot.games.bote.common.entity;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.bote.server.world.ServerWorld;

public class PlayerPacket implements Packet {
    public EntityState player;

    @Override
    public void onServer(Connection connection, ServerListener serverListener) {
        serverListener.getWorld().registerEntity(player);
        connection.sendTCP(this);
    }

    @Override
    public void onClient(Connection connection, ClientListener clientListener) {
        clientListener.getWorld().getPlayer().setState(player);
        System.out.println(clientListener.getWorld().getPlayer().getId());

        //start entity sync between client and server
        connection.sendTCP(new EntityPacket());
    }
}
