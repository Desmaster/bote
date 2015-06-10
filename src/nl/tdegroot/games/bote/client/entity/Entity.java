package nl.tdegroot.games.bote.client.entity;

import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.bote.common.entity.EntityState;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.math.Vector2i;

import java.util.UUID;

public abstract class Entity {

    public final String id = UUID.randomUUID().toString();

    World world;
    public Vector2i pos;

    public Entity(World world, int x, int y) {
        this.world = world;
        pos = new Vector2i(x, y);

        world.addEntity(this);
    };

    public void updateState(EntityState state) {
        pos = state.position;
    }

    public abstract void init();

    public abstract void tick(int delta);

    public abstract void render(Screen screen);
}
