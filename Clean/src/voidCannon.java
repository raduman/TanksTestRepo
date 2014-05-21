
public class voidCannon implements weapon{
        double damage;
    double projSpeed;
    
    voidCannon(double d, double p){
    damage =d;
    projSpeed=p;
    }
    
    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public double getProjSpeed() {
        return projSpeed;
    }
}
