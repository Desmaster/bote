package nl.tdegroot.games.bote.client.states;

import com.esotericsoftware.kryonet.Client;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.Network;
import nl.tdegroot.games.bote.common.level.Level;
import nl.tdegroot.games.bote.common.packet.LoginPacket;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.bote.common.packet.TiledMapPacket;
import nl.tdegroot.games.pixxel.Display;
import nl.tdegroot.games.pixxel.GameException;
import nl.tdegroot.games.pixxel.Keyboard;
import nl.tdegroot.games.pixxel.PixxelGame;
import nl.tdegroot.games.pixxel.gfx.Camera;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.state.State;
import nl.tdegroot.games.pixxel.util.Log;

import java.io.IOException;
import java.net.InetAddress;

public class GameState extends State {
    private Client client;

    private final String host;

    private Level level;

    private boolean loggedIn = false;

    Camera cam;

    public GameState(PixxelGame game, String host) {
        super(game);

        this.host = host;
    }

    public void init(Display display) throws GameException {
        Log.info("Trying to connect to server on: " + host + ":" + Network.TCP_PORT + "/" + Network.UDP_PORT);
        try {
            client = new Client(8192, 8192);

            level = new Level();

            ClientListener clientListener = new ClientListener(this);
            clientListener.setLevel(level);

            InetAddress address = InetAddress.getByName(host);
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

        cam = new Camera(0, 0, display.getScaledWidth(), display.getScaledHeight());
    }

    public void tick(Display display, int delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            cam.setY(cam.getY() - 5);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            cam.setX(cam.getX() + 5);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            cam.setY(cam.getY() + 5);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            cam.setX(cam.getX() - 5);
        }

        display.setTitle("Camera x: " + cam.getX() + " Camera y: " + cam.getY());

        if (loggedIn) {
            if (level.getMap() == null && game.getTime() % 60 == 0) {
                sendTCP(new TiledMapPacket());
            }
        }
    }

    public void render(Display display, Screen screen) {
        screen.setProjectionMatrix(cam.getProjectionMatrix());
        screen.translate(cam.getX(), cam.getY());

        if (level.getMap() != null)
            level.getMap().render(0, 0, screen);

        screen.translate(-cam.getX(), -cam.getY());


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
}