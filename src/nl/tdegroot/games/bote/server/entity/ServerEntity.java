package nl.tdegroot.games.bote.server.entity;

import nl.tdegroot.games.bote.client.entity.Player;
import nl.tdegroot.games.pixxel.math.Vector2i;

public class ServerEntity {

    private int id;
    private Vector2i position;
    public Class entityClass = Player.class;
    private String name;

    public ServerEntity(int id, String name) {
        this.id = id;
        this.name = name;

        position = new Vector2i(100, 100);
    }

    public int getId() {
        return id;
    }

    public Vector2i getPosition() {
        return position;
    }

    public void setPosition(Vector2i position) {
        this.position = position;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public String getName() {
        return name;
    }

}
