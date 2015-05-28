package nl.tdegroot.games.bote.common;

import com.esotericsoftware.kryonet.Connection;

public interface Packet {

    public void onServer(Connection connection);

    public void onClient(Connection connection);

}
