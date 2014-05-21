
import java.awt.Color;
import java.awt.Graphics2D;

public class flyingDamage implements unit {

    battlefield context;
    int damage;
    double x;
    double y;
    int step = 0;
    double yspeed=1;
    double xspeed;
    flyingDamage(battlefield bf, int d, double xx, double yy) {
        context = bf;
        damage = d;
        x = xx;
        y = yy;
        xspeed=Math.random()*2-1;
    }

    @Override
    public void update() {
        step++;
        y -= yspeed;
        x+=xspeed;
        if (step == 80) {
            context.flyingText.remove(this);
        }
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.setFont(Lib.getSmallFont());
        if (damage>=1){
        g.drawString(Integer.toString(damage), (int) x, (int) y);
        }
        else {
        g.drawString(".", (int) x, (int) y);
        }
    }
}
