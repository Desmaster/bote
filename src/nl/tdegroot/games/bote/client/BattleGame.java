package nl.tdegroot.games.bote.client;

import com.esotericsoftware.kryonet.Client;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.Network;
import nl.tdegroot.games.bote.common.level.Level;
import nl.tdegroot.games.bote.common.packet.LoginPacket;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.bote.common.packet.TiledMapPacket;
import nl.tdegroot.games.pixxel.GameException;
import nl.tdegroot.games.pixxel.PixxelGame;
import nl.tdegroot.games.pixxel.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BattleGame extends PixxelGame {

    private Client client;
    private ClientListener clientListener;

    private InetAddress address;
    private final String host;

    private Level level;

    private boolean loggedIn = false;

    public BattleGame(String title, int width, int height, int scale, String host) {
        super(title, width, height, scale);

        this.host = host;
    }

    public void init() throws GameException {
        Log.info("Trying to connect to server on: " + host + ":" + Network.TCP_PORT + "/" + Network.UDP_PORT);
        try {
            client = new Client();

            level = new Level();

            clientListener = new ClientListener(this);
            clientListener.setLevel(level);

            address = InetAddress.getByName(host);
            client.start();

            Network.register(client);

            client.connect(5000, address, Network.TCP_PORT, Network.UDP_PORT);
            client.addListener(clientListener);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginPacket login = new LoginPacket();
        login.name = "Simone";

        sendTCP(login);
    }

    public void tick(int delta) {
        if (loggedIn) {
            if (level.getMap() == null) {
                TiledMapPacket tiledMapPacket = new TiledMapPacket();
                sendTCP(tiledMapPacket);
            }
        }
    }

    public void render() {
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void sendTCP(Packet packet) {
        client.sendTCP(packet);
    }

    public void sendUDP(Packet packet) {
        client.sendUDP(packet);
    }

    public static void main(String[] args) {
        BattleGame game = new BattleGame("Battle of Tiled Environments", 1280, 720, 4, "localhost");
        game.setLogFps(false);
        game.start();
    }
}
