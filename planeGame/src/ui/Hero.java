package ui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
 * 英雄机类
 * 属性对应特点 方法对应行为
 * */
public class Hero extends FlyObject {
    int hp;//英雄机的血量
    //创建子弹的集合
    ArrayList<Fire> fires = new ArrayList<>();
    // 设置英雄机的火力
    int power = 1;

    /*构造方法 确定英雄机特点*/
    public Hero() {
        //确定游戏开始时，英雄机显示的图片
        img = App.getImg("/img/plane.png");
        //使用坐标确定英雄机在游戏开始时显示的位置
        x = 210;
        y = 600;
        //确定英雄机大小
        w = img.getWidth() / 2;
        h = img.getHeight() / 2;
        hp = 3;
    }
    /*英雄机移动到鼠标位置上的方法*/

    public void moveToMouse(int mx, int my) {
        x = mx - w / 2;//让鼠标在飞机的中心
        y = my - h / 2;
    }

    int findex = 0;

    protected void shoot() {
        findex++;
        if (findex >= 3) {
            if (power == 1) {
                Fire fire1 = new Fire(x + w / 2 - 12, y - h / 2 + 10, 1);//子弹正中间发射
                fires.add(fire1);
            } else if (power == 2) {
                Fire fire1 = new Fire(x + w / 2 - 27, y - h / 2 + 10, 1);
                fires.add(fire1);
                Fire fire2 = new Fire(x + w / 2+3, y - h / 2 + 10, 1);
                fires.add(fire2);
            } else {
                Fire fire1 = new Fire(x + w / 2 - 12, y - h / 2 + 10, 1);//正的子弹
                fires.add(fire1);
                Fire fire2 = new Fire(x + w / 2 - 27, y - h / 2 + 10, 1); //往左飞 一次发射多颗子弹
                fires.add(fire2);
                Fire fire3 = new Fire(x + w / 2+3, y - h / 2 + 10, 1); //往左飞 一次发射多颗子弹
                fires.add(fire3);
            }

            //加上下面两条可以有散弹的效果
//        Fire fire1=new Fire(hero.x+hero.w/2-24,hero.y-hero.h/2+10,0); //往左飞 一次发射多颗子弹
//        hero.fires.add(fire1);
//        Fire fire2=new Fire(hero.x+hero.w/2,hero.y-hero.h/2+10,2); //往右飞 一次发射多颗子弹
//        hero.fires.add(fire2);
            findex = 0;
        }
    }

    protected void fireMove() {
        //遍历所有子弹
        for (int i = 0; i < fires.size(); i++) {
            //获取一个子弹
            Fire fire = fires.get(i);
            fire.move();
        }
    }
}
