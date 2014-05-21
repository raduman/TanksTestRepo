
import java.awt.Graphics2D;

public class blackHole implements unit {

    battlefield context;
    int animation = 0;
    double x;
    double y;
    double pull = 2.99;
    double range = 50;
    //double projPull = 10;
    //double projRange = 100;
    
    public blackHole(battlefield bf, double xx, double yy) {
        context = bf;
        x = xx;
        y = yy;
    }

    @Override
    public void update() {
        animation++;
        tank j;
        double d;
        double amount, deg;
        for (int i = 0; i < context.tanks.size(); i++) {
            j = context.tanks.get(i);
            d = Lib.dist(x, y, j.x, j.y);
            if (d < range) {
                amount = (range - d) / range;
                deg = Lib.vectorToDegree(x, y, j.x, j.y);
                if (d < amount * pull) {
                    j.x = x;
                    j.y = y;
                } else {
                    j.x -= amount * pull * Math.cos(deg * Lib.toRad);
                    j.y += amount * pull * Math.sin(deg * Lib.toRad);
                }
            }
        }
/*
        unit u; cannonround c;
        for (int i = 0; i < context.bullets.size(); i++) {
            u=context.bullets.get(i);
            if (u instanceof cannonround){
            c=(cannonround)u;
            
            d = Lib.dist(x, y, c.x, c.y);
            if (d < projRange) {
                amount = (projRange - d) / projRange;
                deg = Lib.vectorToDegree(x, y, c.x, c.y);
                if (d < amount * projPull) {
                    c.x = x;
                    c.y = y;
                } else {
                    c.x -= amount * projPull * Math.cos(deg * Lib.toRad);
                    c.y += amount * projPull * Math.sin(deg * Lib.toRad);
                }
            }}
        }
*/
        if (animation == 100) {
            context.blackHoles.remove(this);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int X = (int) x - Lib.voidspritesized2;
        int Y = (int) y - Lib.voidspritesized2;
        g.drawImage(Lib.voidsprites.get(4 + animation % 7), X, Y, null);
    }
}
