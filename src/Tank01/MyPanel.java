package Tank01;
//嵌入式

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;
import java.util.Vector;

//坦克大战绘图区域
//监听键盘事件，实现keyListener
//为了不停重绘子弹需要将MyPanel实现Runnable，当场一个线程
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    MyTank myTank = null;
    //定义敌人坦克放入Vector集合
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node的集合，恢复敌人坦克
    Vector<Node> nodes = new Vector<>();

    int enemyTankSize = 3;
    //定义一个Vector存放炸弹,当子弹击中坦克就加入一个Bomb
    Vector<Bomb> bombs = new Vector<>();
    //定义三张炸弹图片显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    Image image4 = null;
    Image image5 = null;

    //障碍物集合
    Vector<ViolableBarrier> barriers = new Vector<>();
    Vector<MyTank> myTanks = new Vector<>();

    public MyPanel(String key) {
        //判断记录文件是否存在
        //如果存在就正常执行，如果不存在提示只能开启新的游戏,把key设置成1
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankNum();
        } else {
            System.out.println("文件不存在，只能开启新的游戏");
            key = "1";
        }
        myTank = new MyTank(100, 100, 5);//初始化自己的坦克
        myTanks.add(myTank);
        //根据用户输入开始游戏
        switch (key) {
            //初始化敌人坦克
            case "1":
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0, 3);
                    enemyTank.setDirect(2);
                    enemyTanks.add(enemyTank);
                    new Thread(enemyTank).start();
                    //把敌人坦克集合给每一个敌人坦克
                    enemyTank.setEnemyTanks(enemyTanks);
                    //把坦克集合给数据记录类
                    Recorder.setEnemyTanks(enemyTanks);

                }
                break;
            case "2":
                //继续上局游戏
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY(), node.getDirect());
                    enemyTank.setDirect(node.getDirect());
                    enemyTanks.add(enemyTank);
                    new Thread(enemyTank).start();
                    //把敌人坦克集合给每一个敌人坦克
                    enemyTank.setEnemyTanks(enemyTanks);
                    //把坦克集合给数据记录类
                    Recorder.setEnemyTanks(enemyTanks);

                }
                break;
            default:
                System.out.println("输入有误");
        }


        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/sxc/孙笑川-5.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/sxc/孙笑川-4.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/sxc/孙笑川-3.png"));
        image4 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/sxc/孙笑川-2.png"));
        image5 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/sxc/孙笑川-5.png"));

        //初始化障碍物
        addBarrier(barriers, 0);
        addBarrier(barriers, 100);
        addBarrier(barriers, 200);
        addBarrier(barriers, 300);
        addBarrier(barriers, 400);
        addBarrier(barriers, 500);
        //播放音乐
        new AePlayWave("D:\\系统文件\\桌面\\P\\黑马\\Code\\xiaqizi\\src\\music\\SoundTest.wav").start();
    }

    //画子弹方法
    public void drawEnemyTankShot(Graphics g, EnemyTank enemyTank) {
        for (int j = 0; j < enemyTank.shots.size(); j++) {
            //取出子弹
            Shot shot = enemyTank.shots.get(j);
            if (shot.isLive) {
                g.fill3DRect(shot.x, shot.y, 3, 3, false);
            } else {
                //从Vector移除子弹
                enemyTank.shots.remove(shot);
            }
        }
    }

    //获得5的随机倍数方法
    public int[] int5() {
        Random r = new Random();
        while (true) {
            int x1 = r.nextInt(1800);
            int y1 = r.nextInt(1100);
            if (x1 % 5 == 0 && y1 % 5 == 0) {
                return new int[]{x1, y1};
            }
        }
    }

    //随机给障碍物集合加入障碍物方法
    public void addBarrier(Vector<ViolableBarrier> barriers, int p) {//p第一次赋值是0，后面根据每次想要添加障碍物的数量赋值
        Random r = new Random();
        //,给障碍物一个初始坐标，并记录障碍物的当前坐标
        int[] xy = int5();
        int x = xy[0], y = xy[1];
        barriers.add(new ViolableBarrier(x, y, true));
        int ra = 100 + p;
        for (int i = p; i < ra; i++) {
            //获取0-3的随机数
            int t = r.nextInt(4);
            //如果是0且y坐标大于障碍物的size就向上添加障碍物
            if (t == 0 && y > 10) {
                boolean isAdd = false;
//                遍历障碍物集合，如果y坐标重复就设置为已添加
                for (ViolableBarrier barrier : barriers) {
                    if (barrier.getY() + 10 == y) {
                        isAdd = true;
                        break;
                    }
                }
                //如果是否添加为false，new 一个barrier对象添加到集合
                if (!isAdd) {
                    barriers.add(new ViolableBarrier(x, y - 10, true));
                    //添加完成后当前障碍物坐标更新
                    y -= 10;
                }
                //下同,向右添加障碍物
            } else if (t == 1 && x < 1790) {
                boolean isAdd = false;
                for (ViolableBarrier barrier : barriers) {
                    if (barrier.getX() - 10 == x) {
                        isAdd = true;
                        break;
                    }
                }
                if (!isAdd) {
                    barriers.add(new ViolableBarrier(x + 10, y, true));
                    x += 10;
                }
                //向下添加障碍物
            } else if (t == 2 && y < 1090) {
                boolean isAdd = false;
                for (ViolableBarrier barrier : barriers) {
                    if (barrier.getY() - 10 == y) {
                        isAdd = true;
                        break;
                    }
                }
                if (!isAdd) {
                    barriers.add(new ViolableBarrier(x, y + 10, true));
                    y += 10;
                }
                //向左添加障碍物
            } else if (t == 3 && x > 10) {
                boolean isAdd = false;
                for (ViolableBarrier barrier : barriers) {
                    if (barrier.getX() + 10 == x) {
                        isAdd = true;
                        break;
                    }
                }
                if (!isAdd) {
                    barriers.add(new ViolableBarrier(x - 10, y, true));
                    x -= 10;
                }
            }


//                switch (r.nextInt(4)) {
//                    case 0:
//                        for (int i1 = 0; i1 < barriers.size(); i1++) {
//                            if (barriers.get(i).getY()  == y - 10) {
//                                break;
//                            }
//                        }
//                        barriers.add(new ViolableBarrier(x, y - 10, true));
//                    case 1:
//                        for (int i1 = 0; i1 < barriers.size(); i1++) {
//                            if (barriers.get(i).getX() == y + 10) {
//                                break;
//                            }
//                        }
//                    case 2:
//                        barriers.add(new ViolableBarrier(x + 10, y));
//                    case 3:
//                        barriers.add(new ViolableBarrier(x + 10, y));
//                }

        }
    }

    //判断障坦克是否触碰到障碍物方法
    public static boolean isTouch(Vector<? extends Tank> tanks, Vector<ViolableBarrier> barriers) {
        if (!barriers.isEmpty() && !tanks.isEmpty()) {
            for (int i = 0; i < tanks.size(); i++) {
                Tank tank = tanks.get(i);
                switch (tank.getDirect()) {
                    case 0:
                        for (int i1 = 0; i1 < barriers.size(); i1++) {
                            if (barriers.get(i1).getY() + 11 > tank.getY() && barriers.get(i1).getY() < tank.getY() + 56
                                    && barriers.get(i1).getX() + 10 > tank.getX() && barriers.get(i1).getX() < tank.getX() + 41) {
                                return false;
                            }
                        }
                        break;
                    case 1:
                        for (int i1 = 0; i1 < barriers.size(); i1++) {
                            if (barriers.get(i1).getX() < tank.getX() + 52 && barriers.get(i1).getX() + 10 > tank.getX() - 5
                                    && barriers.get(i1).getY() + 10 > tank.getY() + 10 && barriers.get(i1).getY() < tank.getY() + 51) {
                                return false;
                            }
                        }
                        break;
                    case 2:
                        for (int i1 = 0; i1 < barriers.size(); i1++) {
                            if (barriers.get(i1).getY() < tank.getY() + 61 && barriers.get(i1).getY() + 10 > tank.getY() + 5
                                    && barriers.get(i1).getX() + 10 > tank.getX() && barriers.get(i1).getX() < tank.getX() + 41) {
                                return false;
                            }
                        }
                        break;
                    case 3:
                        for (int i1 = 0; i1 < barriers.size(); i1++) {
                            if (barriers.get(i1).getX() + 10 > tank.getX() - 11 && barriers.get(i1).getX() < tank.getX() + 46
                                    && barriers.get(i1).getY() + 10 > tank.getY() + 10 && barriers.get(i1).getY() < tank.getY() + 51) {
                                return false;
                            }
                        }
                        break;
                    default:
                        return true;
                }
            }
        }
        return true;
    }

    //是否触碰到障碍物
    public static boolean isTouch(Vector<? extends Tank> tanks, Vector<ViolableBarrier> barriers, int direct) {
        if (!barriers.isEmpty() && !tanks.isEmpty()) {
            for (int i = 0; i < tanks.size(); i++) {
                Tank tank = tanks.get(i);
                switch (direct) {
                    case 0:
                        for (int i1 = 0; i1 < barriers.size(); i1++) {
                            if (barriers.get(i1).getY() + 11 > tank.getY() && barriers.get(i1).getY() < tank.getY() + 56
                                    && barriers.get(i1).getX() + 10 > tank.getX() && barriers.get(i1).getX() < tank.getX() + 41) {
                                return false;
                            }
                        }
                        break;
                    case 1:
                        for (int i1 = 0; i1 < barriers.size(); i1++) {
                            if (barriers.get(i1).getX() < tank.getX() + 50 && barriers.get(i1).getX() + 10 > tank.getX() - 5
                                    && barriers.get(i1).getY() + 10 > tank.getY() + 10 && barriers.get(i1).getY() < tank.getY() + 51) {
                                return false;
                            }
                        }
                        break;
                    case 2:
                        for (int i1 = 0; i1 < barriers.size(); i1++) {
                            if (barriers.get(i1).getY() < tank.getY() + 61 && barriers.get(i1).getY() + 10 > tank.getY() + 5
                                    && barriers.get(i1).getX() + 10 > tank.getX() && barriers.get(i1).getX() < tank.getX() + 41) {
                                return false;
                            }
                        }
                        break;
                    case 3:
                        for (int i1 = 0; i1 < barriers.size(); i1++) {
                            if (barriers.get(i1).getX() + 10 > tank.getX() - 11 && barriers.get(i1).getX() < tank.getX() + 46
                                    && barriers.get(i1).getY() + 10 > tank.getY() + 10 && barriers.get(i1).getY() < tank.getY() + 51) {
                                return false;
                            }
                        }
                        break;
                    default:
                        return true;
                }
            }
        }
        return true;
    }

    //显示我方击毁敌方坦克数量的信息
    public void showInfo(Graphics g) {
        //画出玩家的总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 18);
        g.setFont(font);

        g.drawString("您累计击毁敌方坦克", 1805, 30);
        //drawTank
        drawTank(1810, 60, g, 0, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1870, 100);

    }

    //绘图方法
    @Override
    public void paint(Graphics g) {


        super.paint(g);
        g.fillRect(0, 0, 1800, 1100);//填充矩形，默认黑色
        //确保myTank对象已经正确初始化并设置位置等属性
//        for (int i = 0; i < myTanks.size(); i++) {
//            MyTank myTank = myTanks.get(i);
//        }
        //画出背景图片
        g.drawImage(image5, 0, 0, 1800, 1100, this);
        //画出成绩表
        showInfo(g);

        if (!myTanks.isEmpty()) {
            for (int i = 0; i < myTanks.size(); i++) {
                MyTank myTank = myTanks.get(i);
                if (myTank.isLive()) {
                    //写一个方法画出坦克
                    drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0);
                }
            }
        }

        //画出自己坦克画出的子弹
        if (myTank != null && !myTank.shots.isEmpty()) {
            myTank.drawShot(g);
        }

        //画出敌人坦克，遍历Vector
        if (enemyTanks != null) {
            try {
                for (EnemyTank enemyTank : enemyTanks) {
                    if (enemyTank.isLive()) {
                        //取出坦克
                        drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                        //绘制敌人坦克的子弹
                        enemyTank.drawShot(g);
                    } else {
                        enemyTanks.remove(enemyTank);
                    }
                }
            } catch (Exception e) {
                System.out.println("没事的");
            }
        }
        //爆炸效果，如果Vector集合有对象就画出
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            //根据当前炸弹的生命值画出图片
            if (bomb.live > 12) {
                g.drawImage(image1, bomb.x + 7, bomb.y + 10, 30, 30, this);
            } else if (bomb.live > 6) {
                g.drawImage(image2, bomb.x, bomb.y + 3, 45, 45, this);
            } else if (bomb.live > 0) {
                g.drawImage(image3, bomb.x - 5, bomb.y - 5, 60, 60, this);
            } else if (bomb.live > -6) {
                g.drawImage(image4, bomb.x - 12, bomb.y - 10, 75, 75, this);
            } else if (bomb.live > -12) {
                g.drawImage(image3, bomb.x - 5, bomb.y - 5, 60, 60, this);
            } else if (bomb.live > -18) {
                g.drawImage(image2, bomb.x, bomb.y + 3, 45, 45, this);
            } else if (bomb.live > -21) {
                g.drawImage(image1, bomb.x + 7, bomb.y + 10, 30, 30, this);
            }
            //让炸弹生命值减少
            bomb.lifeDown();
            //如果bomb life为0，就从集合中删除
            if (bomb.live < -21) {
                bombs.remove(bomb);
            }
        }
        //障碍物， 如果violableBarrier集合有对象就画出
        if (!barriers.isEmpty())
            for (int i = 0; i < barriers.size(); i++) {
                ViolableBarrier violableBarrier = barriers.get(i);
                if (violableBarrier.isLive()) {
                    g.setColor(Color.yellow);
                    g.fill3DRect(violableBarrier.getX(), violableBarrier.getY(), 10, 10, false);
                } else {
                    barriers.remove(violableBarrier);
                }
            }

    }
    //编写方法画出坦克

    /**
     * @param x      坦克横坐标
     * @param y      坦克纵坐标
     * @param g      画笔
     * @param direct 坦克方向
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        //根据不同的坦克设置不同的颜色
        switch (type) {
            case 0://我们的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克的方向来绘制坦克
        switch (direct) {
            case 0://向上
                g.fill3DRect(x, y, 10, 61, false);//左轮子
                g.fill3DRect(x + 31, y, 10, 61, false);//右轮子
                g.fill3DRect(x + 10, y + 10, 21, 41, false);//中间的矩形
                g.fillRect(x + 18, y, 5, 30);
                g.fillOval(x + 9, y + 19, 21, 21);//圆盖子


                break;
            case 1://向右
                g.fill3DRect(x - 10, y + 10, 61, 10, false); //左轮子
                g.fill3DRect(x - 10, y + 40, 61, 10, false);//右轮子
                g.fill3DRect(x, y + 20, 41, 21, false);//中间的矩形
                g.fillOval(x + 10, y + 20, 20, 20);//圆盖子
                g.fillRect(x + 20, y + 27, 30, 5);

                break;
            case 2://向下
                g.fill3DRect(x, y, 10, 61, false);//左轮子
                g.fill3DRect(x + 31, y, 10, 61, false);//右轮子
                g.fill3DRect(x + 10, y + 10, 21, 41, false);//中间的矩形
                g.fillRect(x + 18, y + 30, 5, 30);
                g.fillOval(x + 9, y + 19, 21, 21);//圆盖子
                break;
            case 3://向左
                g.fill3DRect(x - 10, y + 10, 61, 10, false); //左轮子
                g.fill3DRect(x - 10, y + 40, 61, 10, false);//右轮子
                g.fill3DRect(x, y + 20, 41, 21, false);//中间的矩形
                g.fillOval(x + 10, y + 20, 20, 20);//圆盖子
                g.fillRect(x - 10, y + 27, 30, 5);
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    //判断子弹是否击中坦克
    public void hitTank(Shot s, Tank tank) {
        switch (tank.getDirect()) {
            case 0:
            case 2:
                if (s.x > tank.getX() && s.x + 1 < tank.getX() + 41
                        && s.y > tank.getY() && s.y < tank.getY() + 61) {
                    s.isLive = false;
                    //当我防坦克击毁敌人坦克就记录++
                    if (tank instanceof EnemyTank) {
                        Recorder.addEnemyTankNum();
                    }
                    tank.setLive(false);
                    //创建Bomb对象,加入bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);

                }
                break;
            case 1:
            case 3:
                if (s.x > tank.getX() - 10 && s.x < tank.getX() + 51
                        && s.y > tank.getY() + 10 && s.y < tank.getY() + 51) {
                    s.isLive = false;
                    tank.setLive(false);
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    //生成敌人坦克
    public void createTank() {
        Random r = new Random();
        EnemyTank enemyTank = new EnemyTank(r.nextInt(1759), r.nextInt(1039), 3);
        enemyTanks.add(enemyTank);
        new Thread(enemyTank).start();
        enemyTank.setEnemyTanks(enemyTanks);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    //复活方法
    public void reLive() {
        myTanks.get(0).setLive(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (int i = 0; i < myTanks.size(); i++) {
            MyTank myTank = myTanks.get(i);
            if (e.getKeyCode() == KeyEvent.VK_W && myTank.getY() > 0) {
                if (isTouch(myTanks, barriers, 0)) {
                    myTank.setDirect(0);
                }
                if (isTouch(myTanks, barriers)) {
                    myTank.moveUp();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_D && myTank.getX() + 50 < 1800) {
                if (isTouch(myTanks, barriers, 1)) {
                    myTank.setDirect(1);
                }
                if (isTouch(myTanks, barriers)) {
                    myTank.moveRight();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_S && myTank.getY() + 60 < 1100) {
                if (isTouch(myTanks, barriers, 2)) {
                    myTank.setDirect(2);
                }
                if (isTouch(myTanks, barriers)) {
                    myTank.moveDown();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_A && myTank.getX() - 10 > 0) {
                if (isTouch(myTanks, barriers, 0)) {
                    myTank.setDirect(3);
                }
                if (isTouch(myTanks, barriers)) {
                    myTank.moveLeft();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_J) {
                //创建子弹方法，如果子弹数量大于10就不创建

                if (myTank.shots.size() < 10) {
                    myTank.shotEnemyTank();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_L) {
                //随机创建敌人坦克
                createTank();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_K) {
            reLive();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        //每隔100毫秒重绘区域
        do {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //判断子弹是否击中敌方坦克
            if (!myTank.shots.isEmpty() && myTank.isLive()) {
                //遍历敌人所有坦克
                for (Shot shot : myTank.shots) {
                    for (EnemyTank enemyTank : enemyTanks) {
                        hitTank(shot, enemyTank);
                    }
                }
            }

            //判断子弹是否击中我方坦克
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank e = enemyTanks.get(i);
                if (!(e.shots.isEmpty())) {
//                    for (Shot shot : e.shots) {//报过错
//                        hitTank(shot, myTank);
//                    }
                    for (int i1 = 0; i1 < e.shots.size(); i1++) {

                        try {
                            Shot shot = e.shots.get(i);
                            hitTank(shot, myTank);
                        } catch (Exception ex) {

                        }

                    }
                }

            }
            this.repaint();
        } while (true);

    }
}
