
import java.awt.Color;
import java.awt.Graphics2D;

public class deathMessage implements unit {

    battlefield context;
    double x;
    double y;
    int step = 0;
    double yspeed = 0.2;
    double xspeed;
    String str;
    String str2;
    String str3;
    String str4;
    Color color;
    Color color2;

    deathMessage(battlefield bf, String s, String ss, String sss, String ssss, Color c, Color c2, double xx, double yy) {
        context = bf;
        str = s;
        str2 = ss;
        str3 = sss;
        str4 = ssss;
        color = c;
        color2 = c2;
        x = xx;
        y = yy;
    }

    @Override
    public void update() {
        step++;
        y -= yspeed;
        if (step == 240) {
            context.deathMessages.remove(this);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int fullLength = Lib.strLength(str + " " + str2 + " " + str3 + " " + str4)/2;
        if ((int) x + fullLength < Lib.W) {
            g.setColor(Color.white);
            g.setFont(Lib.getSmallFont());
            g.drawString(str + " ", (int) x, (int) y);
            g.setColor(color);
            g.drawString(str2 + " ", (int) x + Lib.strLength(str + " ") / 2, (int) y);
            g.setColor(Color.white);
            g.setFont(Lib.getSmallFont());
            g.drawString(str3 + " ", (int) x + Lib.strLength(str + " " + str2 + " ") / 2, (int) y);
            g.setColor(color2);
            g.drawString(str4, (int) x + Lib.strLength(str + " " + str2 + " " + str3 + " ") / 2, (int) y);
        } else {
            g.setColor(Color.white);
            g.setFont(Lib.getSmallFont());
            g.drawString(str + " ", (int) x - fullLength, (int) y);
            g.setColor(color);
            g.drawString(str2 + " ", (int) x - fullLength + Lib.strLength(str + " ") / 2, (int) y);
            g.setColor(Color.white);
            g.setFont(Lib.getSmallFont());
            g.drawString(str3 + " ", (int) x - fullLength + Lib.strLength(str + " " + str2 + " ") / 2, (int) y);
            g.setColor(color2);
            g.drawString(str4, (int) x - fullLength + Lib.strLength(str + " " + str2 + " " + str3 + " ") / 2, (int) y);

        }
    }
}
