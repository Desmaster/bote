package nl.tdegroot.games.bote.common;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import nl.tdegroot.games.bote.common.level.Level;
import nl.tdegroot.games.bote.common.packet.Packet;
import nl.tdegroot.games.bote.server.GameServer;
import nl.tdegroot.games.pixxel.util.Log;

public class ServerListener extends Listener {

    private GameServer gameServer;
    private Level level;

    public ServerListener(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public void connected(Connection connection) {
        Log.info(connection.toString() + " connected!");
    }

    public void disconnected(Connection connection) {
        Log.info(connection.toString() + " disconnected!");
    }

    public void received(Connection connection, Object obj) {
        if (!(obj instanceof Packet)) return;
        ((Packet) obj).onServer(connection, this);
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;

    }
}
