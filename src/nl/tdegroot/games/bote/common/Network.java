package nl.tdegroot.games.bote.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {

    public static final int TCP_PORT = 62333;
    public static final int UDP_PORT = 62334;

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(LoginPacket.class);
    }

}
