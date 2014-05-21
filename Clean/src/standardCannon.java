
public class standardCannon implements weapon{
    double damage;
    double projSpeed;
    
    standardCannon(double d, double p){
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
