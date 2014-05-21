
import java.awt.Canvas;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Clean {

    static boolean fullscreen = false;
    static GraphicsEnvironment environment;
    static GraphicsDevice device;
    static DisplayMode dm;
    static DisplayMode dm2;
    static JFrame frame;
    static Canvas gui;
    static battlefield b;
    static long frameCount = 0;
    static long cycleTime;
    static boolean isRunning = true;
    static int FRAME_DELAY = 16;// 20ms implies 50fps (1000/20) = 50
    static BufferStrategy strategy;
    static final Object BufferStrategyMutEx = new Object();
    static menu Menu = Lib.mainMenu;
    static menu TankList;
    static boolean hidden = true;
    static ArrayList<costumizationModule> specs = new ArrayList<costumizationModule>();
    static ArrayList<String> names = new ArrayList<String>();
    
    static boolean tankMoveTest = true;

    static class lol {

        tank t;
        double scandeg = 0.0;
        double scandist = 0.0;
        double scanx = 10.0;
        double scany = 10.0;
        double xyresult;
        boolean lastScan = false;
        boolean secondToLastScan = false;
        boolean lastScan2 = false;
        boolean secondToLastScan2 = false;
        boolean scandir = true;
        boolean scandir2 = true;
        boolean changeddir = false;
        boolean changeddir2 = false;
        boolean lockx = false;
        boolean locky = false;
        boolean scantype;

        lol(tank tt, boolean b) {
            t = tt;
            scantype = b;
        }
    }
    static lol list[] = new lol[400];

    static int find(tank t, lol[] lll, int n) {
        for (int j = 0; j < n; j++) {
            if (lll[j].t == t) {
                return j;
            }
        }
        return -1;
    }

    static void read() {
        FileOutputStream fos;
        ObjectOutputStream oos;
        for (int i = 0; i < names.size(); i++) {
            try {
                fos = new FileOutputStream(names.get(i) + ".menu");
                oos = new ObjectOutputStream(fos);
                oos.writeObject(Lib.mainMenu.nextMenu[1].nextMenu[i]);
                oos.close();
                fos.close();
            } catch (Exception ex) {
            }
        }

        names.clear();
        File f = new File(".");
        FileInputStream fis;
        ObjectInputStream ois;
        File ff[];
        String s;
        if (f.isDirectory()) {
            ff = f.listFiles();
            for (int i = 0; i < ff.length; i++) {
                s = ff[i].getName();
                if (s.length() > 4) {
                    if (s.substring(s.length() - 4).equals(".bot")) {
                        names.add(s.substring(0, s.length() - 4));
                    }
                }
            }
        }

        String n[] = new String[names.size()];
        for (int i = 0; i < n.length; i++) {
            n[i] = names.get(i);
        }

        menu[] cost = new menu[names.size()];
        for (int i = 0; i < cost.length; i++) {
            try {
                fis = new FileInputStream(n[i] + ".menu");
                ois = new ObjectInputStream(fis);
                cost[i] = (menu) ois.readObject();
                ois.close();
                fis.close();
            } catch (Exception ex) {
                cost[i] = menu.costumizationMenu();
            }
        }
        action act[] = new action[names.size()];
        for (int i = 0; i < cost.length; i++) {
            act[i] = null;
        }
        Lib.mainMenu.nextMenu[1] = new menu(n, cost, act);
        for (int i = 0; i < names.size(); i++) {
        }
    }

    static void init() {
        read();
        specs.clear();
        for (int i = 0; i < names.size(); i++) {
            specs.add(new costumizationModule(names.get(i), Lib.mainMenu.nextMenu[1].nextMenu[i]));
        }
        b = new battlefield(specs);
        for (int i = 0; i < specs.size(); i++) {
            list[i] = new lol(b.tanks.get(i), i % 2 == 0);
        }
    }

    static void updateGameState() {
        int i;
        if (tankMoveTest) {
            for (i = 0; i < b.tanks.size(); i++) {
                if (b.tanks.contains(b.tanks.get(i)) && b.tanks.get(i).notmoving()) {
                    b.tanks.get(i).move(Math.random() * 360, 100);
                }
            }
        }

        if (b.tanks.size() > 1) {
            for (i = 0; i < b.tanks.size(); i++) {
                lol data = list[find(b.tanks.get(i), list, names.size())];
                if (data.scantype == true) {

                    data.scandist = data.t.scan(data.scandeg, 3);
                    if (data.scandist != -1) {
                        data.t.cannon(data.scandeg, data.scandist);
                    }

                    if (data.scandist == -1 && data.lastScan && !data.secondToLastScan) {
                        data.scandir = !data.scandir;
                    }

                    if (data.scandist == -1 && data.scandir) {
                        data.scandeg += 3;
                    } else if (data.scandist == -1) {
                        data.scandeg -= 3;
                    }


                    if (data.scandist != -1) {
                        data.secondToLastScan = false;
                        data.lastScan = true;
                    } else {
                        data.secondToLastScan = data.lastScan;
                        data.lastScan = false;
                    }
                } else {
                    data.lockx = false;
                    data.locky = false;
                    data.changeddir = false;
                    data.changeddir2 = false;

                    data.xyresult = data.t.scanXY(data.scanx, data.scany, 20);
                    if (data.xyresult == 3) {
                        data.t.cannonXY(data.scanx, data.scany);
                        data.lockx = true;
                        data.locky = true;
                    } else {

                        if (data.xyresult != 1 && data.lastScan && !data.secondToLastScan) {
                            data.scandir = !data.scandir;
                        }

                        if (data.xyresult != 2 && data.lastScan2 && !data.secondToLastScan2) {
                            data.scandir2 = !data.scandir2;
                        }
                    }


                    if (data.scanx > Lib.W - 10 || data.scanx < 10) {
                        data.scandir = !data.scandir;
                        data.changeddir = true;
                    }
                    if (data.scany > Lib.H - 10 || data.scany < 10) {
                        data.scandir2 = !data.scandir2;
                        data.changeddir2 = true;
                    }

                    if (!data.changeddir) {
                        if (data.xyresult == 1) {
                            data.lockx = true;
                        }
                    }
                    if (!data.changeddir2) {
                        if (data.xyresult == 2) {
                            data.locky = true;
                        }
                    }


                    if (data.scandir && !data.lockx) {
                        data.scanx += 20;
                    } else if (!data.lockx) {
                        data.scanx -= 20;
                    }

                    if (data.scandir2 && !data.locky) {
                        data.scany += 20;
                    } else if (!data.locky) {
                        data.scany -= 20;
                    }

                }
            }
        }


        //b.tanks.get(i).interpretor.ruleazacicli(10);
        b.update();
    }

    static void updateGUI(BufferStrategy strategy) {
        do {
            do {
                Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
                b.draw(g);
                if (!hidden) {
                    Menu.draw(g);
                }
                g.dispose();
            } while (strategy.contentsRestored());
            strategy.show();
        } while (strategy.contentsLost());
    }

    static void synchFramerate() {
        cycleTime = cycleTime + FRAME_DELAY;
        long difference = cycleTime - System.currentTimeMillis();
        if (difference > 0) {
            try {
                Thread.sleep(difference);
            } catch (InterruptedException e) {
                System.out.print(e.toString());
            }
        }
    }

    public static void main(String[] args) {

        environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
        dm = device.getDisplayMode();
        dm2 = new DisplayMode(Lib.W, Lib.H, dm.getBitDepth(), dm.getRefreshRate());

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        gui = new Canvas();
        gui.setFocusable(false);
        gui.setSize(Lib.W, Lib.H);
        frame.getContentPane().add(gui);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        isRunning = true;
        init();

        frame.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() != KeyEvent.VK_SUBTRACT && e.getKeyCode() != KeyEvent.VK_ADD && e.getKeyCode() != KeyEvent.VK_F && e.getKeyCode() != KeyEvent.VK_C && e.getKeyCode() != KeyEvent.VK_D && e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_UP && e.getKeyCode() != KeyEvent.VK_DOWN && e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_RIGHT && e.getKeyCode() != KeyEvent.VK_ESCAPE) {
                    return;
                } else {
                    if (e.getKeyCode() == KeyEvent.VK_ADD && !fullscreen) {
                        if (FRAME_DELAY > 1) {
                            FRAME_DELAY--;
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT && !fullscreen) {
                        FRAME_DELAY++;
                    } else if (e.getKeyCode() == KeyEvent.VK_F) {
                        Lib.flying = !Lib.flying;
                        b.flyingText.clear();
                    } else if (e.getKeyCode() == KeyEvent.VK_C) {
                        Lib.clipping = !Lib.clipping;
                    } else if (e.getKeyCode() == KeyEvent.VK_D) {
                        Lib.disco = !Lib.disco;
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isAltDown()) {
                        synchronized (BufferStrategyMutEx) {
                            if (fullscreen) {
                                fullscreen = false;
                                device.setDisplayMode(dm);
                                frame.setVisible(false);
                                frame.dispose();
                                frame.setUndecorated(false);
                                device.setFullScreenWindow(null);
                                frame.setLocationRelativeTo(null);
                                frame.setVisible(true);
                                strategy.dispose();
                                gui.createBufferStrategy(2);
                                strategy = gui.getBufferStrategy();
                            } else {
                                fullscreen = true;
                                frame.setVisible(false);
                                frame.dispose();
                                frame.setUndecorated(true);
                                device.setFullScreenWindow(frame);
                                device.setDisplayMode(dm2);
                                frame.setVisible(true);
                                strategy.dispose();
                                gui.createBufferStrategy(2);
                                strategy = gui.getBufferStrategy();
                            }
                            //prevent over-sync
                            cycleTime = System.currentTimeMillis();
                        }
                    } else if (hidden && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        hidden = false;
                    } else if (!hidden) {
                        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                            Menu.doRight();
                        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                            Menu.doLeft();
                        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                            Menu.choice++;
                            Menu.choice %= Menu.size;
                        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                            Menu.choice--;
                            if (Menu.choice < 0) {
                                Menu.choice = Menu.size - 1;
                            }
                        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            if (Menu.previousMenu == null) {
                                hidden = true;
                            } else {
                                Menu = Menu.goback();
                            }
                        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            Menu.doAction();
                            Menu = Menu.gotoMenu(Menu.nextMenu[Menu.choice]);
                        }
                    }
                }
            }
        });

        gui.createBufferStrategy(2);
        strategy = gui.getBufferStrategy();
        cycleTime = System.currentTimeMillis();

        // Game Loop
        while (isRunning) {

            if (hidden) {
                updateGameState();
            }

            synchronized (BufferStrategyMutEx) {
                updateGUI(strategy);
            }
            frameCount++;
            if (!fullscreen) {
                synchFramerate();
            }
        }



    }
}