package nl.tdegroot.games.bote.client;

import nl.tdegroot.games.bote.client.states.GameState;
import nl.tdegroot.games.pixxel.PixxelStateGame;
import nl.tdegroot.games.pixxel.state.State;

public class BattleGame extends PixxelStateGame {

    public final State gameState;

    public BattleGame(String title, int width, int height, int scale, String host) {
        super(title, width, height, scale);

        gameState = new GameState(this, host);
        setState(gameState);
    }

    public static void main(String[] args) {
        BattleGame game = new BattleGame("Battle of Tiled Environments", 1280, 720, 4, "localhost");
        game.setLogFps(true);
        game.start();
    }

}
