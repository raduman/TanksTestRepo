
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class background {

    BufferedImage base;
    BufferedImage expl;
    BufferedImage flat;
    int terrain[][];
    int lin;
    int col;
    int xOffset;
    int yOffset;
    int xTileOffset;
    int yTileOffset;
    int tilt = 0;
    spritesheet defaultTile;
    
    background(int variation) {

        defaultTile=Lib.tileTheme[variation];
        
        xOffset = (Lib.W % Lib.tilesize) / 2 - Lib.tilesize / 2;
        yOffset = (Lib.H % Lib.tilesize) / 2 - Lib.tilesize / 2;
        xTileOffset = Lib.tilesize / 2 - xOffset;
        yTileOffset = Lib.tilesize / 2 - yOffset;

        int i, j;
        lin = Lib.H / Lib.tilesize;
        col = Lib.W / Lib.tilesize;

        terrain = new int[lin + 2][];
        for (i = 0; i < lin + 2; i++) {
            terrain[i] = new int[col + 2];
        }

        base = new BufferedImage((col + 1) * Lib.tilesize, (lin + 1) * Lib.tilesize, BufferedImage.TYPE_INT_RGB);
        expl = new BufferedImage((col + 1) * Lib.tilesize, (lin + 1) * Lib.tilesize, BufferedImage.TYPE_INT_ARGB);

        expl.createGraphics();
        Graphics2D g = (Graphics2D) base.createGraphics();

        for (i = 0; i <= lin; i++) {
            for (j = 0; j <= col; j++) {
                if (Math.random() < 0.3) {
                    g.drawImage(defaultTile.get((int) (Math.random() * 4), 5 + (int) (Math.random() * 3)), j * Lib.tilesize, i * Lib.tilesize, null);
                } else {
                    g.drawImage(defaultTile.get((int) (Math.random() * 4), 4), j * Lib.tilesize, i * Lib.tilesize, null);
                }
            }
        }
    }

    void boom(int i, int j) {
        int x = Math.round((float) (i - xOffset) / (float) Lib.tilesize) - 1;
        int y = Math.round((float) (j - yOffset) / (float) Lib.tilesize) - 1;
        int xx = x + 1;
        int yy = y + 1;
        Graphics2D g = (Graphics2D) expl.getGraphics();
        if ((xx > 0 && xx <= col) && (yy > 0 && yy <= lin)) {
            if (terrain[yy][xx] != 1) {
                terrain[yy][xx] = 1;

                g.setBackground(Lib.transparentColor);
                g.clearRect(x * Lib.tilesize, y * Lib.tilesize, 2 * Lib.tilesize, 2 * Lib.tilesize);

                if (terrain[yy - 1][xx - 1] == 1 && terrain[yy][xx - 1] == 1 && terrain[yy - 1][xx] == 1) {
                    if (Math.random() < 0.4) {
                        g.drawImage(Lib.explosionTile.get((int) (Math.random() * 4), 5 + (int) (Math.random() * 3)), x * Lib.tilesize, y * Lib.tilesize, null);
                    } else {
                        g.drawImage(Lib.explosionTile.get(0, 0), x * Lib.tilesize, y * Lib.tilesize, null);
                    }
                } else if (terrain[yy - 1][xx - 1] == 0 && terrain[yy][xx - 1] == 1 && terrain[yy - 1][xx] == 1) {
                    g.drawImage(Lib.explosionTile.get(1, 3), x * Lib.tilesize, y * Lib.tilesize, null);
                } else if (terrain[yy - 1][xx - 1] == 1 && terrain[yy][xx - 1] == 0 && terrain[yy - 1][xx] == 1) {
                    g.drawImage(Lib.explosionTile.get(3, 1), x * Lib.tilesize, y * Lib.tilesize, null);
                } else if (terrain[yy - 1][xx - 1] == 1 && terrain[yy][xx - 1] == 1 && terrain[yy - 1][xx] == 0) {
                    g.drawImage(Lib.explosionTile.get(2, 3), x * Lib.tilesize, y * Lib.tilesize, null);
                } else if (terrain[yy - 1][xx - 1] == 0 && terrain[yy][xx - 1] == 0 && terrain[yy - 1][xx] == 1) {
                    g.drawImage(Lib.explosionTile.get(1, 1), x * Lib.tilesize, y * Lib.tilesize, null);
                } else if (terrain[yy - 1][xx - 1] == 1 && terrain[yy][xx - 1] == 0 && terrain[yy - 1][xx] == 0) {
                    g.drawImage(Lib.explosionTile.get(2, 1), x * Lib.tilesize, y * Lib.tilesize, null);
                } else if (terrain[yy - 1][xx - 1] == 0 && terrain[yy][xx - 1] == 1 && terrain[yy - 1][xx] == 0) {
                    g.drawImage(Lib.explosionTile.get(0, 3), x * Lib.tilesize, y * Lib.tilesize, null);
                } else {
                    g.drawImage(Lib.explosionTile.get(0, 1), x * Lib.tilesize, y * Lib.tilesize, null);
                }

                if (terrain[yy - 1][xx] == 1 && terrain[yy - 1][xx + 1] == 1 && terrain[yy][xx + 1] == 1) {
                    if (Math.random() < 0.4) {
                        g.drawImage(Lib.explosionTile.get((int) (Math.random() * 4), 5 + (int) (Math.random() * 3)), (x + 1) * Lib.tilesize, y * Lib.tilesize, null);
                    } else {
                        g.drawImage(Lib.explosionTile.get(0, 0), (x + 1) * Lib.tilesize, y * Lib.tilesize, null);
                    }
                } else if (terrain[yy - 1][xx] == 0 && terrain[yy - 1][xx + 1] == 1 && terrain[yy][xx + 1] == 1) {
                    g.drawImage(Lib.explosionTile.get(1, 3), (x + 1) * Lib.tilesize, y * Lib.tilesize, null);
                } else if (terrain[yy - 1][xx] == 1 && terrain[yy - 1][xx + 1] == 0 && terrain[yy][xx + 1] == 1) {
                    g.drawImage(Lib.explosionTile.get(2, 3), (x + 1) * Lib.tilesize, y * Lib.tilesize, null);
                } else if (terrain[yy - 1][xx] == 1 && terrain[yy - 1][xx + 1] == 1 && terrain[yy][xx + 1] == 0) {
                    g.drawImage(Lib.explosionTile.get(3, 2), (x + 1) * Lib.tilesize, y * Lib.tilesize, null);
                } else if (terrain[yy - 1][xx] == 0 && terrain[yy - 1][xx + 1] == 0 && terrain[yy][xx + 1] == 1) {
                    g.drawImage(Lib.explosionTile.get(0, 3), (x + 1) * Lib.tilesize, y * Lib.tilesize, null);
                } else if (terrain[yy - 1][xx] == 1 && terrain[yy - 1][xx + 1] == 0 && terrain[yy][xx + 1] == 0) {
                    g.drawImage(Lib.explosionTile.get(2, 2), (x + 1) * Lib.tilesize, y * Lib.tilesize, null);
                } else if (terrain[yy - 1][xx] == 0 && terrain[yy - 1][xx + 1] == 1 && terrain[yy][xx + 1] == 0) {
                    g.drawImage(Lib.explosionTile.get(1, 2), (x + 1) * Lib.tilesize, y * Lib.tilesize, null);
                } else {
                    g.drawImage(Lib.explosionTile.get(0, 2), (x + 1) * Lib.tilesize, y * Lib.tilesize, null);
                }

                if (terrain[yy][xx - 1] == 1 && terrain[yy + 1][xx - 1] == 1 && terrain[yy + 1][xx] == 1) {
                    if (Math.random() < 0.4) {
                        g.drawImage(Lib.explosionTile.get((int) (Math.random() * 4), 5 + (int) (Math.random() * 3)), x * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                    } else {
                        g.drawImage(Lib.explosionTile.get(0, 0), x * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                    }
                } else if (terrain[yy][xx - 1] == 0 && terrain[yy + 1][xx - 1] == 1 && terrain[yy + 1][xx] == 1) {
                    g.drawImage(Lib.explosionTile.get(1, 3), x * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else if (terrain[yy][xx - 1] == 1 && terrain[yy + 1][xx - 1] == 0 && terrain[yy + 1][xx] == 1) {
                    g.drawImage(Lib.explosionTile.get(3, 1), x * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else if (terrain[yy][xx - 1] == 1 && terrain[yy + 1][xx - 1] == 1 && terrain[yy + 1][xx] == 0) {
                    g.drawImage(Lib.explosionTile.get(3, 2), x * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else if (terrain[yy][xx - 1] == 0 && terrain[yy + 1][xx - 1] == 0 && terrain[yy + 1][xx] == 1) {
                    g.drawImage(Lib.explosionTile.get(1, 1), x * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else if (terrain[yy][xx - 1] == 1 && terrain[yy + 1][xx - 1] == 0 && terrain[yy + 1][xx] == 0) {
                    g.drawImage(Lib.explosionTile.get(3, 0), x * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else if (terrain[yy][xx - 1] == 0 && terrain[yy + 1][xx - 1] == 1 && terrain[yy + 1][xx] == 0) {
                    g.drawImage(Lib.explosionTile.get(1, 2), x * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else {
                    g.drawImage(Lib.explosionTile.get(1, 0), x * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                }

                if (terrain[yy][xx + 1] == 1 && terrain[yy + 1][xx + 1] == 1 && terrain[yy + 1][xx] == 1) {
                    if (Math.random() < 0.4) {
                        g.drawImage(Lib.explosionTile.get((int) (Math.random() * 4), 5 + (int) (Math.random() * 3)), (x + 1) * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                    } else {
                        g.drawImage(Lib.explosionTile.get(0, 0), (x + 1) * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                    }
                } else if (terrain[yy][xx + 1] == 0 && terrain[yy + 1][xx + 1] == 1 && terrain[yy + 1][xx] == 1) {
                    g.drawImage(Lib.explosionTile.get(2, 3), (x + 1) * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else if (terrain[yy][xx + 1] == 1 && terrain[yy + 1][xx + 1] == 0 && terrain[yy + 1][xx] == 1) {
                    g.drawImage(Lib.explosionTile.get(3, 2), (x + 1) * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else if (terrain[yy][xx + 1] == 1 && terrain[yy + 1][xx + 1] == 1 && terrain[yy + 1][xx] == 0) {
                    g.drawImage(Lib.explosionTile.get(3, 1), (x + 1) * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else if (terrain[yy][xx + 1] == 0 && terrain[yy + 1][xx + 1] == 0 && terrain[yy + 1][xx] == 1) {
                    g.drawImage(Lib.explosionTile.get(2, 2), (x + 1) * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else if (terrain[yy][xx + 1] == 1 && terrain[yy + 1][xx + 1] == 0 && terrain[yy + 1][xx] == 0) {
                    g.drawImage(Lib.explosionTile.get(3, 0), (x + 1) * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else if (terrain[yy][xx + 1] == 0 && terrain[yy + 1][xx + 1] == 1 && terrain[yy + 1][xx] == 0) {
                    g.drawImage(Lib.explosionTile.get(2, 1), (x + 1) * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                } else {
                    g.drawImage(Lib.explosionTile.get(2, 0), (x + 1) * Lib.tilesize, (y + 1) * Lib.tilesize, null);
                }
            }
        }
        g.dispose();
    }

    void draw(Graphics2D g) {
        if (tilt > 0) {
            int ox = (int) (Math.random() * 10 - 5);
            int oy = (int) (Math.random() * 10 - 5);
            g.drawImage(base, ox + xOffset, oy + yOffset, null);
            g.drawImage(expl, ox + xOffset, oy + yOffset, null);
            tilt--;
        } else {
            g.drawImage(base, xOffset, yOffset, null);
            g.drawImage(expl, xOffset, yOffset, null);
        }
    }
}