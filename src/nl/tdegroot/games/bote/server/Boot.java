package nl.tdegroot.games.bote.server;

import nl.tdegroot.games.bote.common.input.CommandLineInput;

public class Boot {

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();

        //Pretty good start.
        CommandLineInput input = new CommandLineInput();
        input.start();
    }

}
