package Tank01;


//射击一颗子弹
public class Shot implements Runnable {
    int x;//子弹x坐标
    int y;//子弹y坐标
    int direct = 0;//子弹的方向
    int speed = 5;//子弹的速度
    boolean isLive = true;

    @Override
    public void run() {//射击行为
        while (true) {
            //线程休眠
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //根据方向来改变x,y坐标
            switch (direct) {
                case 0://上
                    y -= speed;
                    break;
                case 1://右
                    x += speed;
                    break;
                case 2://下
                    y += speed;
                    break;
                case 3://左
                    x -= speed;
                    break;
            }
            //当子弹碰到敌人坦克时也要销毁
            if (!(x >= 0 && x <= 1800 && y >= 0 && y <= 1100 && isLive)) {
                isLive = false;
                break;
            }
        }
    }

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }
}
