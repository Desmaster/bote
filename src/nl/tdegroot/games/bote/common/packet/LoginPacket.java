package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.client.BattleGame;
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
        server.addClient(id, name);
        Log.info(name + " is joining the server");
        loggedIn = true;

        connection.sendTCP(this);
    }

    public void onClient(Connection connection, ClientListener clientListener) {
        BattleGame game = clientListener.getGame();
        Log.info("" + game);
        if (loggedIn) {
            game.setLoggedIn(true);
        }
    }

}
