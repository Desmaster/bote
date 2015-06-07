package nl.tdegroot.games.bote.common.entity;

import nl.tdegroot.games.pixxel.math.Vector2i;

public class Entity {

    private Vector2i position;
    private Vector2i size;

    public Entity() {
        this(new Vector2i(), new Vector2i());
    }

    public Entity(Vector2i position, Vector2i size) {
        this.position = position;
        this.size = size;
    }

}
