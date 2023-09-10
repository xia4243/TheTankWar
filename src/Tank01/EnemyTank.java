package Tank01;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    //在敌人坦克类，使用Vector保存多个shot
    Vector<Shot> shots = new Vector<>();
    //增加成员，EnemyTank可以得到敌人的坦克Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //判断当前坦克是都和Vector里的其他坦克发生重叠
    public boolean isTouchEnemyTank() {
        //判断当前坦克的方向,如果重叠返回true
        switch (this.getDirect()) {
            case 0:
                //让当前的this坦克和所有坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从集合中取出一个坦克与当前坦克比较
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //排除当前坦克
                    if (this != enemyTank) {
                        //如果敌人坦克方向是0或2
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克左上角坐标为this.getX,右上角是this.getX + 41
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 41
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 61) {
                                return true;
                            }
                            if (this.getX() + 41 >= enemyTank.getX()
                                    && this.getX() + 41 <= enemyTank.getX() + 41
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 61) {
                                return true;
                            }

                        }
                        //如果敌人坦克方向是1或3
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (this.getX() >= enemyTank.getX() - 10
                                    && this.getX() <= enemyTank.getX() + 51
                                    && this.getY() >= enemyTank.getY() + 10
                                    && this.getY() <= enemyTank.getY() + 51) {
                                return true;
                            }
                            if (this.getX() + 41 >= enemyTank.getX() - 10
                                    && this.getX() + 41 <= enemyTank.getX() + 51
                                    && this.getY() >= enemyTank.getY() + 10
                                    && this.getY() <= enemyTank.getY() + 51) {
                                return true;
                            }
                        }
                    }

                }
                break;
            case 1:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从集合中取出一个坦克与当前坦克比较
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //排除当前坦克
                    if (this != enemyTank) {
                        //如果敌人坦克方向是0或2
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (this.getX() + 51 >= enemyTank.getX()
                                    && this.getX() + 51 <= enemyTank.getX() + 41
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 61) {
                                return true;
                            }
                            if (this.getX() + 51 >= enemyTank.getX()
                                    && this.getX() + 51 <= enemyTank.getX() + 41
                                    && this.getY() + 51 >= enemyTank.getY()
                                    && this.getY() + 51 <= enemyTank.getY() + 61) {
                                return true;
                            }

                        }
                        //如果敌人坦克方向是1或3
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (this.getX() + 51 >= enemyTank.getX() - 10
                                    && this.getX() + 51 <= enemyTank.getX() + 51
                                    && this.getY() >= enemyTank.getY() + 10
                                    && this.getY() <= enemyTank.getY() + 51) {
                                return true;
                            }
                            if (this.getX() + 51 >= enemyTank.getX() - 10
                                    && this.getX() + 51 <= enemyTank.getX() + 51
                                    && this.getY() + 51 >= enemyTank.getY() + 10
                                    && this.getY() + 51 <= enemyTank.getY() + 51) {
                                return true;
                            }
                        }
                    }

                }
                break;
            case 2:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从集合中取出一个坦克与当前坦克比较
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //排除当前坦克
                    if (this != enemyTank) {
                        //如果敌人坦克方向是0或2
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 41
                                    && this.getY() + 61 >= enemyTank.getY()
                                    && this.getY() + 61 <= enemyTank.getY() + 61) {
                                return true;
                            }
                            if (this.getX() + 41 >= enemyTank.getX()
                                    && this.getX() + 41 <= enemyTank.getX() + 41
                                    && this.getY() + 61 >= enemyTank.getY()
                                    && this.getY() + 61 <= enemyTank.getY() + 61) {
                                return true;
                            }

                        }
                        //如果敌人坦克方向是1或3
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (this.getX() >= enemyTank.getX() - 10
                                    && this.getX() <= enemyTank.getX() + 51
                                    && this.getY() + 61 >= enemyTank.getY() + 10
                                    && this.getY() + 61 <= enemyTank.getY() + 51) {
                                return true;
                            }
                            if (this.getX() + 41 >= enemyTank.getX() - 10
                                    && this.getX() + 41 <= enemyTank.getX() + 51
                                    && this.getY() + 61 >= enemyTank.getY() + 10
                                    && this.getY() + 61 <= enemyTank.getY() + 51) {
                                return true;
                            }
                        }
                    }

                }
                break;
            case 3:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从集合中取出一个坦克与当前坦克比较
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //排除当前坦克
                    if (this != enemyTank) {
                        //如果敌人坦克方向是0或2
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (this.getX() - 10 >= enemyTank.getX()
                                    && this.getX() - 10 <= enemyTank.getX() + 41
                                    && this.getY() + 10 >= enemyTank.getY()
                                    && this.getY() + 10 <= enemyTank.getY() + 61) {
                                return true;
                            }
                            if (this.getX() + 10 >= enemyTank.getX()
                                    && this.getX() + 10 <= enemyTank.getX() + 41
                                    && this.getY() + 51 >= enemyTank.getY()
                                    && this.getY() + 51 <= enemyTank.getY() + 61) {
                                return true;
                            }

                        }
                        //如果敌人坦克方向是1或3
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (this.getX() - 10 >= enemyTank.getX() - 10
                                    && this.getX() - 10 <= enemyTank.getX() + 51
                                    && this.getY() >= enemyTank.getY() + 10
                                    && this.getY() <= enemyTank.getY() + 51) {
                                return true;
                            }
                            if (this.getX() + 10 >= enemyTank.getX() - 10
                                    && this.getX() + 10 <= enemyTank.getX() + 51
                                    && this.getY() + 51 >= enemyTank.getY() + 10
                                    && this.getY() + 51 <= enemyTank.getY() + 51) {
                                return true;
                            }
                        }
                    }

                }
                break;
        }
        return false;
    }

    //画子弹方法
    @Override
    public void drawShot(Graphics g) {
        for (int j = 0; j < shots.size(); j++) {
            //取出子弹
            Shot shot = shots.get(j);
            if (shot.isLive) {
                g.fill3DRect(shot.x, shot.y, 3, 3, false);
            } else {
                //从Vector移除子弹
                shots.remove(shot);
            }
        }
    }

    public void shotEnemyTank() {
        //创建 Shot 对象,根据当前坦克对象的位置和方向
        //创建对象
        switch (getDirect()) {
            case 0://向上
                Shot shot = new Shot(getX() + 19, getY(), 0);
                new Thread(shot).start();
                shots.add(shot);
                break;
            case 1://向右
                Shot shot1 = new Shot(getX() + 50, getY() + 29, 1);
                shots.add(shot1);
                new Thread(shot1).start();
                break;
            case 2://向下
                Shot shot2 = new Shot(getX() + 19, getY() + 61, 2);
                shots.add(shot2);
                new Thread(shot2).start();
                break;
            case 3://向左
                Shot shot3 = new Shot(getX() - 10, getY() + 29, 3);
                shots.add(shot3);
                new Thread(shot3).start();
                break;
        }
    }

    public EnemyTank(int x, int y) {
        super(x, y);
    }


    //睡眠方法
    public void sleep(int x) {
        try {
            Thread.sleep(x);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //改变坐标方法
//    public void setXY(int direct) {
//        Random r = new Random();
//        int speed = r.nextInt(30) + 20;
//        switch (direct) {
//            case 0:
//                for (int i = 0; i < speed; i++) {
//                    setY(getY() - 2);
//                    sleep(50);
//                }
//            case 1:
//                for (int j = 0; j < speed; j++) {
//                    setX(getX() + 2);
//                    sleep(50);
//                }
//                sleep(50);
//            case 2:
//                for (int j = 0; j < speed; j++) {
//                    setY(getY() + 2);
//                    sleep(50);
//                }
//            case 3:
//                for (int j = 0; j < speed; j++) {
//                    setX(getX() - 2);
//                    sleep(50);
//                }
//        }
//    }

    //加入子弹方法
    public void addShot(int x) {
        if (x == 5 || x == 3 || x == 7 || x == 10 || x == 0 || x == 2 || x == 4 || x == 1) {
//            //给敌人加入一颗子弹
//            Shot shot = new Shot(getX() + 20, getY() + 60, getDirect());
//            new Thread(shot).start();
//            //加入enemyTank子弹的集合
//            shots.add(shot);
            shotEnemyTank();
        }
    }

    public EnemyTank(int x, int y, int speed) {
        super(x, y, speed);
    }



    @Override
    public void run() {
        Random r = new Random();
//        while (true) {
//            if (getX() >= 0 && getX() <= 3200 && getY() >= 0 && getY() <= 2000) {
//                //给一个10内的随机数，如果是5就发射子弹
//                addShot(r.nextInt(11));
//                switch (getDirect()) {
//                    case 0:
//                        //改变敌人的移动方向
//                        setDirect(r.nextInt(4));
//                        switch (getDirect()) {
//                            //根据敌人坦克坐标随机加对应坐标
//                            case 0:
//                                setXY(0);
//                                break;
//                            case 1:
//                                setXY(1);
//                                break;
//                            case 2:
//                                setXY(2);
//                                break;
//                            case 3:
//                                setXY(3);
//                                break;
//                        }
//                        break;
//
//                    case 1:
//                        setDirect(r.nextInt(4));
//                        switch (getDirect()) {
//                            case 0:
//                                setXY(0);
//                                break;
//                            case 1:
//                                setXY(1);
//                                break;
//                            case 2:
//                                setXY(2);
//                                break;
//                            case 3:
//                                setXY(3);
//                                break;
//                        }
//                        break;
//                    case 2:
//                        setDirect(r.nextInt(4));
//                        switch (getDirect()) {
//                            case 0:
//                                setXY(0);
//                                break;
//                            case 1:
//                                setXY(1);
//                                break;
//                            case 2:
//                                setXY(2);
//                                break;
//                            case 3:
//                                setXY(3);
//                                break;
//                        }
//                        break;
//                    case 3:
//                        setDirect(r.nextInt(4));
//                        switch (getDirect()) {
//                            case 0:
//                                setXY(0);
//                                break;
//                            case 1:
//                                setXY(1);
//                                break;
//                            case 2:
//                                setXY(2);
//                                break;
//                            case 3:
//                                setXY(3);
//                                break;
//                        }
//                        break;
//                }
//                sleep(5000);
//            }
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }


        while (isLive()) {
            int rx = r.nextInt(30) + 20;
            //根据坦克的方向来继续移动
            switch (getDirect()) {
                case 0:
                    for (int i = 0; i < rx; i++) {
                        if (getY() >= 0 && !isTouchEnemyTank()) {
                            moveUp();
                            sleep(50);
                        } else break;
                    }
                    break;
                case 1:
                    for (int i = 0; i < rx; i++) {
                        if (getX() + 51 <= 1800 && !isTouchEnemyTank()) {
                            moveRight();
                            sleep(50);
                        } else break;
                    }
                    break;
                case 2:
                    for (int i = 0; i < rx; i++) {
                        if (getY() + 61 <= 1100 && !isTouchEnemyTank()) {
                            moveDown();
                            sleep(50);
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < rx; i++) {
                        if (getX() - 10 >= 0 && !isTouchEnemyTank()) {
                            moveLeft();
                            sleep(50);

                        }
                    }
            }

            //敌人随机射出子弹
            addShot(r.nextInt(11));
            //休眠50毫秒
            sleep(1000);
            //随机改变坦克方向
            setDirect((int) (Math.random() * 4));
        }
        //考虑这个线程什么时候结束
    }
}

