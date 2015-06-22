package nl.tdegroot.games.bote.server.world;

import nl.tdegroot.games.bote.common.entity.EntityState;
import nl.tdegroot.games.bote.common.event.EventDispatcher;
import nl.tdegroot.games.bote.common.event.EventHandler;
import nl.tdegroot.games.bote.common.event.EventListener;
import nl.tdegroot.games.bote.common.input.Command;
import nl.tdegroot.games.bote.common.input.event.CommandEvent;
import nl.tdegroot.games.pixxel.util.Random;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

public class ServerWorld implements EventHandler {

    private ArrayList<EntityState> entityStates = new ArrayList<EntityState>();

    public ServerWorld() {
        EventDispatcher.register(this);
    }

    @EventListener("command:players")
    public void playerEvent(CommandEvent command) {
        System.out.println(entityStates.size());
    }

    public ArrayList<EntityState> getEntityStates() {
        return entityStates;
    }

    public EntityState getEntity(int entityId) {
        for(EntityState entity : entityStates) {
            if (entity.getId() == entityId)
                return entity;
        }

        return null;
    }

    public EntityState registerEntity(EntityState entityState) {
        //TODO: Needs to be more unique
        int randomID = Random.nextInt(0, Integer.MAX_VALUE);
        System.out.println("randomID = " + randomID);
        entityState.setId(randomID);

        System.out.println("Entity ID:" + entityState.getId());

        entityStates.add(entityState);

        return entityState;
    }
}
