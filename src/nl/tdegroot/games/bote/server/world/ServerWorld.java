package nl.tdegroot.games.bote.server.world;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
        System.out.println("Server world!");
        EventDispatcher.register(this);
    }

    @EventListener("command:players")
    public void playerEvent(CommandEvent command) {
        System.out.println(entityStates.size());
    }

    public ArrayList<EntityState> getEntityStates() {
        System.out.println(entityStates.size());
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
        int randomID = Random.nextInt(0, Integer.MAX_VALUE);

        entityState.setId(randomID);
        entityStates.add(entityState);

        return entityState;
    }
}
