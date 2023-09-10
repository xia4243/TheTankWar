package Tank01;

import java.io.*;
import java.util.Vector;

//记录相关的信息和文件进行交互
public class Recorder {
    //记录我方击毁敌人坦克数量
    private static int allEnemyTankNum = 0;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "out\\score\\myScore.txt";
    //得到敌人坦克集合
    private static Vector<EnemyTank> enemyTanks = null;
    //定义一个Node的Vector用于保存敌人的信息
    private static Vector<Node> nodes = new Vector<>();


    //读取recordFile文件恢复相关信息
    public static Vector<Node> getNodesAndEnemyTankNum() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件生成nodes集合
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return nodes;
    }

    //当游戏退出是将文件保存
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "" + "\r\n");
            //遍历敌人坦克集合，保存敌人坦克
            //定义一个属性，通过set方法得到敌人坦克集合
            if (!enemyTanks.isEmpty()) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank.isLive()) {
                        String record = enemyTank.getX() + " "
                                + enemyTank.getY() + " " + enemyTank.getDirect();
                        bw.write(record + "\r\n");
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static String getRecordFile() {
        return recordFile;
    }

    //当我方击毁一个敌人坦克就对allEnemyTankNum++
    public static void addEnemyTankNum() {
        allEnemyTankNum++;
    }

}
