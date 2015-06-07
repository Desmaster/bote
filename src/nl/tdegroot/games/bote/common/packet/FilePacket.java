package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;

public class FilePacket implements Packet {
    @Override
    public void onServer(Connection connection, ServerListener serverListener) {
    }

    @Override
    public void onClient(Connection connection, ClientListener clientListener) {

    }
}
