package nl.tdegroot.games.bote.server;

import com.esotericsoftware.kryonet.Server;
import nl.tdegroot.games.bote.common.Network;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.pixxel.util.Log;

import java.io.*;

public class GameServer {

    private Server server;

    private String name;

    public GameServer() {
        this("GameServer");
    }

    public GameServer(String name) {
        this.name = name;

        server = new Server();
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
        server.addListener(new ServerListener());
    }


}
