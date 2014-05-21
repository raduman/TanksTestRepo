
import java.awt.Graphics2D;

public class blackHoleExplosion implements unit {

    battlefield context;
    int animation = 0;
    double x;
    double y;

    public blackHoleExplosion(battlefield bf, double xx, double yy) {
        context = bf;
        x = xx;
        y = yy;
    }

    @Override
    public void update() {
        animation++;
        if (animation == 5) {
            context.explosions.remove(this);
            if (x < Lib.tankRadius) {
                x = Lib.tankRadius;
            } else if (x > Lib.W - Lib.tankRadius) {
                x = Lib.W - Lib.tankRadius;
            }
            if (y < Lib.tankRadius) {
                y = Lib.tankRadius;
            } else if (y > Lib.H - Lib.tankRadius) {
                y = Lib.H - Lib.tankRadius;
            }
            context.blackHoles.add(new blackHole(context, x, y));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int X = (int) x - Lib.voidspritesized2;
        int Y = (int) y - Lib.voidspritesized2;
        g.drawImage(Lib.voidsprites.get(animation), X, Y, null);
    }
}
