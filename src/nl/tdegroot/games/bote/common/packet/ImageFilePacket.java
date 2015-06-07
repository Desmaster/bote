package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.pixxel.util.FileUtils;

import java.awt.image.BufferedImage;

public class ImageFilePacket implements Packet {

    public String fileName;
    public int[] pixels;

    public int width;
    public int height;

    public void onServer(Connection connection, ServerListener serverListener) {

    }

    public void onClient(Connection connection, ClientListener clientListener) {
        BufferedImage image = FileUtils.createImageFromPixels(pixels, width, height);
        String path = FileUtils.getHomeDirectory() + fileName;
        FileUtils.writeImage(path, image);
    }
}
