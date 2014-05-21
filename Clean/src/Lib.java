
import java.awt.Color;
import java.awt.Font;
import java.io.File;

public class Lib {

    static boolean disco = false;
    static boolean clipping = false;
    static boolean flying = false;
    static final double hp = 200.0;
    static final double maxhp = 200.0;
    static final double maxspeed = 3.0;
    static final double speedincrement = 0.05;
    static final double rotationincrement = 5.0;
    static final double turretrotationincrement = 10.0;
    static final double heatincrement = 1.0;
    static final double maxcooldown = 0.0;
    static final double cooldownincrement = 1.0;
    static final double maxheat = 60.0;
    static final double heatpershot = 30.0;
    static double friction = 0.05;
    static int tankRadius = 20;

    
    static final String[] begin=new String[]{"here lies","rip","untimely demise of","the defeat of","death of"};
    static final String[] mid=new String[]{"humiliated by","annihilated by","destroyed by","ground to dust by",
    "blown to pieces by", "reduced to nothing by","ruined by","crushed by",", guts ripped open by",", face smashed in by"};
    
    
    final static int tilesize = 32;
    final static int spritesize = 128;
    final static int spritesized2 = spritesize / 2;
    final static int turretspritesized2 = 24;
    final static spritesheet explosionTile = new spritesheet("boom.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet grassTile = new spritesheet("grass.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet dirtTile = new spritesheet("dirt.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet darkgrassTile = new spritesheet("dgrass.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet roughdirtTile = new spritesheet("rough.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet winterdirtTile = new spritesheet("wdirt.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet wintergrassTile = new spritesheet("wgrass.png", 8, 4, tilesize, tilesize, 0, 0);
    //final static spritesheet snowTile = new spritesheet("snow.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet winterroughTile = new spritesheet("wrough.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet cavedirtTile = new spritesheet("cdirt.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet cavebrickTile = new spritesheet("cbrick.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet fallgrassTile = new spritesheet("fgrass.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet falldarkgrassTile = new spritesheet("fdgrass.png", 8, 4, tilesize, tilesize, 0, 0);
    final static spritesheet[] tileTheme = new spritesheet[]{grassTile, dirtTile, darkgrassTile, roughdirtTile, winterdirtTile, wintergrassTile,
        winterroughTile, cavedirtTile, cavebrickTile, fallgrassTile, falldarkgrassTile};
    final static spritesheet hitsprites = new spritesheet("hit.png", 3, 9, 128, 128, 0, 128, true);
    final static spritesheet hitturretsprites = new spritesheet("hitturret.png", 1, 16, 48, 48, 0, 0);
    final static spritesheet redtanksprites = new spritesheet("red.png", 3, 9, spritesize, spritesize, 0, 128, true);
    final static spritesheet redtankshadows = new spritesheet("redshadow.png", 1, 9, spritesize, spritesize, 0, 128, true);
    final static spritesheet redturretsprites = new spritesheet("redturret.png", 1, 16, 48, 48, 0, 0);
    final static spritesheet bluetanksprites = new spritesheet("blue.png", 3, 9, spritesize, spritesize, 0, 128, true);
    final static spritesheet bluetankshadows = new spritesheet("blueshadow.png", 1, 9, spritesize, spritesize, 0, 128, true);
    final static spritesheet blueturretsprites = new spritesheet("blueturret.png", 1, 16, 48, 48, 0, 0);
    final static spritesheet greentanksprites = new spritesheet("green.png", 3, 9, spritesize, spritesize, 0, 128, true);
    final static spritesheet greentankshadows = new spritesheet("greenshadow.png", 1, 9, spritesize, spritesize, 0, 128, true);
    final static spritesheet greenturretsprites = new spritesheet("greenturret.png", 1, 16, 48, 48, 0, 0);
    final static spritesheet yellowtanksprites = new spritesheet("yellow.png", 3, 9, spritesize, spritesize, 0, 128, true);
    final static spritesheet yellowtankshadows = new spritesheet("yellowshadow.png", 1, 9, spritesize, spritesize, 0, 128, true);
    final static spritesheet yellowturretsprites = new spritesheet("yellowturret.png", 1, 16, 48, 48, 0, 0);
    final static spritesheet cannonsprites = new spritesheet("cannon.png", 1, 5, 32, 32, 0, 0);
    final static spritesheet voidcannonsprites = new spritesheet("voidcannon.png", 1, 5, 32, 32, 0, 0);
    final static spritesheet sparksprites = new spritesheet("spark.png", 1, 10, 48, 48, 0, 0);
    final static spritesheet explsprites = new spritesheet("expl.png", 3, 4, 60, 60, 0, 0);
    final static spritesheet voidsprites = new spritesheet("void.png", 1, 11, 100, 100, 0, 0);
    final static spritesheet tankexplsprites = new spritesheet("bigexpl.png", 4, 5, 160, 120, 0, 0);
    final static int turretXbindpoints[] = {41, 35, 31, 28, 27, 28, 31, 35, 41, 45, 49, 52, 53, 52, 49, 45};
    final static int turretYbindpoints[] = {39, 38, 37, 34, 30, 27, 24, 22, 21, 22, 24, 27, 30, 34, 37, 38};
    final static int projXbindpoints[] = {8, 21, 30, 36, 40, 36, 31, 20, 8, -4, -15, -20, -24, -20, -14, -5};
    final static int projYbindpoints[] = {-17, -13, -9, -1, 9, 19, 27, 32, 30, 32, 27, 19, 9, -1, -9, -13};
    
    final static Color transparentColor = new Color(0, 0, 0, 0);
    final static int alpha = 40;
    final static Color redFill = new Color(255, 0, 0, alpha);
    final static Color blueFill = new Color(0, 0, 255, alpha);
    final static Color greenFill = new Color(0, 255, 0, alpha);
    final static Color yellowFill = new Color(255, 255, 0, alpha);
    final static int lineAlpha = 90;
    final static Color redLine = new Color(255, 0, 0, lineAlpha);
    final static Color blueLine = new Color(0, 0, 255, lineAlpha);
    final static Color greenLine = new Color(0, 255, 0, lineAlpha);
    final static Color yellowLine = new Color(255, 255, 0, lineAlpha);
    final static tankGraphics RED = new tankGraphics(redtanksprites, redturretsprites, redtankshadows, redFill, redLine,Color.red);
    final static tankGraphics BLUE = new tankGraphics(bluetanksprites, blueturretsprites, bluetankshadows, blueFill, blueLine,Color.blue);
    final static tankGraphics GREEN = new tankGraphics(greentanksprites, greenturretsprites, greentankshadows, greenFill, greenLine,Color.green);
    final static tankGraphics YELLOW = new tankGraphics(yellowtanksprites, yellowturretsprites, yellowtankshadows, yellowFill, yellowLine,Color.yellow);
    final static tankGraphics FLAVORS[] = new tankGraphics[]{RED, BLUE, GREEN, YELLOW};
    final static int cannonspritesized2 = 16;
    final static int explspritesized2 = 30;
    final static int voidspritesized2 = 50;
    final static int sparkX = 24;
    final static int sparkY = 32;
    final static int bigexplXspritesized2 = 80;
    final static int bigexplYspritesized2 = 60;
    final static int W = 1024;
    final static int H = 768;
    final static int ipotenuza = (int) Math.sqrt(H * H + W * W) + 200;
    final static int ipotenuza2 = 2 * ipotenuza;
    final static double toRad = Math.PI / 180.0;
    final static double toDeg = 180.0 / Math.PI;
    final static int charHeight = 24;
    final static int charWidth = 24;
    final static int ltWidth = 16;
    final static int iOffset = 16;
    final static int iWidth = 8;
    final static int oneOffset = 12;
    final static int charSpacer = 6;
    static Font font;
    static Font smallFont;
    static Font tinyFont;
    final static Color menuColor = new Color(0, 0, 0, 150);
    final static Color textColor = Color.white;
    final static Color menuHighlight = new Color(0, 0, 100, 150);
    final static Color textHighlight = new Color(255, 255, 50);
    final static startGame start = new startGame();
    final static exitGame exit = new exitGame();
    final static readSources read = new readSources();
    final static menu Param = new menu(new String[]{"one", "two", "three", "four"}, new menu[]{null, null, null, null}, new action[]{null, null, null, null});
    final static String weapons[] = new String[]{"cannon", "scatter", "void", "machinegun", "railgun"};
    final static String armors[] = new String[]{"plating", "shield", "fortress", "reactive", "defense"};
    final static String turrets[] = new String[]{"turret", "recoiler",};
    final static String tracks[] = new String[]{"treads", "grips"};
    final static String specials[] = new String[]{"gyroscope", "nanobots", "temper"};
    final static String weaponModifiers[] = new String[]{"load", "magazine", "field", "reload", "charge"};
    // load projectile speed vs damage           2 20            12,10           22   5
    //magazine how many projectiles can hold         3               8               13
    //field                           duration and strength vs firing speed and damage
    //reload                              damage and speed vs heat limit and cooldown
    //charge                               fire speed vs damage
    
    final static String armorModifiers[] = new String[]{"weight", "strength", "reduction", "treshold", "degree"};
    final static String turretModifiers[] = new String[]{"rotation", "recoil"};
    final static String trackModifiers[] = new String[]{"acceleration", "handling"};
    final static String specialModifiers[] = new String[]{"quality", "tech", "malice"};
    
    final static menu mainMenu = new menu(new String[]{"start", "read", "exit"}, new menu[]{null, null, null}, new action[]{start, read, exit});

    static Font getFont() {
        if (font == null) {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, new File("visitor1.ttf"));
                tinyFont = font.deriveFont(10f);
                smallFont = font.deriveFont(20f);
                font = font.deriveFont(40f);
            } catch (Exception ex) {
                System.out.println("the goddamned font is missing");
            }
        }
        return font;
    }

    static Font getSmallFont() {
        if (smallFont == null) {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, new File("visitor1.ttf"));
                tinyFont = font.deriveFont(10f);
                smallFont = font.deriveFont(20f);
                font = font.deriveFont(40f);
            } catch (Exception ex) {
                System.out.println("the goddamned font is missing");
            }
        }
        return smallFont;
    }

    static Font getTinyFont() {
        if (tinyFont == null) {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, new File("visitor1.ttf"));
                tinyFont = font.deriveFont(10f);
                smallFont = font.deriveFont(20f);
                font = font.deriveFont(40f);
            } catch (Exception ex) {
                System.out.println("the goddamned font is missing");
            }
        }
        return tinyFont;
    }

    static int strLength(String s) {
        return (s.length() * charWidth - (s.length() - s.replace("i", "").length()) * iOffset - (s.length() - s.replace("1", "").length()) * oneOffset);
    }

    static double angleDifference(double a, double b) {
        if (a > b) {
            if (a - b <= 180) {
                return a - b;
            }
            if (a > 180) {
                return (360 - a + b);
            } else {
                return a - b;
            }
        } else {
            if (b - a <= 180) {
                return b - a;
            }
            if (b > 180) {
                return (360 - b + a);
            } else {
                return b - a;
            }
        }
    }

    static boolean angleCase(double tinta, double original) {
        if (tinta > original) {
            if (tinta - original <= 180) {
                return true;
            }
            if (tinta > 180) {
                return false;
            } else {
                return true;
            }
        } else {
            if (original - tinta <= 180) {
                return false;
            }
            if (original > 180) {
                return true;
            } else {
                return false;
            }
        }
    }

    static int directionToSprite16(double angle) {
        if (angle < 101.25) {
            return 4 - (int) ((angle + 11.25) / 22.5);
        }
        return 15 - (int) ((angle - 101.25) / 22.5);
    }

    static double normalizeAngle(double angle) {
        if (angle > 360.0) {
            angle %= 360.0;
        }
        if (angle < 0.0) {
            angle %= 360.0;
            angle += 360.0;
        }
        return angle;
    }

    static double vectorToDegree(double x1, double y1, double x2, double y2) {
        if (y2 > y1) {
            return 360.0 - Math.acos((x2 - x1) / dist(x1, y1, x2, y2)) * toDeg;
        } else {
            return Math.acos((x2 - x1) / dist(x1, y1, x2, y2)) * toDeg;
        }
    }

    static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
