package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;

public class FilePacket implements Packet {
    Byte[] data;

    @Override
    public void onServer(Connection connection, ServerListener serverListener) {
        //Send data to client


    }

    @Override
    public void onClient(Connection connection, ClientListener clientListener) {

    }
}
