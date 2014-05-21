
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class heatbar {
    tank owner;
    BufferedImage bi;
    int width;
    int fill;
    int height;

    heatbar(tank t) {
        owner = t;
        height = 2;
        width = Lib.spritesized2;
        fill = width;
        bi = new BufferedImage(width + 2, height + 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bi.createGraphics();
        g.setColor(new Color(200, 100, 255));
        g.fillRect(0, 0, width + 2, height + 2);
        g.setBackground(Lib.transparentColor);
        g.clearRect(1, 1 , width, height);
        g.dispose();
    }

    public void update() {
            if ((int) ((owner.heat / owner.maxHeat) * width) != fill) {
                fill = (int) ((owner.heat / owner.maxHeat) * width);
                Graphics2D g = (Graphics2D) bi.getGraphics();
                g.setColor(new Color(200, 100, 255));
                g.fillRect(0, 0, width + 2, height + 2);
                g.setBackground(Lib.transparentColor);
                g.clearRect(1 + fill, 1 , width - fill, height);
                g.dispose();
            }
    }
}
