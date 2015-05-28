package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;

public interface Packet {

    public void onServer(Connection connection);

    public void onClient(Connection connection);

}
