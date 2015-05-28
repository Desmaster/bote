package nl.tdegroot.games.bote.client.network;

import nl.tdegroot.games.pixxel.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class DeliveryService implements Runnable {

    private Thread run, listen, send;
    private DatagramSocket socket;
    private boolean running;

    private InetAddress address;
    private final String host;
    private int port;

    public DeliveryService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName(host);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void start() {
        running = true;
        run = new Thread(this, "DeliveryService");
        run.start();
    }

    public void run() {
        Log.info("Started listening to " + host + ":" + port);
        listen();
    }

    private void listen() {
        listen = new Thread("Listen") {
            public void run() {
                while (running) {
                    byte[] data = new byte[512];
                    DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayInputStream bis = new ByteArrayInputStream(data);
                }
            }
        };
        listen.start();
    }

    private void send() {
        send = new Thread("Listen") {
            public void run() {

            }
        };
    }
}
