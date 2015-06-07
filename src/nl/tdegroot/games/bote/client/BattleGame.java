package nl.tdegroot.games.bote.client;

import nl.tdegroot.games.bote.common.packet.LoginPacket;
import nl.tdegroot.games.pixxel.GameException;
import nl.tdegroot.games.pixxel.Keyboard;
import nl.tdegroot.games.pixxel.PixxelGame;
import nl.tdegroot.games.pixxel.gfx.Camera;
import nl.tdegroot.games.pixxel.gfx.Color;
import nl.tdegroot.games.pixxel.gfx.Screen;

public class BattleGame extends PixxelGame {

    private boolean loggedIn = false;

    Camera cam;

    public BattleGame(String title, int width, int height, int scale) {
        super(title, width, height, scale);
    }

    public void init() throws GameException {
        cam = new Camera(0, 0, display.screen.getWidth(), display.screen.getHeight());

        DeliveryService deliveryService = new DeliveryService("localhost");
        deliveryService.start();

        LoginPacket login = new LoginPacket();
        login.name = "Simone";

        deliveryService.sendTCP(login);
    }

    public void tick(int delta) {
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
    }

    public void render(Screen screen) {
        screen.setProjectionMatrix(cam.getProjectionMatrix());
        screen.translate(cam.getX(), cam.getY());

        screen.fillRect(0, 0, 10, 10);

        screen.fillRect(400, 100, 10, 10);

        screen.translate(-cam.getX(), -cam.getY());
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
