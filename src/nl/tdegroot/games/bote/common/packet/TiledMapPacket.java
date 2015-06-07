package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.pixxel.GameException;
import nl.tdegroot.games.pixxel.map.tiled.TiledMap;
import nl.tdegroot.games.pixxel.util.FileUtils;
import nl.tdegroot.games.pixxel.util.Log;
import nl.tdegroot.games.pixxel.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TiledMapPacket implements Packet {

    private String tiledMapContents;

    public void onServer(Connection connection, ServerListener serverListener) {
        tiledMapContents = getTiledMapContents();
        sendTilesets("res/tiledmap.tmx", connection);
        connection.sendTCP(this);
    }

    public void onClient(Connection connection, ClientListener clientListener) {
        if (clientListener.getLevel().getMap() != null) return;

        Log.info("Received map with height: " + tiledMapContents);

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

    private void sendTilesets(String ref, Connection connection) {
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
                for (int j = 0; j < list.getLength(); j++) {
                    Element imageNode = (Element) list.item(j);
                    String source = imageNode.getAttribute("source");

                    String currentPath = tileSetLocation + "/" + source;
                    currentPath = currentPath.replace('\\', '/');

                    BufferedImage image = ImageIO.read(ResourceLoader.getResourceAsStream(currentPath));
                    int imageWidth = image.getWidth();
                    int imageHeight = image.getHeight();
                    int[] pixels = new int[imageWidth * imageHeight];
                    image.getRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);

                    int index = currentPath.lastIndexOf("/");
                    String fileName = currentPath.substring(index + 1);

                    ImageFilePacket imagePacket = new ImageFilePacket();
                    imagePacket.fileName = fileName;
                    imagePacket.pixels = pixels;
                    imagePacket.width = imageWidth;
                    imagePacket.height = imageHeight;

                    connection.sendTCP(imagePacket);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
