package nl.tdegroot.games.bote.server;

import com.esotericsoftware.kryonet.Server;
import nl.tdegroot.games.bote.client.entity.Player;
import nl.tdegroot.games.bote.common.Network;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.bote.common.entity.EntityPacket;
import nl.tdegroot.games.bote.common.level.Level;
import nl.tdegroot.games.bote.common.packet.BroadcastPacket;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.bote.server.world.ServerWorld;
import nl.tdegroot.games.pixxel.util.Log;

import java.io.IOException;
import java.util.HashMap;

public class GameServer implements Runnable {

    private Server server;
    private ServerListener serverListener;
    private HashMap<Integer, String> clients = new HashMap<Integer, String>();

    private Thread thread;
    private boolean running = false;

    public ServerWorld serverWorld;

    private Level level;

    private String name;
    private ServerWorld world;

    public GameServer() {
        this("GameServer");
    }

    public GameServer(String name) {
        this.name = name;

        server = new Server(8192, 8192);
        serverListener = new ServerListener(this);

        serverWorld = new ServerWorld(this);
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
        running = true;
        thread = new Thread(this, "Server Loop");
        thread.start();
    }

    public void run() {
        while (running) {
            serverWorld.update();
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void registerClient(int id, String name) {
        clients.put(id, name);
        serverWorld.registerEntity(id, name, Player.class);
    }

    public void broadcast(String message) {
        BroadcastPacket packet = new BroadcastPacket();
        packet.message = message;

        server.sendToAllTCP(packet);
    }

    public Server getServer() {
        return server;
    }

    public ServerWorld getWorld() {
        return world;
    }
}
