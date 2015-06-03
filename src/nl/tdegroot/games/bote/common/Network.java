package nl.tdegroot.games.bote.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import nl.tdegroot.games.bote.common.packet.LoginPacket;
import nl.tdegroot.games.bote.common.packet.TiledMapPacket;
import nl.tdegroot.games.pixxel.gfx.Color;
import nl.tdegroot.games.pixxel.gfx.Sprite;
import nl.tdegroot.games.pixxel.map.tiled.TiledMap;

public class Network {

    public static final int TCP_PORT = 62333;
    public static final int UDP_PORT = 62334;

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(LoginPacket.class);
        kryo.register(TiledMapPacket.class);
        kryo.register(TiledMap.class);
        kryo.register(int[].class);
        kryo.register(int[][].class);
        kryo.register(Sprite.class);
        kryo.register(Sprite[].class);
        kryo.register(Color.class);
    }

}
