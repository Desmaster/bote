package nl.tdegroot.games.bote.common;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import nl.tdegroot.games.bote.client.BattleGame;
import nl.tdegroot.games.bote.common.level.Level;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.pixxel.util.Log;

public class ClientListener extends Listener {

    private final BattleGame game;
    private Level level;

    public ClientListener(BattleGame game) {
        this.game = game;
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

    public BattleGame getGame() {
        return game;
    }

    public synchronized Level getLevel() {
        return level;
    }

    public synchronized void setLevel(Level level) {
        this.level = level;
    }

}
