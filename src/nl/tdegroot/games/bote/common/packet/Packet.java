package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.client.BattleGame;
import nl.tdegroot.games.bote.server.GameServer;

public interface Packet {

    public void onServer(Connection connection, GameServer server);

    public void onClient(Connection connection, BattleGame game);

}
