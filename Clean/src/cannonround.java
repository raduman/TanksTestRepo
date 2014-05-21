
import java.awt.Graphics2D;

public class cannonround implements unit {

    battlefield context;
    tank owner;
    double angle;
    double speedx;
    double speedy;
    double x;
    double y;
    double targetx;
    double targety;
    double animation = 0;
    double step = 0.2;
    double damage;
    double explosionRadius = 20.0;
    int steps;
    spritesheet sprites;
    
    cannonround(battlefield bf,spritesheet s, tank o, double originx, double originy, double xxx, double yyy, double dd, double speed) {
        context = bf;
        owner = o;
        x = originx;
        y = originy;
        targetx = xxx;
        targety = yyy;
        double d = Lib.dist(x, y, targetx, targety);
        speedx = speed * ((xxx - x) / d);
        speedy = speed * ((y - yyy) / d);
        steps = (int) (d / speed);
        damage=dd;
        sprites=s;
    }

    @Override
    public void update() {
        if (steps == 0) {
            context.bullets.remove(this);
            if (owner.w instanceof voidCannon){
            context.explosions.add(new blackHoleExplosion(context, targetx, targety));
            } else {
            context.explosions.add(new explosion(context, targetx, targety));
            }
            int i;
            tank j;
            double distance;
            double maxdist;
            for (i = 0; i < context.tanks.size(); i++) {
                j = context.tanks.get(i);
                distance = Lib.dist(j.x, j.y, targetx, targety);
                maxdist = Lib.tankRadius + explosionRadius;
                if (distance < maxdist) {
                    j.hitThisFrame = true;
                    double actualdamage=((maxdist - distance) / maxdist) * damage;
                    context.flyingText.add(new flyingDamage(context,(int)actualdamage,j.x,j.y));
                    j.hp -= actualdamage;
                    if (j.hp<0){
                        context.deathMessages.add(new deathMessage(context,Lib.begin[(int)(Math.random()*Lib.begin.length)],j.name,Lib.mid[(int)(Math.random()*Lib.mid.length)],owner.name,j.graphics.color,owner.graphics.color,j.x,j.y));
                    j.die();
                    }
                    
                }
            }

        }
        x += speedx;
        y -= speedy;
        animation += step;
        if (animation >= 5.0) {
            animation = 0.0;
        }
        steps--;
    }

    @Override
    public void draw(Graphics2D g) {
        int X = (int) x - Lib.cannonspritesized2;
        int Y = (int) y - Lib.cannonspritesized2;
        g.drawImage(sprites.get((int) animation, 0), X, Y, null);
        
        if (Lib.clipping){
            g.setColor(owner.graphics.color);
            g.drawOval((int)(targetx-explosionRadius), (int)(targety-explosionRadius), (int)(2*explosionRadius), (int)(2*explosionRadius));
        }
        
    }
}
