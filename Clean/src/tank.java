
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class tank implements unit {

    String name;
    battlefield context;
    tankGraphics graphics;
    hpbar health;
    heatbar gauge;
    double direction = 0.0;
    double targetDirection = 0.0;
    double rotationIncrement;
    double turretDirection = 0.0;
    double turretRotationIncrement;
    double targetSpeed = 0.0;
    double currentSpeed = 0.0;
    double hp;
    double maxHp;
    double heat = 0.0;
    double heatIncrement;
    double cooldown = 0.0;
    double maxCooldown;
    double cooldownIncrement;
    double maxHeat;
    double heatPerShot;
    double x;
    double y;
    double maxSpeed;
    double speedIncrement;
    double moveAnimation = 0.0;
    double shotDirection = 0.0;
    double shotX = 0.0;
    double shotY = 0.0;
    double scanDegree = 0.0;
    double scanSpan = 0.0;
    double scanX = 0.0;
    double scanY = 0.0;
    double scanWidth = 0.0;
    int shotCount = 0;
    boolean hitThisFrame = false;
    boolean scanThisFrame = false;
    boolean scanXYThisFrame = false;
    boolean shotOrder = false;
    Polygon scanPoly;
    double testX;
    double testY;
    double impactSpeedX = 0.0;
    double impactSpeedY = 0.0;
    weapon w;

    public tank(battlefield bb, tankGraphics tg, double pozx, double pozy, costumizationModule specifications) {

        hp = specifications.hp;
        maxHp = specifications.maxhp;
        maxSpeed = specifications.maxspeed;
        speedIncrement = specifications.speedincrement;
        rotationIncrement = specifications.rotationincrement;
        turretRotationIncrement = specifications.turretrotationincrement;
        maxHeat = specifications.maxheat;
        heatIncrement = specifications.heatincrement;
        heatPerShot = specifications.heatpershot;
        cooldownIncrement = specifications.cooldownincrement;
        maxCooldown = specifications.maxcooldown;
        w = specifications.w;
        name = specifications.name;

        context = bb;
        x = pozx;
        y = pozy;
        health = new hpbar(this);
        gauge = new heatbar(this);

        graphics = tg;
    }

    public tank(battlefield bb, int i, double pozx, double pozy, costumizationModule specifications) {

        hp = specifications.hp;
        maxHp = specifications.maxhp;
        maxSpeed = specifications.maxspeed;
        speedIncrement = specifications.speedincrement;
        rotationIncrement = specifications.rotationincrement;
        turretRotationIncrement = specifications.turretrotationincrement;
        maxHeat = specifications.maxheat;
        heatIncrement = specifications.heatincrement;
        heatPerShot = specifications.heatpershot;
        cooldownIncrement = specifications.cooldownincrement;
        maxCooldown = specifications.maxcooldown;
        w = specifications.w;
        name = specifications.name;

        context = bb;
        x = pozx;
        y = pozy;
        health = new hpbar(this);
        gauge = new heatbar(this);

        graphics = Lib.FLAVORS[i % Lib.FLAVORS.length];
    }

    void die() {
        context.tanks.remove(this);
        context.deathAnimations.add(new carcass(this));
    }

    boolean canshoot() {
        if (cooldown == 0.0 && (maxHeat - heat) >= heatPerShot && shotOrder == false) {
            return true;
        }
        return false;
    }

    void cannon(double degree, double range) {
        if (canshoot()) {
            degree = Lib.normalizeAngle(degree);
            shotOrder = true;

            shotX = x + range * Math.cos(degree * Lib.toRad);
            shotY = y - range * Math.sin(degree * Lib.toRad);

            shotDirection = Lib.vectorToDegree(x - Lib.spritesized2 + Lib.turretXbindpoints[Lib.directionToSprite16(direction)] + Lib.turretspritesized2, y - Lib.spritesized2 + Lib.turretYbindpoints[Lib.directionToSprite16(direction)] + Lib.turretspritesized2, shotX, shotY);

        }
    }

    double scan(double degree, double angle) {
        degree = Lib.normalizeAngle(degree);
        angle = Lib.normalizeAngle(angle);

        scanThisFrame = true;
        scanDegree = Lib.normalizeAngle(degree - angle / 2);
        scanSpan = angle;

        double minDist = Lib.ipotenuza;
        double d;
        boolean found = false;
        for (int i = 0; i < context.tanks.size(); i++) {
            if (i != context.tanks.indexOf(this)) {
                if (Lib.angleDifference(Lib.vectorToDegree(x, y, context.tanks.get(i).x, context.tanks.get(i).y), degree) < angle / 2) {
                    d = Lib.dist(x, y, context.tanks.get(i).x, context.tanks.get(i).y);
                    if (d < minDist) {
                        found = true;
                        minDist = d;
                    }
                }
            }
        }
        if (found) {
            return minDist;
        }
        return -1;
    }

    double scanXY(double X, double Y, double width) {
        double widthd2 = width / 2;

        scanXYThisFrame = true;
        scanX = X - widthd2;
        scanY = Y - widthd2;
        scanWidth = width;

        for (int i = 0; i < context.tanks.size(); i++) {
            if (i != context.tanks.indexOf(this)) {
                if (context.tanks.get(i).x > X - widthd2 && context.tanks.get(i).x < X + widthd2 && context.tanks.get(i).y > Y - widthd2 && context.tanks.get(i).y < Y + widthd2) {
                    return 3;
                } else if (context.tanks.get(i).x > X - widthd2 && context.tanks.get(i).x < X + widthd2) {
                    return 1;
                } else if (context.tanks.get(i).y > Y - widthd2 && context.tanks.get(i).y < Y + widthd2) {
                    return 2;
                }
            }
        }



        return 0;
    }

    void cannonXY(double X, double Y) {
        if (canshoot()) {
            shotX = X;
            shotY = Y;
            shotOrder = true;
            shotDirection = Lib.vectorToDegree(x - Lib.spritesized2 + Lib.turretXbindpoints[Lib.directionToSprite16(direction)] + Lib.turretspritesized2, y - Lib.spritesized2 + Lib.turretYbindpoints[Lib.directionToSprite16(direction)] + Lib.turretspritesized2, X, Y);
        }
    }

    void move(double degree, double speed) {
        degree = Lib.normalizeAngle(degree);
        if (speed < 0.0) {
            speed = 0.0;
        }
        if (speed > 100.0) {
            speed = 100.0;
        }
        targetSpeed = maxSpeed * (speed / 100.0);
        targetDirection = degree;
    }

    void stop() {
        targetSpeed = 0.0;
    }

    void moveXY(double X, double Y, double speed) {
    }

    boolean notmoving() {
        if (currentSpeed == 0.0 && targetSpeed == 0.0) {
            return true;
        }
        return false;
    }

    boolean moving() {
        if (currentSpeed != 0.0 || targetSpeed != 0.0) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        health.update();
        gauge.update();
        // adjust move animation
        if (currentSpeed != 0) {
            moveAnimation += currentSpeed / maxSpeed * 0.2;
            moveAnimation %= 3;
        }

        //adjust cooldown
        if (cooldown > 0) {
            cooldown -= cooldownIncrement;
        }
        if (cooldown < 0) {
            cooldown = 0.0;
        }
        //adjust heat
        if (heat > 0) {
            heat -= heatIncrement;
        }
        if (heat < 0) {
            heat = 0.0;
        }

        if (heat == 0.0 && w instanceof scatterCannon) {
            scatterCannon container = (scatterCannon) w;
            if (container.storedProjectile < container.projectileLimit) {
                heat += heatPerShot;
                container.storedProjectile++;
            }
        }

        // adjust turret or shoot

        if (shotOrder) {
            if (turretDirection == shotDirection) {
                if (w instanceof scatterCannon) {
                    double XX = x - Lib.spritesized2 + Lib.cannonspritesized2 + Lib.turretXbindpoints[Lib.directionToSprite16(direction)] + Lib.projXbindpoints[Lib.directionToSprite16(turretDirection)];
                    double YY = y - Lib.spritesized2 + Lib.cannonspritesized2 + Lib.turretYbindpoints[Lib.directionToSprite16(direction)] + Lib.projYbindpoints[Lib.directionToSprite16(turretDirection)];
                    context.bullets.add(new cannonround(context,Lib.cannonsprites, this, XX, YY, shotX, shotY, w.getDamage(), w.getProjSpeed()));
                    scatterCannon container = (scatterCannon) w;
                    for (int i = 0; i < container.storedProjectile; i++) {
                        context.bullets.add(new cannonround(context,Lib.cannonsprites, this, XX, YY, shotX + Math.random() * container.getRadius() * Math.cos(Math.random() * 360 * Lib.toRad), shotY - Math.random() * container.getRadius() * Math.cos(Math.random() * 360 * Lib.toRad), w.getDamage(), w.getProjSpeed()));
                        shotCount++;
                    }
                    container.storedProjectile = 0;
                } else if (w instanceof voidCannon) {
                    double XX = x - Lib.spritesized2 + Lib.cannonspritesized2 + Lib.turretXbindpoints[Lib.directionToSprite16(direction)] + Lib.projXbindpoints[Lib.directionToSprite16(turretDirection)];
                    double YY = y - Lib.spritesized2 + Lib.cannonspritesized2 + Lib.turretYbindpoints[Lib.directionToSprite16(direction)] + Lib.projYbindpoints[Lib.directionToSprite16(turretDirection)];
                    context.bullets.add(new cannonround(context,Lib.voidcannonsprites, this, XX, YY, shotX, shotY, w.getDamage(), w.getProjSpeed()));

                } else {
                    double XX = x - Lib.spritesized2 + Lib.cannonspritesized2 + Lib.turretXbindpoints[Lib.directionToSprite16(direction)] + Lib.projXbindpoints[Lib.directionToSprite16(turretDirection)];
                    double YY = y - Lib.spritesized2 + Lib.cannonspritesized2 + Lib.turretYbindpoints[Lib.directionToSprite16(direction)] + Lib.projYbindpoints[Lib.directionToSprite16(turretDirection)];
                    context.bullets.add(new cannonround(context,Lib.cannonsprites, this, XX, YY, shotX, shotY, w.getDamage(), w.getProjSpeed()));

                }
                
                shotCount++;
                cooldown = maxCooldown;
                heat += heatPerShot;
                shotOrder = false;
            } else if (Lib.angleDifference(shotDirection, turretDirection) <= turretRotationIncrement) {
                turretDirection = shotDirection;
            } else {
                // trebuie sa rotesc tunul cat mai scurt stanga sau dreapta
                if (Lib.angleCase(shotDirection, turretDirection)) {
                    //catre stanga, sens antiorar
                    turretDirection += turretRotationIncrement;
                    if (turretDirection > 360.0) {
                        turretDirection -= 360.0;
                    }
                } else {
                    //catre dreapta, sens orar
                    turretDirection -= turretRotationIncrement;
                    if (turretDirection < 0.0) {
                        turretDirection += 360.0;
                    }
                }
            }
        }


        //adjust speed and direction
        if ((currentSpeed != targetSpeed) || (targetDirection != direction)) {
            // sunt pe directia care trebuie? (sau poate doar vreau sa ma opresc?)
            if (targetDirection == direction || targetSpeed == 0.0) {
                //sunt pe directia care trebuie, adjustez doar viteza
                if (Math.abs(currentSpeed - targetSpeed) <= speedIncrement) {
                    //am setat viteza la fix
                    currentSpeed = targetSpeed;
                } else {
                    // setez viteza in functie de faptul daca accelerez sau incetinesc
                    if (targetSpeed - currentSpeed > 0) {
                        currentSpeed += speedIncrement;
                    } else {
                        currentSpeed -= speedIncrement;
                    }
                }
            } else {
                //nu sunt pe directia care trebuie, vad daca pot modifica directia
                if (currentSpeed > maxSpeed / 2) {
                    // ma misc pre repede ca sa pot ajusta directia
                    if (Math.abs(currentSpeed - maxSpeed / 2) <= speedIncrement) {
                        // setez viteza la fix
                        currentSpeed = maxSpeed / 2;
                    } else {
                        //incetinesc cat pot
                        currentSpeed -= speedIncrement;
                    }

                } else {
                    // ma misc suficient de incet, pot sa rotesc tancul
                    if (Lib.angleDifference(targetDirection, direction) <= rotationIncrement) {
                        // pot sa setez directia la fix
                        direction = targetDirection;
                    } else {
                        // trebuie sa rotesc tancul cat mai scurt stanga sau dreapta
                        if (Lib.angleCase(targetDirection, direction)) {
                            //catre stanga, sens antiorar
                            direction += rotationIncrement;
                            if (direction > 360.0) {
                                direction -= 360.0;
                            }
                            //turela se roteste odata cu tancul
                            //turretDirection += rotationIncrement;
                            //if (turretDirection > 360.0) {
                            //    turretDirection -= 360.0;
                            //}
                        } else {
                            //catre dreapta, sens orar
                            direction -= rotationIncrement;
                            if (direction < 0.0) {
                                direction += 360.0;
                            }
                            //turela se roteste odata cu tancul
                            //turretDirection -= rotationIncrement;
                            //if (turretDirection < 0.0) {
                            //    turretDirection += 360.0;
                            //}
                        }
                    }
                }
            }
        }


        x += impactSpeedX + currentSpeed * Math.cos(direction * Lib.toRad);
        y -= impactSpeedY + currentSpeed * Math.sin(direction * Lib.toRad);

        if (impactSpeedX != 0) {
            if (impactSpeedX < Lib.friction) {
                impactSpeedX = 0;
            } else {
                impactSpeedX -= Lib.friction;
            }
        }

        if (impactSpeedY != 0) {
            if (impactSpeedY < Lib.friction) {
                impactSpeedY = 0;
            } else {
                impactSpeedY -= Lib.friction;
            }
        }


        if (x > Lib.W - Lib.tankRadius) {
            x = Lib.W - Lib.tankRadius;
            impactSpeedX = 0;
            currentSpeed = 0.0;
            targetSpeed = 0.0;
        }
        if (x < Lib.tankRadius) {
            x = Lib.tankRadius;
            impactSpeedX = 0;
            currentSpeed = 0.0;
            targetSpeed = 0.0;
        }

        if (y > Lib.H - Lib.tankRadius) {
            y = Lib.H - Lib.tankRadius;
            impactSpeedY = 0;
            currentSpeed = 0.0;
            targetSpeed = 0.0;
        }
        if (y < Lib.tankRadius) {
            y = Lib.tankRadius;
            impactSpeedY = 0;
            currentSpeed = 0.0;
            targetSpeed = 0.0;
        }

        if (scanThisFrame) {
            int detail = 5;
            int xpoints[] = new int[detail + 1];
            int ypoints[] = new int[detail + 1];

            for (int j = 0; j < detail; j++) {
                xpoints[j] = (int) x + (int) (Lib.ipotenuza * Math.cos((scanDegree + j * scanSpan / (detail - 1)) * Lib.toRad));
                ypoints[j] = (int) y - (int) (Lib.ipotenuza * Math.sin((scanDegree + j * scanSpan / (detail - 1)) * Lib.toRad));
            }
            xpoints[detail] = (int) x;
            ypoints[detail] = (int) y;

            scanPoly = new Polygon(xpoints, ypoints, detail + 1);
        }

    }

    @Override
    public void draw(Graphics2D g) {
        int X = (int) x - Lib.spritesized2;
        int Y = (int) y - Lib.spritesized2;
        g.drawImage(graphics.shadowSprites.get(Lib.directionToSprite16(direction), 0), X, Y, null);
        if (hitThisFrame) {
            g.drawImage(Lib.hitsprites.get(Lib.directionToSprite16(direction), (int) moveAnimation), X, Y, null);
            g.drawImage(Lib.hitturretsprites.get(0, Lib.directionToSprite16(turretDirection)), X + Lib.turretXbindpoints[Lib.directionToSprite16(direction)], Y + Lib.turretYbindpoints[Lib.directionToSprite16(direction)], null);
            hitThisFrame = false;
        } else {
            g.drawImage(graphics.tankSprites.get(Lib.directionToSprite16(direction), (int) moveAnimation), X, Y, null);
            g.drawImage(graphics.turretSprites.get(0, Lib.directionToSprite16(turretDirection)), X + Lib.turretXbindpoints[Lib.directionToSprite16(direction)], Y + Lib.turretYbindpoints[Lib.directionToSprite16(direction)], null);
        }
        //for test purposes
        if (Lib.clipping) {
            g.setColor(Color.white);
            g.drawOval((int) x - Lib.tankRadius, (int) y - Lib.tankRadius, 2 * Lib.tankRadius, 2 * Lib.tankRadius);
        }
        g.drawImage(gauge.bi, (int) x - Lib.spritesized2 / 2, (int) y - Lib.spritesized2 / 2 - 6, null);
        g.drawImage(health.bi, (int) x - Lib.spritesized2 / 2, (int) y - Lib.spritesized2 / 2 - 11, null);
        g.setFont(Lib.getTinyFont());
        g.setColor(Color.GREEN);
        g.drawString(Integer.toString((int) hp), (int) x - Lib.spritesized2 / 2, (int) y - Lib.spritesized2 / 2 - 12);
        g.setColor(Color.CYAN);
        g.drawString(Integer.toString(shotCount), (int) x + Lib.spritesized2 / 2 - Lib.strLength(Integer.toString(shotCount)) / 4 + 2, (int) y - Lib.spritesized2 / 2 - 12);
        g.setFont(Lib.getSmallFont());
        g.setColor(Color.white);
        g.drawString(name, (int) x - Lib.strLength(name) / 4, (int) y + Lib.spritesized2 / 2 + 8);
    }
}
