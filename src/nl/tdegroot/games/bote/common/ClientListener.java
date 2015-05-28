package nl.tdegroot.games.bote.common;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.pixxel.util.Log;

public class ClientListener extends Listener {

    public void connected(Connection connection) {
        Log.info("Connected to " + connection.toString());
    }

    public void disconnected(Connection connection) {
        Log.info("Disconnected from " + connection.toString());
    }

    public void received(Connection connection, Object obj) {
        if (!(obj instanceof Packet)) return;
        ((Packet) obj).onClient(connection);
    }
}
