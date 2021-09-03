package ui;

import java.awt.image.BufferedImage;
/*
* 在开发中 会将具有相同特点的类放到一起，将这些相同的特点抽离出来形成一个父类
* */
public class FlyObject {
    //飞行物图片
    BufferedImage img;
    //飞行物横坐标
    int x;
    //飞行物纵坐标
    int y;
    //飞行物宽度
    int w;
    //飞行物高度
    int h;
}
