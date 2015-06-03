package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.bote.common.level.Level;
import nl.tdegroot.games.pixxel.GameException;
import nl.tdegroot.games.pixxel.gfx.Sprite;
import nl.tdegroot.games.pixxel.map.tiled.TiledMap;
import nl.tdegroot.games.pixxel.util.FileUtils;
import nl.tdegroot.games.pixxel.util.Log;
import nl.tdegroot.games.pixxel.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TiledMapPacket implements Packet {

    private String tiledMapContents;
    private Sprite[] sprites;

    public void onServer(Connection connection, ServerListener serverListener) {
        tiledMapContents = getTiledMapContents();
        sprites = getSpriteSheets("res/tiledmap.tmx");

        connection.sendTCP(this);
    }

    public void onClient(Connection connection, ClientListener clientListener) {
        if (clientListener.getLevel().getMap() != null) return;

        Log.info("Received map with height: " + tiledMapContents);
        for (int i = 0; i < sprites.length; i++) {
            Sprite sprite = sprites[i];
            BufferedImage image = FileUtils.createImageFromPixels(sprite.getPixels(), sprite.width, sprite.height);
            String path = FileUtils.getHomeDirectory() + sprite.getFileName();
            FileUtils.writeImage(path, image);
        }
        InputStream in = new ByteArrayInputStream(tiledMapContents.getBytes(StandardCharsets.UTF_8));
        try {
            TiledMap map = new TiledMap(in, FileUtils.getHomeDirectory());
            clientListener.getLevel().setMap(map);
        } catch (GameException e) {
            e.printStackTrace();
        }
        Log.info(System.getProperty("user.home"));
        // TODO: https://stackoverflow.com/questions/562160/in-java-how-do-i-parse-xml-as-a-string-instead-of-a-file
    }

    private String getTiledMapContents() {
        InputStream is = ResourceLoader.getResourceAsStream("res/tiledmap.tmx");
        InputStreamReader input = new InputStreamReader(is);
        final int CHARS_PER_PAGE = 5000; //counting spaces
        final char[] buffer = new char[CHARS_PER_PAGE];
        StringBuilder output = new StringBuilder(CHARS_PER_PAGE);
        try {
            for (int read = input.read(buffer, 0, buffer.length);
                 read != -1;
                 read = input.read(buffer, 0, buffer.length)) {
                output.append(buffer, 0, read);
            }
        } catch (IOException e) {
        }

        return output.toString();
    }

    private Sprite[] getSpriteSheets(String ref) {
        Sprite[] sprites = null;
        String tileSetLocation = ref.substring(0, ref.lastIndexOf("/"));
        try {
            InputStream in = ResourceLoader.getResourceAsStream(ref);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            builder.setEntityResolver((publicId, systemId) -> new InputSource(new ByteArrayInputStream(new byte[0])));
            Document doc = builder.parse(in);

            Element docElement = doc.getDocumentElement();

            NodeList setNodes = docElement.getElementsByTagName("tileset");

            for (int i = 0; i < setNodes.getLength(); i++) {
                Element current = (Element) setNodes.item(i);
                NodeList list = current.getElementsByTagName("image");
                sprites = new Sprite[list.getLength() * setNodes.getLength()];
                for (int j = 0; j < list.getLength(); j++) {
                    Element image = (Element) list.item(i);
                    String source = image.getAttribute("source");

                    String currentPath = tileSetLocation + "/" + source;
                    currentPath = currentPath.replace('\\', '/');

                    Sprite sprite = new Sprite(currentPath);
                    sprites[j + i * list.getLength()] = sprite;
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return sprites;
    }

    public static BufferedImage getImageFromArray(int[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0, 0, width, height, pixels);
        return image;
    }

}
