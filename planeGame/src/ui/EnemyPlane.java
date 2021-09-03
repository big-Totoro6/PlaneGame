package ui;

import java.awt.image.BufferedImage;
import java.util.Random;

/*
 * 敌机类
 * 需求：
 *1. 游戏中的敌机很多
 *2. 敌机的数量时未知的
 * */
public class EnemyPlane extends FlyObject {
    int speed;
    int hp;
    int type;
    public EnemyPlane() {
        //定义一个生成随机数的类
        Random rd = new Random();
        //确定敌机图片 随机
        int index = rd.nextInt(6) + 1;//[1,7)
        String path = "/img/ep" + (index < 10 ? "0" : "") + index + ".png";//ep 后面是否加0 用条件表达式判断 如果有十多架飞机的话
        img = App.getImg(path);
        //确定敌机大小
        w = img.getWidth() / 2;
        h = img.getHeight() / 2;
        //确定敌机的位置

        x = rd.nextInt(521 - w);//在[0,521) 中 让它不要超出屏幕
        y = -h;//从屏幕外出现

        //设置速度
        speed = index + 4;//根据体型 速度不一样
        //设置血量
        if(index==6){
            type=6;//这是道具机
            hp=3;
        }else{
            this.hp=6-index;//最小的级别 index=5 hp 只有1格血 打一下就死了
            type=index;
        }

    }

    //让敌机向下移动
    public void move() {
        y += speed;
    }

    public boolean shootBy(Fire f) {
        boolean hit = (x <= f.x + f.w) && (x >= f.x - w) && (y<=f.y+f.h)&&(y>=f.y-h);
        return hit;
    }
    public boolean hitBy(Hero f) {
        boolean hit = (x <= f.x + f.w) && (x >= f.x - w) && (y<=f.y+f.h)&&(y>=f.y-h);
        return hit;
    }
}
