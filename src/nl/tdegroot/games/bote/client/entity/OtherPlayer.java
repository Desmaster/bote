package nl.tdegroot.games.bote.client.entity;

import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.pixxel.gfx.Animation;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.gfx.SpriteSheet;

public class OtherPlayer extends Entity {

    SpriteSheet spriteSheet;
    Animation walkAnimation;

    public OtherPlayer(World world) {
        super(world);
    }

    @Override
    public void init() {
        spriteSheet = new SpriteSheet("res/player_trans.png", 32, 32);
        walkAnimation = new Animation(spriteSheet);
        walkAnimation.setFrameStrip(40, 49);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Screen screen) {
        screen.drawString(getPos().getX(), getPos().getY(), "Other player");
        walkAnimation.render(getPos().getX(), getPos().getY(), screen);
    }
}
