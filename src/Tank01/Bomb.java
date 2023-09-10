package Tank01;

public class Bomb implements Runnable{
    int x, y;//炸弹的坐标
    int live = 18;//炸弹生命周期
    boolean isLive = true;//是否存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //减少生命值
    public void lifeDown() {
        if(live > -21) {
            live--;
        }else {
            isLive = false;
        }
    }


    @Override
    public void run() {

    }
}
