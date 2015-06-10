package nl.tdegroot.games.bote.common.entity;

import nl.tdegroot.games.pixxel.math.Vector2i;

public class EntityState {

    private Vector2i position;
    private Vector2i size;

    public EntityState() {
        this(new Vector2i(), new Vector2i());
    }

    public EntityState(Vector2i position, Vector2i size) {
        this.position = position;
        this.size = size;
    }

}
