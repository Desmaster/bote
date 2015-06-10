package nl.tdegroot.games.bote.server.world;

import nl.tdegroot.games.bote.client.entity.Entity;
import nl.tdegroot.games.bote.common.entity.EntityState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ServerWorld {

    public ConcurrentHashMap<String, EntityState> entities = new ConcurrentHashMap<String, EntityState>();

    public ServerWorld() {

    }

    public ConcurrentHashMap<String, EntityState> getEntities() {
        return entities;
    }
}
