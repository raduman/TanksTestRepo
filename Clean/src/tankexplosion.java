
import java.awt.Graphics2D;

public class tankexplosion implements unit {

    battlefield context;
    int animation = 0;
    double x;
    double y;

    public tankexplosion(battlefield bf, double xx, double yy) {
        context = bf;
        x = xx;
        y = yy;
    }

    @Override
    public void update() {
        animation++;
        if (animation == 20) {
            context.explosions.remove(this);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int X = (int) x - Lib.bigexplXspritesized2;
        int Y = (int) y - Lib.bigexplYspritesized2;
        g.drawImage(Lib.tankexplsprites.get(animation), X, Y, null);
    }
}
