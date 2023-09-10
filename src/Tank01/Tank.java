package Tank01;

import java.awt.*;

public abstract class Tank {
    private int x;//坦克的横坐标
    private int y;//坦克纵坐标
    private int direct;//坦克方向
    private int speed = 3;//坦克速度
    private boolean isLive = true;
    private boolean isLoad = false;

    public Tank(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }



    public Tank(int x, int y) {
    }

    //画子弹方法
    public void drawShot(Graphics g) {
    }

    //移动方法
    public void moveUp() {
        y -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

}

