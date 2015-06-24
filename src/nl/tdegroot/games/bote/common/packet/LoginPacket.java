package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.client.states.GameState;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.bote.server.GameServer;
import nl.tdegroot.games.pixxel.util.Log;

public class LoginPacket implements Packet {

    public String name;
    private boolean loggedIn = false;

    public void onServer(Connection connection, ServerListener serverListener) {
        GameServer server = serverListener.getGameServer();

        int id = connection.getID();
        server.registerClient(id, name);
        Log.info(name + " is joining the server");
        loggedIn = true;
        server.broadcast(name + " is joining the server");

        connection.sendTCP(this);
    }

    public void onClient(Connection connection, ClientListener clientListener) {
        GameState game = clientListener.getState();
        game.getPlayer().setId(connection.getID());
        if (loggedIn) {
            game.setLoggedIn(true);
        }
    }

}
