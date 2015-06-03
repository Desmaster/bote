package nl.tdegroot.games.bote.common.level;

import nl.tdegroot.games.pixxel.map.tiled.TiledMap;

public class Level {

    private volatile TiledMap map;

    public synchronized TiledMap getMap() {
        return map;
    }

    public synchronized void setMap(TiledMap map) {
        this.map = map;
    }
}
