package nl.tdegroot.games.bote.common.packet;

import com.esotericsoftware.kryonet.Connection;
import nl.tdegroot.games.bote.common.ClientListener;
import nl.tdegroot.games.bote.common.ServerListener;
import nl.tdegroot.games.pixxel.map.tiled.TileSet;
import nl.tdegroot.games.pixxel.map.tiled.TiledMap;
import nl.tdegroot.games.pixxel.util.Log;
import nl.tdegroot.games.pixxel.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;

public class TiledMapPacket implements Packet {

    private String tiledMapContents;
    private byte[][] tileSetData;

    public void onServer(Connection connection, ServerListener serverListener) {
        tiledMapContents = getTiledMapContents();
        tileSetData = getTilesets("res/tiledmap.tmx");

        connection.sendTCP(this);
    }

    public void onClient(Connection connection, ClientListener clientListener) {
        Log.info("Received map with height: " + tiledMapContents);

        // TODO: https://stackoverflow.com/questions/585534/what-is-the-best-way-to-find-the-users-home-directory-in-java
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

    private byte[][] getTilesets(String ref) {
        byte[][] tilesets = null;
        String tileSetLocation = ref.substring(0, ref.lastIndexOf("/"));
        try {
            InputStream in = ResourceLoader.getResourceAsStream(ref);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new EntityResolver() {
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    return new InputSource(new ByteArrayInputStream(new byte[0]));
                }
            });
            Document doc = builder.parse(in);

            Element docElement = doc.getDocumentElement();

            NodeList setNodes = docElement.getElementsByTagName("tileset");

            for (int i = 0; i < setNodes.getLength(); i++) {
                Element current = (Element) setNodes.item(i);
                NodeList list = current.getElementsByTagName("image");
                tilesets = new byte[list.getLength()][];
                for (int j = 0; j < list.getLength(); j++) {
                    Element image = (Element) list.item(i);
                    String source = image.getAttribute("source");
                    String currentPath = tileSetLocation + "/" + source;

                    BufferedImage bufferedImage = ImageIO.read(ResourceLoader.getResourceAsStream(currentPath));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "png", baos);
                    byte[] data = baos.toByteArray();
                    tilesets[i] = data;
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return tilesets;
    }

}
