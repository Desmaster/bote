package nl.tdegroot.games.bote.client.entity;

import nl.tdegroot.games.bote.client.world.World;
import nl.tdegroot.games.pixxel.Keyboard;
import nl.tdegroot.games.pixxel.gfx.Animation;
import nl.tdegroot.games.pixxel.gfx.Screen;
import nl.tdegroot.games.pixxel.gfx.SpriteSheet;
import nl.tdegroot.games.pixxel.math.Vector2i;

public class Player extends Entity implements Syncable {

    private static String entityType = "PLAYER";

    Vector2i size;
    SpriteSheet spriteSheet;
    Animation walkAnimation;

    public Player(World world, int x, int y) {
        super(world);

        position = new Vector2i(x, y);
    }

    @Override
    public void init() {
        spriteSheet = new SpriteSheet("res/player_trans.png", 32, 32);
        walkAnimation = new Animation(spriteSheet);
        walkAnimation.setFrameStrip(80, 89);
    }

    @Override
    public void tick() {
        int x = getPosition().getX();
        int y = getPosition().getY();

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            y -= 1;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            x += 1;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            y += 1;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            x -= 1;
        }

        getPosition().setX(x);
        getPosition().setY(y);
        getState().position = getPosition();
    }

    @Override
    public void render(Screen screen) {
//        spriteSheet.render(pos.getX(), pos.getY(), 0, 0, screen);

        walkAnimation.render(getPosition().getX(), getPosition().getY(), screen);
//        screen.drawString(getPosition().getX(), getPosition().getY(), "Lorey");
    }
}
