package nl.tdegroot.games.bote.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class GameServer implements Runnable {

    private DatagramSocket socket;
    private Thread run, update, receive;
    private boolean running;

    private String name;
    private int port;

    public GameServer(int port) {
        this("GameServer", port);
    }

    public GameServer(String name, int port) {
        this.name = name;
        this.port = port;

        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        run = new Thread(this, "GameServer");
        run.start();
    }

    public void run() {
        running = true;
        System.out.println("Server started to run on port " + port + "!");
        update();
        receive();
    }

    private void update() {
        update = new Thread("Update") {
            public void run() {
                while (running) {

                }
            }
        };
        update.start();
    }

    private void receive() {
        receive = new Thread("Receive") {
            public void run() {
                while (running) {
                    byte[] data = new byte[512];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    process(packet);
                }
            }
        };
        receive.start();
    }

    private void process(DatagramPacket packet) {

    }


}
