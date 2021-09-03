package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/*
1.写一个类继承JFrame
2.写一个构造方法确定窗口特点
* 游戏的窗体
* */
public class GameFrame extends JFrame {
    //构造器 初始化一个窗体
    public GameFrame(){
        //设置标题
        setTitle("My plane fight");
        //设置大小
        setSize(520,733);
        //设置位置居中 null 表示相对于屏幕左上角居中
        setLocationRelativeTo(null);
        //设置不允许改变大小
        setResizable(false);
        //设置默认关闭选项 防止关闭窗口程序依然在运行
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args){
        //建立窗体对象
        GameFrame frame=new GameFrame();
        //建立面板对象
        GamePanel panel=new GamePanel(frame);//放边在面板中加键盘监听器
        //开始游戏的按钮 按下按钮开始游戏
        //创建面板之后 则开始游戏
        //把面板加入窗体
        frame.add(panel);
        //显示窗体
        frame.setVisible(true);
    }
}
