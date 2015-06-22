package nl.tdegroot.games.bote.client.entity;

import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.bote.common.entity.EntityState;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.math.Vector2i;

import java.util.Objects;

public abstract class Entity {

    private int id = 0;
    private static String entityType;

    private World world;
    private EntityState state;

    public Vector2i position;

    public Entity(World world, int x, int y) {
        world.addEntity(this);

        position = new Vector2i(x, y);
        state = new EntityState();
        state.setEntityClass(getClass());
    };

    public int getId() { return id; }

    /**
     * Sets the Entity Id
     * Can only be set once
     * @param id Id of the Entity
     */
    public void setId(int id) {
        if (this.id == 0) this.id = id;
    }

    public static void setType(String type) { if (entityType == null) entityType = type; }

    public static String getType() { return entityType; }

    public void setState(EntityState entityState) {
        state = entityState;

        if (!Objects.equals(getType(), "PLAYER") || entityType == null) {
            entityType = getType();
        }

        position  = entityState.position;
    }

    public EntityState getState() {
        return state;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public abstract void init();

    public abstract void tick();

    public abstract void render(Screen screen);
}
