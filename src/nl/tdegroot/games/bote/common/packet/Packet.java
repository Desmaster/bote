package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;

public interface Packet {

    public void onServer(Connection connection, ServerListener serverListener);

    public void onClient(Connection connection, ClientListener clientListener);

}
