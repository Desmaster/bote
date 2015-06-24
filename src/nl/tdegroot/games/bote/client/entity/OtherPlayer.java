package nl.tdegroot.games.bote.client.entity;

import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.pixxel.gfx.Animation;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.gfx.SpriteSheet;

public class OtherPlayer extends Entity {

    private SpriteSheet spriteSheet;
    private Animation walkAnimation;

    public OtherPlayer(World world) {
        super(world);
    }

    public void init() {
        spriteSheet = new SpriteSheet("res/player_trans.png", 32, 32);
        walkAnimation = new Animation(spriteSheet);
        walkAnimation.setFrameStrip(40, 49);
    }

    public void tick() {

    }

    public void render(Screen screen) {
        screen.drawString(getPosition().getX(), getPosition().getY(), "Other player");
        walkAnimation.render(getPosition().getX(), getPosition().getY(), screen);
    }
}
