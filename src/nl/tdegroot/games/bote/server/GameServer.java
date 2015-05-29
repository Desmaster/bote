package nl.tdegroot.games.bote.server;

import com.esotericsoftware.kryonet.Server;
import nl.tdegroot.games.bote.common.Network;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.bote.common.level.Level;
import nl.tdegroot.games.pixxel.util.Log;

import java.io.IOException;
import java.util.HashMap;

public class GameServer {

    private Server server;
    private ServerListener serverListener;
    private HashMap<Integer, String> clients = new HashMap<Integer, String>();
    private Level level;

    private String name;

    public GameServer() {
        this("GameServer");
    }

    public GameServer(String name) {
        this.name = name;

        server = new Server();
        serverListener = new ServerListener(this);
        level = new Level();

        serverListener.setLevel(level);
    }

    public void start() {
        Log.info("Trying to start server on port: " + Network.TCP_PORT + "/" + Network.UDP_PORT);
        try {
            server.start();
            Network.register(server);
            server.bind(Network.TCP_PORT, Network.UDP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.addListener(serverListener);
    }

    public void addClient(int id, String name) {
        clients.put(id, name);
    }

}
