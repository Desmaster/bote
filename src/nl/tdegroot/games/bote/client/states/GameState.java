package nl.tdegroot.games.bote.client.states;

import com.esotericsoftware.kryonet.Client;
import nl.tdegroot.games.bote.client.entity.Player;
import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.Network;
import nl.tdegroot.games.bote.common.entity.PlayerPacket;
import nl.tdegroot.games.bote.common.packet.LoginPacket;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.pixxel.Display;
import nl.tdegroot.games.pixxel.GameException;
import nl.tdegroot.games.pixxel.PixxelGame;
import nl.tdegroot.games.pixxel.gfx.Camera;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.state.State;
import nl.tdegroot.games.pixxel.util.Log;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class GameState extends State {
    private Client client;

    private final String host;

    private boolean loggedIn = false;

    private Display display;
    private Camera cam;

    public World world;

    Player player;

    public GameState(PixxelGame game, String host) {
        super(game);
        this.host = host;
    }

    public void init(Display display) throws GameException {
        this.display = display;

        Log.info("Trying to connect to server on: " + host + ":" + Network.TCP_PORT + "/" + Network.UDP_PORT);
        try {
            client = new Client(8192, 8192);


            ClientListener clientListener = new ClientListener(this);

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

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/pixels.ttf")).deriveFont(24f);
            display.getScreen().setDefaultFont(font);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cam = new Camera(0, 0, display.getScaledWidth(), display.getScaledHeight());

        world = new World();
        world.init();
        
        player = new Player(world, 100, 100);

        //TODO: Player now could be registered in EntityPacket aswell, still needs to be added.
        PlayerPacket playerPacket = new PlayerPacket();
        playerPacket.player = player.getState();
        sendTCP(playerPacket);
    }

    public void tick() {
        world.tick();

        display.setTitle("Camera x: " + cam.getX() + " Camera y: " + cam.getY());
    }

    public void render(Screen screen) {

        screen.setProjectionMatrix(cam.getProjectionMatrix());
        screen.translate(cam.getX(), cam.getY());

        world.render(screen);

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