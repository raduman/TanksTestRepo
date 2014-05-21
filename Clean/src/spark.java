
import java.awt.Graphics2D;

public class spark implements unit {

    battlefield context;
    int animation = 0;
    double x;
    double y;

    public spark(battlefield bf, double xx, double yy) {
        context = bf;
        x = xx;
        y = yy;
    }

    @Override
    public void update() {
        animation++;
        if (animation == 10) {
            context.explosions.remove(this);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int X = (int) x - Lib.sparkX;
        int Y = (int) y - Lib.sparkY;
        g.drawImage(Lib.sparksprites.get(animation), X, Y, null);
    }
}
