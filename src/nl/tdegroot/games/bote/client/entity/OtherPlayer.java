package nl.tdegroot.games.bote.client.entity;

import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.pixxel.gfx.Animation;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.gfx.SpriteSheet;

public class OtherPlayer extends Entity {

    SpriteSheet spriteSheet;
    Animation walkAnimation;

    public OtherPlayer(World world, int x, int y) {
        super(world, x, y);
    }

    @Override
    public void init() {
        spriteSheet = new SpriteSheet("res/player_trans.png", 32, 32);
        walkAnimation = new Animation(spriteSheet);
        walkAnimation.setFrameStrip(25, 29);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Screen screen) {
        screen.drawString(position.getX(), position.getY(), "Other player");
        walkAnimation.render(position.getX(), position.getY(), screen);
    }
}
