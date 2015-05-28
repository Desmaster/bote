package nl.tdegroot.games.bote.client;

import com.esotericsoftware.kryonet.Client;
import nl.tdegroot.games.bote.common.Network;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.pixxel.util.Log;

import java.io.IOException;
import java.net.InetAddress;

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

    public void sendTCP(Packet packet) {
        client.sendTCP(packet);
    }

    public void sendUDP(Packet packet) {
        client.sendUDP(packet);
    }

}
