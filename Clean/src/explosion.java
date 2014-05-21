
import java.awt.Graphics2D;

public class explosion implements unit {

    battlefield context;
    int animation = 0;
    double x;
    double y;

    public explosion(battlefield bf, double xx, double yy) {
        context = bf;
        x = xx;
        y = yy;
    }

    @Override
    public void update() {
        animation++;
        if (animation == 4) {
            context.back.boom((int) x, (int) y);
        }
        if (animation == 12) {
            context.explosions.remove(this);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int X = (int) x - Lib.explspritesized2;
        int Y = (int) y - Lib.explspritesized2;
        g.drawImage(Lib.explsprites.get(animation), X, Y, null);
    }
}
