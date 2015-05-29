package nl.tdegroot.games.bote.client;

import nl.tdegroot.games.bote.common.packet.LoginPacket;
import nl.tdegroot.games.pixxel.GameException;
import nl.tdegroot.games.pixxel.PixxelGame;

public class BattleGame extends PixxelGame {

    private boolean loggedIn = false;

    public BattleGame(String title, int width, int height, int scale) {
        super(title, width, height, scale);
    }

    public void init() throws GameException {
        DeliveryService deliveryService = new DeliveryService("localhost");
        deliveryService.start();

        LoginPacket login = new LoginPacket();
        login.name = "Simone";

        deliveryService.sendTCP(login);
    }

    public void tick(int delta) {

    }

    public void render() {

    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public static void main(String[] args) {
        BattleGame game = new BattleGame("Battle of Tiled Environments", 1280, 720, 4);
        game.setLogFps(false);
        game.start();
    }
}
