package Tank01;

import java.awt.*;
import java.util.Vector;

public class MyTank extends Tank {//自己的坦克
    //创建一个Shot对象,表示一个射击的行为
    Vector<Shot> shots = new Vector<>();

    public MyTank(int x, int y, int speed) {
        super(x, y, speed);
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

    @Override
    public void setLive(boolean live) {
        super.setLive(live);
    }

    //发射子弹方法
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
}

