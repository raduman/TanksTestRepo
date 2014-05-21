
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class menu implements Serializable{

    menu previousMenu = null;
    action[] actions;
    String[] items;
    menu[] nextMenu;
    int size;
    int choice = 0;

    menu(String[] i, menu[] m, action[] a) {
        items = i;
        nextMenu = m;
        actions = a;
        size = items.length;
    }

    static menu costumizationMenu(){
        String[] temp=new String[]{null,null,null,null,null, "augment1", "augment2", "augment3", "augment4","augment5"};
    return new menu(temp, new menu[]{null, null, null, null, null, null, null, null,null,null}, new action[]{new horizontalMenuList(Lib.weapons,temp,Lib.weaponModifiers,5), new horizontalMenuList(Lib.armors,temp,Lib.armorModifiers,6), new horizontalMenuList(Lib.turrets,temp,Lib.turretModifiers,7),new horizontalMenuList(Lib.tracks,temp,Lib.trackModifiers,8),new horizontalMenuList(Lib.specials,temp,Lib.specialModifiers,9), new menuModifier(5),new menuModifier(5),new menuModifier(5), new menuModifier(5),new menuModifier(5)});
    }
    
    menu gotoMenu(menu m) {
        if (m == null) {
            return this;
        }
        m.previousMenu = this;
        return m;
    }

    menu goback() {
        if (previousMenu == null) {
            return this;
        }
        return previousMenu;
    }

    void doAction() {
        if (actions[choice] == null) {
            return;
        }
        actions[choice].go();
    }

    void doRight() {
        if (actions[choice] == null) {
            return;
        }
        actions[choice].right();
    }

    void doLeft() {
        if (actions[choice] == null) {
            return;
        }
        actions[choice].left();
    }

    void draw(Graphics2D g) {
        g.setFont(Lib.getFont());
        int i, j, a, b, c;

        for (i = 0; i < size; i++) {

            if (actions[i] instanceof horizontalMenuList) {
                horizontalMenuList container = (horizontalMenuList) actions[i];
                String s = container.get();
                int halfString = Lib.strLength(s) / 2;
                a = Lib.W / 2 - halfString;
                b = Lib.H / 2 - (size * (Lib.charHeight + Lib.charSpacer)) / 2 + i * (Lib.charHeight + Lib.charSpacer);
                if (choice != i) {
                    g.setColor(Lib.menuColor);
                    g.fillRect(a - 2 - Lib.charSpacer / 2, b - Lib.charHeight + 2 - Lib.charSpacer / 2, halfString + halfString + Lib.charSpacer, Lib.charHeight + Lib.charSpacer);
                    g.setColor(Lib.textColor);
                    g.drawString(s, a, b);
                } else {
                    g.setColor(Lib.menuHighlight);
                    g.fillRect(a - 2 - Lib.charSpacer / 2, b - Lib.charHeight + 2 - Lib.charSpacer / 2, halfString + halfString + Lib.charSpacer, Lib.charHeight + Lib.charSpacer);
                    g.setColor(Lib.textHighlight);
                    g.drawString(s, a, b);

                    s = container.getPrev() + " < ";
                    c = Lib.strLength(s);
                    a = Lib.W / 2 - c - halfString;
                    b = Lib.H / 2 - (size * (Lib.charHeight + Lib.charSpacer)) / 2 + i * (Lib.charHeight + Lib.charSpacer);
                    g.setColor(Lib.menuHighlight);
                    g.fillRect(a - 2 - Lib.charSpacer / 2, b - Lib.charHeight + 2 - Lib.charSpacer / 2, c, Lib.charHeight + Lib.charSpacer);
                    g.setColor(Lib.textHighlight);
                    g.drawString(s, a, b);

                    s = " > " + container.getNext();
                    c = Lib.strLength(s);
                    a = Lib.W / 2 + halfString;
                    b = Lib.H / 2 - (size * (Lib.charHeight + Lib.charSpacer)) / 2 + i * (Lib.charHeight + Lib.charSpacer);
                    g.setColor(Lib.menuHighlight);
                    g.fillRect(a - 2 + Lib.charSpacer / 2, b - Lib.charHeight + 2 - Lib.charSpacer / 2, c - Lib.charSpacer - 2, Lib.charHeight + Lib.charSpacer);
                    g.setColor(Lib.textHighlight);
                    g.drawString(s, a, b);
                }


            } else if (actions[i] instanceof menuModifier) {
                menuModifier container = (menuModifier) actions[i];

                a = Lib.W / 2 - Lib.strLength(items[i])/2;
                b = Lib.H / 2 - (size * (Lib.charHeight + Lib.charSpacer)) / 2 + i * (Lib.charHeight + Lib.charSpacer);
                if (choice != i) {
                    g.setColor(Lib.menuColor);
                } else {
                    g.setColor(Lib.menuHighlight);
                }
                g.fillRect(a - 2 - Lib.charSpacer / 2, b - Lib.charHeight + 2 - Lib.charSpacer / 2, Lib.strLength(items[i]) + Lib.charSpacer, Lib.charHeight + Lib.charSpacer);
                if (choice != i) {
                    g.setColor(Lib.textColor);
                } else {
                    g.setColor(Lib.textHighlight);
                }
                g.drawString(items[i], a, b);

                if (container.value != 0) {
                    String s = "";
                    for (j = 0; j < Math.abs(container.value); j++) {
                        s += "i";
                    }
                    if (container.value < 0) {
                        a = Lib.W / 2 - Lib.strLength(items[i])/2 + container.value * Lib.iWidth - Lib.charWidth;
                        b = Lib.H / 2 - (size * (Lib.charHeight + Lib.charSpacer)) / 2 + i * (Lib.charHeight + Lib.charSpacer);
                        if (choice != i) {
                            g.setColor(Lib.menuColor);
                        } else {
                            g.setColor(Lib.menuHighlight);
                        }
                        g.fillRect(a - 2 - Lib.charSpacer / 2, b - Lib.charHeight + 2 - Lib.charSpacer / 2, -container.value * Lib.iWidth + Lib.charSpacer, Lib.charHeight + Lib.charSpacer);
                        g.setColor(new Color(255, 50, 50));
                        g.drawString(s, a, b);
                    } else {
                        a = Lib.W / 2 + Lib.strLength(items[i])/2 + Lib.charWidth;
                        b = Lib.H / 2 - (size * (Lib.charHeight + Lib.charSpacer)) / 2 + i * (Lib.charHeight + Lib.charSpacer);
                        if (choice != i) {
                            g.setColor(Lib.menuColor);
                        } else {
                            g.setColor(Lib.menuHighlight);
                        }
                        g.fillRect(a - 2 - Lib.charSpacer / 2, b - Lib.charHeight + 2 - Lib.charSpacer / 2, container.value * Lib.iWidth + Lib.charSpacer, Lib.charHeight + Lib.charSpacer);
                        g.setColor(new Color(50, 255, 50));
                        g.drawString(s, a, b);
                    }
                }
            } else {
                if (choice != i) {
                    a = Lib.W / 2 - Lib.strLength(items[i])/2;
                    b = Lib.H / 2 - (size * (Lib.charHeight + Lib.charSpacer)) / 2 + i * (Lib.charHeight + Lib.charSpacer);
                    g.setColor(Lib.menuColor);
                    g.fillRect(a - 2 - Lib.charSpacer / 2, b - Lib.charHeight + 2 - Lib.charSpacer / 2, Lib.strLength(items[i]) + Lib.charSpacer, Lib.charHeight + Lib.charSpacer);
                    g.setColor(Lib.textColor);
                    g.drawString(items[i], a, b);
                } else {
                    a = Lib.W / 2 - Lib.strLength(items[i])/2 - Lib.ltWidth - Lib.charWidth;
                    b = Lib.H / 2 - (size * (Lib.charHeight + Lib.charSpacer)) / 2 + i * (Lib.charHeight + Lib.charSpacer);
                    g.setColor(Lib.menuHighlight);
                    g.fillRect(a - 2 - Lib.charSpacer / 2, b - Lib.charHeight + 2 - Lib.charSpacer / 2, Lib.strLength(items[i]) + Lib.ltWidth + Lib.ltWidth + Lib.charWidth + Lib.charWidth + Lib.charSpacer, Lib.charHeight + Lib.charSpacer);
                    g.setColor(Lib.textHighlight);
                    g.drawString("> " + items[i] + " <", a, b);
                }
            }
        }
    }
}
