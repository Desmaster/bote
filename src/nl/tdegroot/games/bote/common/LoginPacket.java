package nl.tdegroot.games.bote.common;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.pixxel.util.Log;

public class LoginPacket implements Packet {

    public String name;

    public void onServer(Connection connection) {
        Log.info("Server received: " + name);
    }

    public void onClient(Connection connection) {

    }

}
