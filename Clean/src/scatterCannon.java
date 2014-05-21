
public class scatterCannon implements weapon{
    double damage;
    double projSpeed;
    int projectileLimit;
    int storedProjectile=0;
    
    scatterCannon(double d, double p,int i){
    damage =d;
    projSpeed=p;
    projectileLimit=i;
    }
    
    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public double getProjSpeed() {
        return projSpeed;
    }
    
    double getRadius(){
        return 30+7*storedProjectile;
    }
}
