package nl.tdegroot.games.bote.common;

import com.esotericsoftware.kryonet.Client;
import nl.tdegroot.games.pixxel.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DeliveryService {

    private Client client;

    private InetAddress address;
    private final String host;

    public DeliveryService(String host) {
        this.host = host;

        client = new Client();
    }

    public void start() {
        Log.info("Trying to connect to server on: " + host + ":" + Network.TCP_PORT + "/" + Network.UDP_PORT);
        try {
            address = InetAddress.getByName(host);

            client.start();
            Network.register(client);
            client.connect(5000, address, Network.TCP_PORT, Network.UDP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Packet packet) {
        client.sendTCP(packet);
        Log.info("Sent packet");
    }
}
