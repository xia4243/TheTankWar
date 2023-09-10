package Tank01;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class MyTankGame01 extends JFrame {
    //定义MyPanel mp = null;
    MyPanel mp = null;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        MyTankGame01 game01 = new MyTankGame01();
    }

    public MyTankGame01() {
        System.out.println("请输入选择 1:新游戏 2: 继续上局" );
        String st = sc.next();
        mp = new MyPanel(st);
        this.add(mp);//把面板(游戏的绘图区域)
        //将mp放入thread并启动
        new Thread(mp).start();
        this.setSize(2000, 1150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(mp);
        this.setVisible(true);
        //在JFrame 增加响应窗口关闭的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });

    }
}
