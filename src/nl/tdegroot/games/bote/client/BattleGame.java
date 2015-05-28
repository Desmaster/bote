package nl.tdegroot.games.bote.client;

import nl.tdegroot.games.pixxel.GameException;
import nl.tdegroot.games.pixxel.PixxelGame;

public class BattleGame extends PixxelGame{

    public BattleGame(String title, int width, int height, int scale) {
        super(title, width, height, scale);
    }

    public void init() throws GameException {

    }

    public void tick(int delta) {

    }

    public void render() {

    }

    public static void main(String[] args) {
        BattleGame game = new BattleGame("Battle of Tiled Environments", 1280, 720, 4);
        game.start();
    }
}
