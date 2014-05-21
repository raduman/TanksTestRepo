
import java.awt.Color;

public class tankGraphics {

    spritesheet tankSprites;
    spritesheet turretSprites;
    spritesheet shadowSprites;
    Color scanColor;
    Color scanLineColor;
    Color color;
    tankGraphics(spritesheet a, spritesheet b, spritesheet c, Color aa, Color bb,Color cc) {
        tankSprites = a;
        turretSprites = b;
        shadowSprites = c;
        scanColor = aa;
        scanLineColor = bb;
        color=cc;
    }
}
