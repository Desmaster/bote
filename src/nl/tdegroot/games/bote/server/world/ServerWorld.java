package nl.tdegroot.games.bote.server.world;

import nl.tdegroot.games.bote.common.entity.EntityState;

import java.util.ArrayList;

public class ServerWorld {

    ArrayList<EntityState> entities = new ArrayList<EntityState>();

    public ServerWorld() {

    }

    public ArrayList<EntityState> getEntities() {
        return entities;
    }
}
