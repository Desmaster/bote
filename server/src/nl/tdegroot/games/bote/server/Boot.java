package nl.tdegroot.games.bote.server;

public class Boot {

    public static void main(String[] args) {
        GameServer server = new GameServer(65535);
        server.start();
    }

}
