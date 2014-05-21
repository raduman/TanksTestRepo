
import java.awt.Graphics2D;

public class carcass implements unit {

    battlefield context;
    tankGraphics graphics;
    double direction = 0.0;
    double turretdirection = 0.0;
    double x;
    double y;
    int spritesized2 = 64;
    int turretXbindpoints[] = {41, 35, 31, 28, 27, 28, 31, 35, 41, 45, 49, 52, 53, 52, 49, 45};
    int turretYbindpoints[] = {39, 38, 37, 34, 30, 27, 24, 22, 21, 22, 24, 27, 30, 34, 37, 38};
    int step = 0;
    boolean hitThisFrame = false;

    public carcass(tank t) {
        context = t.context;
        graphics = t.graphics;
        direction = t.direction;
        turretdirection = t.turretDirection;
        x = t.x;
        y = t.y;
    }

    @Override
    public void update() {
        if (step % 14 == 0) {
            context.explosions.add(new explosion(context, x + Math.random() * 60 - 30, y + Math.random() * 60 - 30));
            hitThisFrame = true;
        }
        if (step % 7 == 0) {
            context.explosions.add(new spark(context, x + Math.random() * 40 - 20, y + Math.random() * 40 - 20));
            hitThisFrame = true;
        }

        if (step == 180) {
            context.deathAnimations.remove(this);
            context.explosions.add(new tankexplosion(context, x, y));
            context.back.tilt = 30;
        }
        step++;
    }

    @Override
    public void draw(Graphics2D g) {
        int randomX = 0;
        int randomY = 0;
        if (step % 7 == 0) {
            randomX = (int) (Math.random() * 4 - 2);
            randomY = (int) (Math.random() * 4 - 2);
        }
        int X = (int) x - spritesized2;
        int Y = (int) y - spritesized2;
        g.drawImage(graphics.shadowSprites.get(Lib.directionToSprite16(direction), 0), X + randomX, Y + randomY, null);
        if (hitThisFrame) {
            g.drawImage(Lib.hitsprites.get(Lib.directionToSprite16(direction), 0), X + randomX, Y + randomY, null);
            g.drawImage(Lib.hitturretsprites.get(0, Lib.directionToSprite16(turretdirection)), X + randomX + turretXbindpoints[Lib.directionToSprite16(direction)], Y + randomY + turretYbindpoints[Lib.directionToSprite16(direction)], null);
            hitThisFrame = false;
        } else {
            g.drawImage(graphics.tankSprites.get(Lib.directionToSprite16(direction), 0), X + randomX, Y + randomY, null);
            g.drawImage(graphics.turretSprites.get(0, Lib.directionToSprite16(turretdirection)), X + randomX + turretXbindpoints[Lib.directionToSprite16(direction)], Y + randomY + turretYbindpoints[Lib.directionToSprite16(direction)], null);
        }
    }
}
