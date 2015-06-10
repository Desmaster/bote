package nl.tdegroot.games.bote.client;

import com.esotericsoftware.kryonet.Client;
import nl.tdegroot.games.bote.client.states.GameState;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.Network;
import nl.tdegroot.games.bote.common.level.Level;
import nl.tdegroot.games.bote.common.packet.LoginPacket;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.bote.common.packet.TiledMapPacket;
import nl.tdegroot.games.pixxel.Display;
import nl.tdegroot.games.pixxel.GameException;
import nl.tdegroot.games.pixxel.Keyboard;
import nl.tdegroot.games.pixxel.PixxelGame;
import nl.tdegroot.games.pixxel.gfx.Camera;
import nl.tdegroot.games.pixxel.gfx.Color;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.state.State;
import nl.tdegroot.games.pixxel.util.Log;

import java.io.IOException;
import java.net.InetAddress;

public class BattleGame extends PixxelGame {

    public final State gameState;

    public BattleGame(String title, int width, int height, int scale, String host) {
        super(title, width, height, scale);

        gameState = new GameState(this, host);
        setCurrentState(gameState);
    }

    public static void main(String[] args) {
        BattleGame game = new BattleGame("Battle of Tiled Environments", 1280, 720, 4, "localhost");
        game.setLogFps(false);
        game.start();
    }
}
