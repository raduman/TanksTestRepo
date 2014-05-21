
import java.awt.Color;
import java.awt.Graphics2D;


public class flyingString implements unit{
    
    battlefield context;
    double x;
    double y;
    int step = 0;
    double yspeed=1;
    double xspeed;
    String str;
    Color color;
        flyingString(battlefield bf, String s, Color c, double xx, double yy) {
        context = bf;
        str=s;
        color=c;
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
        g.setColor(color);
        g.setFont(Lib.getSmallFont());
        g.drawString(str, (int) x, (int) y);
    }
    
}
