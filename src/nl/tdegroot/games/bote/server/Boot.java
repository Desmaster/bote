package nl.tdegroot.games.bote.server;

import nl.tdegroot.games.bote.common.Network;

public class Boot {

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
    }

}
