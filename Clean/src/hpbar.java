
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class hpbar {

    tank owner;
    BufferedImage bi;
    int width;
    int fill;
    int height;
    int stage = 0;

    hpbar(tank t) {
        owner = t;
        height = 3;
        width = Lib.spritesized2;
        fill = width;
        bi = new BufferedImage(width + 2, height + 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bi.createGraphics();
        g.setColor(new Color(0, 200, 0));
        g.fillRect(0, 0, width + 2, height + 2);
        g.dispose();
    }

    public void update() {
            if ((int) ((owner.hp / owner.maxHp) * width) != fill) {
                fill = (int) ((owner.hp / owner.maxHp) * width);
                Graphics2D g = (Graphics2D) bi.getGraphics();
                g.setBackground(Lib.transparentColor);
                g.clearRect(1 + fill, 1, width - fill, height);
                g.dispose();
            }

            if (owner.hp < 0.8*owner.maxHp && stage < 1) {
                Graphics2D g = (Graphics2D) bi.getGraphics();
                g.setColor(new Color(100, 200, 0));
                g.fillRect(0, 0, width + 2, height + 2);
                g.setBackground(Lib.transparentColor);
                g.clearRect(1 + fill, 1, width - fill, height);
                stage++;
            }  else if (owner.hp < 0.6*owner.maxHp && stage < 2) {
                Graphics2D g = (Graphics2D) bi.getGraphics();
                g.setColor(new Color(200, 200, 0));
                g.fillRect(0, 0, width + 2, height + 2);
                g.setBackground(Lib.transparentColor);
                g.clearRect(1 + fill, 1, width - fill, height);
                stage++;
            } else if (owner.hp < 0.4*owner.maxHp && stage < 3) {
                Graphics2D g = (Graphics2D) bi.getGraphics();
                g.setColor(new Color(200, 100, 0));
                g.fillRect(0, 0, width + 2, height + 2);
                g.setBackground(Lib.transparentColor);
                g.clearRect(1 + fill, 1, width - fill, height);
                stage++;
            } else if (owner.hp < 0.2*owner.maxHp && stage < 4) {
                Graphics2D g = (Graphics2D) bi.getGraphics();
                g.setColor(new Color(200, 0, 0));
                g.fillRect(0, 0, width + 2, height + 2);
                g.setBackground(Lib.transparentColor);
                g.clearRect(1 + fill, 1, width - fill, height);
                stage++;
            }
        
    }
}
