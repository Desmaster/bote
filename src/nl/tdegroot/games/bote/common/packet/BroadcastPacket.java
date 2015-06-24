package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.pixxel.util.Log;

public class BroadcastPacket implements Packet {

    public String message;

    public void onServer(Connection connection, ServerListener serverListener) {

    }

    public void onClient(Connection connection, ClientListener clientListener) {
        Log.info(message);
    }
}
