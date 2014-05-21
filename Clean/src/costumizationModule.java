
public class costumizationModule {

    private static final long serialVersionUID = 1L;
    String name;
    double hp = Lib.hp;
    double maxhp = Lib.maxhp;
    double maxspeed = Lib.maxspeed;
    double speedincrement = Lib.speedincrement;
    double rotationincrement = Lib.rotationincrement;
    double turretrotationincrement = Lib.turretrotationincrement;
    double maxheat = Lib.maxheat;
    double heatincrement = Lib.heatincrement;
    double heatpershot = Lib.heatpershot;
    double maxcooldown = Lib.maxcooldown;
    double cooldownincrement = Lib.cooldownincrement;
    weapon w;
    
    costumizationModule(String s, menu m) {
        name = s;
        horizontalMenuList container = (horizontalMenuList) m.actions[0];
        
        if (container.items[container.choice].equals("cannon")) {
            w = new standardCannon(10, 12);
        } else if (container.items[container.choice].equals("scatter")) {
            w = new scatterCannon(8, 12 , 8);
            heatincrement=0.8;
        } else if (container.items[container.choice].equals("void")){
            w = new voidCannon(5, 12);
            heatincrement=0.7;
            heatpershot=maxheat;
        }
    }
}
