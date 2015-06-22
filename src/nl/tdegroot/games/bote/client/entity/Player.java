package nl.tdegroot.games.bote.client.entity;

import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.bote.common.entity.EntityState;
import nl.tdegroot.games.pixxel.Keyboard;
import nl.tdegroot.games.pixxel.gfx.Animation;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.gfx.Sprite;
import nl.tdegroot.games.pixxel.gfx.SpriteSheet;
import nl.tdegroot.games.pixxel.math.Vector2i;

public class Player extends Entity {

    private static String entityType = "PLAYER";

    Vector2i size;
    SpriteSheet spriteSheet;
    Animation walkAnimation;

    public Player(World world, int x, int y) {
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
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.setY(position.getY() - 1);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.setX(position.getX() + 1);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.setY(position.getY() + 1);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.setX(position.getX() - 1);
        }
    }

    @Override
    public void render(Screen screen) {
//        spriteSheet.render(pos.getX(), pos.getY(), 0, 0, screen);

        walkAnimation.render(position.getX(), position.getY(), screen);
//        screen.drawString(position.getX(), position.getY(), "Lorey");
    }
}
