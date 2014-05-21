
public class horizontalMenuList implements action{
    
    String[] items;
    int choice = 0;
    int size;
    String[] menuList;
    String[] list;
    int link;
    horizontalMenuList(String[] i,String[] j,String[] ll,int l){
        items=i;
    size = items.length;
    menuList=j;
    list=ll;
    link=l;
    }
    
    @Override
    public void go() {
        return;
    }


    @Override
    public void right() {
        choice++;
        choice%=size;
        menuList[link]=list[choice];
    }

    @Override
    public void left() {
        choice--;
        if (choice<0) choice = size-1;
        menuList[link]=list[choice];
    }
    
    public String get(){
    return items[choice];
    }
    
    public String getNext(){
        return items[(choice+1)%size];
    }
    public String getPrev(){
        if (choice==0) return items[size-1];
        return items[choice-1];
    }
    
}
