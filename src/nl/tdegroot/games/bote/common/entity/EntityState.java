package nl.tdegroot.games.bote.common.entity;

import nl.tdegroot.games.pixxel.math.Vector2i;

public class EntityState {
    private int id = 0;
    public Vector2i position;
    public Class entityClass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id == 0) this.id = id;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        if (this.entityClass == null) this.entityClass = entityClass;
    }
}
