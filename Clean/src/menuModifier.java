
public class menuModifier implements action{
    int value=0;
    int limit;
    menuModifier(int l){limit = l;}
    
    @Override
    public void go() {
        return;
    }

    @Override
    public void right() {
        if (value!=limit){
            value++;
        }
    }

    @Override
    public void left() {
        if (value!=-limit){
            value--;
        }
    }
    
}
