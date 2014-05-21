
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class battlefield {

    background back;
    ArrayList<tank> tanks;
    ArrayList<unit> bullets;
    ArrayList<unit> explosions;
    ArrayList<unit> blackHoles;
    ArrayList<unit> flyingText;
    ArrayList<carcass> deathAnimations;
    ArrayList<deathMessage> deathMessages;
    
    battlefield(ArrayList<costumizationModule> specs) {
        back = new background((int) (Math.random() * Lib.tileTheme.length));
        tanks = new ArrayList<tank>();
        bullets = new ArrayList<unit>();
        explosions = new ArrayList<unit>();
        blackHoles = new ArrayList<unit>();
        flyingText = new ArrayList<unit>();
        deathAnimations = new ArrayList<carcass>();
        deathMessages = new ArrayList<deathMessage>();
        
        for (int i = 0; i<specs.size(); i++) {
            tanks.add(new tank(this, i, 50 + Math.random() * (Lib.W - 100), 50 + Math.random() * (Lib.H - 100), specs.get(i)));
        }
    }

    void update() {
        int i, j;
                if (tanks.size() > 1) {
            for (i = 0; i < tanks.size() - 1; i++) {
                tank t = tanks.get(i);
                for (j = i + 1; j < tanks.size(); j++) {
                    tank p = tanks.get(j);
                    if (Lib.dist(t.x, t.y, p.x, p.y) < Lib.tankRadius + Lib.tankRadius) {
                        double impactx=(p.x + t.x) / 2;
                        double impacty=(p.y + t.y) / 2;
                        p.x=impactx+(Lib.tankRadius+1)*Math.cos(Lib.vectorToDegree(impactx, impacty, p.x, p.y) * Lib.toRad);
                        p.y=impacty-(Lib.tankRadius+1)*Math.sin(Lib.vectorToDegree(impactx, impacty, p.x, p.y) * Lib.toRad);
                        t.x=impactx+(Lib.tankRadius+1)*Math.cos(Lib.vectorToDegree(impactx, impacty, t.x, t.y) * Lib.toRad);
                        t.y=impacty-(Lib.tankRadius+1)*Math.sin(Lib.vectorToDegree(impactx, impacty, t.x, t.y) * Lib.toRad);
                        
                        p.impactSpeedX = t.currentSpeed * Math.cos(t.direction * Lib.toRad) - p.currentSpeed * Math.cos(p.direction * Lib.toRad)/2;
                        p.impactSpeedY = t.currentSpeed * Math.sin(t.direction * Lib.toRad) + p.currentSpeed * Math.sin(p.direction * Lib.toRad)/2;
                        t.impactSpeedX = p.currentSpeed * Math.cos(p.direction * Lib.toRad) - t.currentSpeed * Math.cos(t.direction * Lib.toRad)/2;
                        t.impactSpeedY = p.currentSpeed * Math.sin(p.direction * Lib.toRad) + t.currentSpeed * Math.sin(t.direction * Lib.toRad)/2;
                        
                        p.currentSpeed=0;
                        t.currentSpeed=0;
                        p.targetSpeed=0;
                        t.targetSpeed=0;
                        
                        flyingText.add(new flyingString(this, "bump", Color.red, impactx, impacty));
                    }
                }
            }
        }


        for (i = 0; i < tanks.size(); i++) {
            tanks.get(i).update();
        }

        for (i = 0; i < bullets.size(); i++) {
            bullets.get(i).update();
        }
        for (i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
        }
        for (i = 0; i < blackHoles.size(); i++) {
            blackHoles.get(i).update();
        }
        for (i = 0; i < deathAnimations.size(); i++) {
            deathAnimations.get(i).update();
        }
        if (Lib.flying){
        for (i = 0; i < flyingText.size(); i++) {
            flyingText.get(i).update();
        }
        }
        for (i = 0; i < deathMessages.size(); i++) {
            deathMessages.get(i).update();
        }
    }

    void draw(Graphics2D g) {
        int i;
        back.draw(g);
     if (Lib.disco){   
        for (i = 0; i < tanks.size(); i++) {
            tank t = tanks.get(i);
            if (t.scanThisFrame) {

                g.setColor(t.graphics.scanColor);
                g.fillPolygon(t.scanPoly);
                g.setColor(t.graphics.scanLineColor);
//                g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, 1f, new float[]{6f, 5f}, 0f));
                g.drawPolygon(t.scanPoly);

                t.scanThisFrame = false;
            }
            if (t.scanXYThisFrame) {

                g.setColor(t.graphics.scanColor);
                g.fillRect((int) t.scanX, -5, (int) t.scanWidth, Lib.H + 10);
                g.fillRect(-5, (int) t.scanY, Lib.W + 10, (int) t.scanWidth);
                g.setColor(t.graphics.scanLineColor);
//                g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, 1f, new float[]{6f, 5f}, 0f));
                g.drawRect((int) t.scanX, -5, (int) t.scanWidth, Lib.H + 10);
                g.drawRect(-5, (int) t.scanY, Lib.W + 10, (int) t.scanWidth);

                t.scanXYThisFrame = false;
            }
        }
    }
        for (i = 0; i < tanks.size(); i++) {
            tanks.get(i).draw(g);
        }
        for (i = 0; i < deathAnimations.size(); i++) {
            deathAnimations.get(i).draw(g);
        }
        for (i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }
        for (i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(g);
        }
        for (i = 0; i < blackHoles.size(); i++) {
            blackHoles.get(i).draw(g);
        }
        if (Lib.flying){
        for (i = 0; i < flyingText.size(); i++) {
            flyingText.get(i).draw(g);
        }
        }
        for (i = 0; i < deathMessages.size(); i++) {
            deathMessages.get(i).draw(g);
        }
    }
}