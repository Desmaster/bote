package nl.tdegroot.games.bote.client.entity;

import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.bote.common.entity.EntityState;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.math.Vector2i;

import java.util.Objects;

public abstract class Entity {

    private int id = 0;

    private World world;
    private EntityState state;

    public Entity(World world) {
        world.addEntity(this);

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

    public void setState(EntityState entityState) {
        state = entityState;
        setId(entityState.getId());
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

    public Vector2i getPos() {
        return state.position;
    }
}
