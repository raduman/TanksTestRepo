
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class spritesheet {

    BufferedImage sprite[];
    int nrcol;

    public spritesheet(String filename, int cols, int lines, int spriteWidth, int spriteHeight, int offsetWidth, int offsetHeight) {
        nrcol = cols;
        BufferedImage temp = new BufferedImage(cols * (spriteWidth + offsetWidth) - offsetWidth, lines * (spriteHeight + offsetHeight) - offsetHeight, BufferedImage.TYPE_INT_ARGB);
        sprite = new BufferedImage[lines * cols];
        try {
            temp = ImageIO.read(new File(filename));
        } catch (Exception ex) {
            System.out.print(ex.toString());
        }
        int i, j;
        for (i = 0; i < lines; i++) {
            for (j = 0; j < cols; j++) {
                sprite[i * cols + j] = temp.getSubimage(j * (spriteWidth + offsetWidth), i * (spriteHeight + offsetHeight), spriteWidth, spriteHeight);
            }
        }
        temp.getGraphics().dispose();
    }

    spritesheet(String filename, int cols, int lines, int spriteWidth, int spriteHeight, int offsetWidth, int offsetHeight, boolean mirror) {
        nrcol = cols;
        BufferedImage temp = new BufferedImage(cols * (spriteWidth + offsetWidth) - offsetWidth, lines * (spriteHeight + offsetHeight) - offsetHeight, BufferedImage.TYPE_INT_ARGB);
        sprite = new BufferedImage[((2 * lines) - 2) * cols];
        try {
            temp = ImageIO.read(new File(filename));
        } catch (Exception ex) {
            System.out.print(ex.toString());
        }
        int i, j;
        for (i = 0; i < lines; i++) {
            for (j = 0; j < cols; j++) {
                sprite[i * cols + j] = temp.getSubimage(j * (spriteWidth + offsetWidth), i * (spriteHeight + offsetHeight), spriteWidth, spriteHeight);
            }
        }
        Graphics2D g;
        AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
        at.translate(-spriteWidth, 0);

        for (i = lines; i < (2 * lines) - 2; i++) {
            for (j = 0; j < cols; j++) {
                sprite[i * cols + j] = new BufferedImage(spriteWidth, spriteHeight, BufferedImage.TYPE_INT_ARGB);
                g = (Graphics2D) sprite[i * cols + j].createGraphics();

                g.drawImage(sprite[(2 * lines - 2 - i) * cols + j], at, null);
                g.dispose();
            }
        }

        temp.getGraphics().dispose();
    }

    BufferedImage get(int i, int j) {
        return sprite[i * nrcol + j];
    }

    BufferedImage get(int i) {
        return sprite[i];
    }
}
