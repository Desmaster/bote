package nl.tdegroot.games.bote.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import nl.tdegroot.games.bote.client.entity.Player;
import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.bote.common.entity.EntityPacket;
import nl.tdegroot.games.bote.common.entity.EntityState;
import nl.tdegroot.games.bote.common.entity.PlayerPacket;
import nl.tdegroot.games.bote.common.packet.ImageFilePacket;
import nl.tdegroot.games.bote.common.packet.LoginPacket;
import nl.tdegroot.games.bote.common.packet.TiledMapPacket;
import nl.tdegroot.games.bote.common.packet.WorldPacket;
import nl.tdegroot.games.pixxel.gfx.Color;
import nl.tdegroot.games.pixxel.gfx.Sprite;
import nl.tdegroot.games.pixxel.map.tiled.Layer;
import nl.tdegroot.games.pixxel.map.tiled.TiledMap;
import nl.tdegroot.games.pixxel.math.Vector2i;

import java.util.ArrayList;

public class Network {

    public static final int TCP_PORT = 62333;
    public static final int UDP_PORT = 62334;

    public static final String MAP_LOCATION = "res/tiledmap.tmx";

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(LoginPacket.class);
        kryo.register(TiledMapPacket.class);
        kryo.register(ImageFilePacket.class);

        kryo.register(EntityPacket.class);
        kryo.register(PlayerPacket.class);
        kryo.register(WorldPacket.class);

        kryo.register(World.class);
        kryo.register(Player.class);
        kryo.register(EntityState.class);

        kryo.register(TiledMap.class);
        kryo.register(Layer.class);
        kryo.register(Sprite.class);
        kryo.register(Sprite[].class);
        kryo.register(Color.class);
        kryo.register(Vector2i.class);

        kryo.register(int[].class);
        kryo.register(int[][].class);
        kryo.register(int[][][].class);
        kryo.register(ArrayList.class);
        kryo.register(Class.class);
    }

}
