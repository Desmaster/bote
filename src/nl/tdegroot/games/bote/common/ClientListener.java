package nl.tdegroot.games.bote.common;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import nl.tdegroot.games.bote.client.BattleGame;
import nl.tdegroot.games.bote.client.states.GameState;
import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.bote.common.level.Level;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.pixxel.state.State;
import nl.tdegroot.games.pixxel.util.Log;

import java.util.UUID;

public class ClientListener extends Listener {

    private final GameState gameState;
    private Level level;

    //TODO: Make this compatible with multiple states?
    public ClientListener(GameState gameState) {
        this.gameState = gameState;
    }

    public void connected(Connection connection) {
        Log.info("Connected to " + connection.toString());
    }

    public void disconnected(Connection connection) {
        Log.info("Disconnected from " + connection.toString());
    }

    public void received(Connection connection, Object obj) {
        if (!(obj instanceof Packet)) return;
        ((Packet) obj).onClient(connection, this);
    }

    public GameState getState() {
        return gameState;
    }

    public synchronized Level getLevel() {
        return level;
    }

    public synchronized void setLevel(Level level) {
        this.level = level;
    }

    public synchronized World getWorld() {
        return gameState.world;
    };

    public synchronized String getPlayerID() {
        return gameState.playerid;
    }
}
