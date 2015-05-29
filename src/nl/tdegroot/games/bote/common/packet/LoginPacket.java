package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.client.BattleGame;
import nl.tdegroot.games.bote.server.GameServer;
import nl.tdegroot.games.pixxel.util.Log;

public class LoginPacket implements Packet {

    public String name;
    private boolean loggedIn = false;

    public void onServer(Connection connection, GameServer server) {
        int id = connection.getID();
        server.addClient(id, name);
        Log.info("Server received: " + name);
        loggedIn = true;
    }

    public void onClient(Connection connection, BattleGame game) {
        game.setLoggedIn(loggedIn);
    }

}
