package ui;

import javax.security.auth.login.FailedLoginException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
 * 游戏面板 JPanel
 * 1.写一个类继承JPanel
 * 2.写一个构造方法初始化面板属性
 *
 * //画图步骤
 * 1.在类中定义图片并取名
 * 2.在构造方法中调用工具初始化图片
 * 3.在画图方法 paint 画图片
 * */
public class GamePanel extends JPanel {
    //1.定义背景图
    BufferedImage bg;
    //2.创建英雄机
    Hero hero = new Hero();
    ArrayList<EnemyPlane> eps = new ArrayList<>();//创建个敌机的集合
    int score = 0;
    //    public static int gameIndex=0;//判断是第几次点击  设置开始
    //设置游戏的开关
    boolean gameover = false;//游戏开始时，gameover=false  ;游戏结束 gameover=true;
    private JButton startButton;
    private boolean start = false;

    /*
    开始游戏的方法
    * */
    public void action() {
        //创建并启动一个线程，控制游戏场景中的物体活动
        //固定格式
        //new Thread(){public void run(){...线程需要做的事情...}}.start();

        new Thread() {
            public void run() {
                //写一个死循环，让游戏一直运行
                while (true) {
                    if (!gameover) {
                        //调用敌机入场的方法
                        epEnter();
                        //让敌机移动
                        epMove();
                        //发射子弹
                        hero.shoot();
                        //让子弹移动
                        hero.fireMove();
                        shootEp();
                        hit();
                    }

                    //每移动一次 则休眠一会 防止落得超级快
                    try {
                        Thread.sleep(180);//控制整体的速度
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    repaint();//刷新界面
                }
            }

        }.start();
    }

    /*判断子弹是否击中敌机*/
    protected void shootEp() {
        for (int i = 0; i < hero.fires.size(); i++) {
            Fire f = hero.fires.get(i);
            //每拿到一颗子弹，判断一些这颗子弹是否击中敌机
            bang(f);
        }
    }

    /*
     * 判断一颗子弹是否击中敌机
     * */
    protected void bang(Fire f) {
        for (int i = 0; i < eps.size(); i++) {
            EnemyPlane e = eps.get(i);
            //判断这个子弹是否击中敌机
            if (e.shootBy(f)) {
                //敌机被子弹击中
                e.hp--;//扣一滴血
                if (e.hp <= 0) {//没血了
                    if (e.type == 6)//打掉了道具机
                    {
                        //子弹改变方法
                        hero.power++;
                        if (hero.power > 3) {
                            hero.hp++;
                            if (hero.hp >= 5) {
                                hero.hp = 5;//最大生命 即使打了道具机也不加生命
                            }
                            hero.power = 3;
                        }
                    }
                    eps.remove(e);//敌机死亡
                    score += 10;
                }
                hero.fires.remove(f);//子弹消失
            }
        }
    }

    /* 敌机入场方法
    问题 敌机出现频率太高
    可否没执行20次epEnter方法才创建一个敌机*/
    int index = 0;//记录方法执行的次数，每执行n次释放一次敌机

    protected void epEnter() {
        index++;//记录执行的次数
        if (index >= 20) {
            //创建敌机
            EnemyPlane e = new EnemyPlane();
            //把敌机放到这个集合中
            eps.add(e);
            index = 0;
        }
    }

    //检测敌机是否撞到英雄机
    protected void hit() {
        for (int i = 0; i < eps.size(); i++) {
            EnemyPlane e = eps.get(i);
            //如果敌机撞到英雄机
            if (e.hitBy(hero)) {
                //撞到敌机 敌机销毁
                eps.remove(e);
                score += 10;
                hero.hp--;
                hero.power = 1;//英雄机被撞到了 则恢复初始火力
                if (hero.hp <= 0) {
                    gameover = true;
//                    gameIndex=1;
                }
            }
        }
    }

    protected void epMove() {
        //遍历所有敌机
        for (int i = 0; i < eps.size(); i++) {
            //获取每一个敌机
            EnemyPlane ep = eps.get(i);
            //敌机向下移动
            ep.move();
        }
    }


    public GamePanel(GameFrame frame) {
        //设置背景
        setBackground(Color.black);
        //初始化图片
        bg = App.getImg("/img/background.jpg");
        startButton = new JButton("开始游戏");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startButton.setVisible(false);
                gameover = false;
                action();
            }
        });
//        使用鼠标监听器（固定格式）
//        1.创建鼠标适配器
        MouseAdapter adapter = new MouseAdapter() {
            //2.确定需要监听鼠标的事件
            // 鼠标事件
            //mouseMoved() 监听鼠标移动
            //mouseClicked() 监听鼠标单击事件
            //mousePressed() 监听鼠标按下事件
            //mouseEntered() 监听鼠标移入游戏界面
            //mouseExited() 监听鼠标移除游戏界面

            //鼠标点击事件


            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameover) {
                    //重新开始游戏
                    hero = new Hero();
                    //重置游戏开关
                    gameover = false;
                    score = 0;//分数清零
                    eps.clear();//清空敌机
                    hero.fires.clear();//清空子弹
                    repaint();
                }
            }

//            //鼠标移动事件
//            public void mouseMoved(MouseEvent e) {
//                //当鼠标在游戏界面中移动时会触发
////               System.out.println("Mouse is moving ");
//                //让英雄机跟着鼠标一起动
//                //让英雄机的横纵坐标等于鼠标的横纵坐标
//                //获取鼠标的横纵坐标
//                int mx = e.getX();
//                int my = e.getY();
//                if(!gameover) {//游戏没结束 绑定鼠标和英雄机
//                    //让英雄机移动到鼠标的位置 （英雄机的行为可以在英雄机中定义，可以定义移动到鼠标位置的方法）
//                    hero.moveToMouse(mx, my);//必须要刷新界面 才能让飞机跟着走 不然就只是x，y变了 ，但没显示走
//                    //刷新界面，将英雄机绘制到新的位置上
//                    //重新调用repaint 方法
//                }
//
//                repaint();
//            }
        };
        //3.将适配器加入到监听器中
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        //使用键盘监听器（固定格式）
        //1.创建键盘适配器
        KeyAdapter kd = new KeyAdapter() {
            //2.确定需要监听的键盘事件
            @Override
            public void keyPressed(KeyEvent e) {
                //监听键盘按键
                //获取键盘上按键的数字

                int keycode = e.getKeyCode();
//                System.out.println(keycode);
                if (!gameover) {//游戏没结束 绑定鼠标和英雄机
                    if (keycode == KeyEvent.VK_UP) {
                        //英雄机向上
                        hero.y -= 10;
                    } else if (keycode == KeyEvent.VK_LEFT) {
                        //英雄机向左
                        hero.x -= 10;
                    } else if (keycode == KeyEvent.VK_RIGHT) {
                        //英雄机向右
                        hero.x += 10;
                    } else if (keycode == KeyEvent.VK_DOWN) {
                        //英雄机向下
                        hero.y += 10;
                    }
                }
                repaint();

            }
        };
        //3.将适配器加入到监听器中
        frame.addKeyListener(kd);
        //当你Frame里边有按钮的时候如果对窗口增加键盘监听会失效
        // 原因是因为 你窗口里边有按钮 会让你窗口失去焦点 你点了窗口也不能获取焦点反正就是监听不上键盘了
        // 解决办法贼鸡简单 让窗口获得焦点即可 this.setFocusable(true);
        frame.setFocusable(true);
    }


    /*
     * 专用画图方法
     * Graphics g 画笔
     * 游戏界面的显示都在这里面通过画图进行操作
     * */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //画图片
        //g.drawImage(图片，图片横坐标，图片纵坐标，null)
        //或者可以自定义图片大小
        // g.drawImage(bg,0,0,自定义图片的宽度,自定义图片的高度,null);
        //注意 画图 先画的会被后画的覆盖
        g.drawImage(bg, 0, 0, null);
        this.add(startButton);
        int frameWidth = this.getWidth();
        int frameHeight = this.getHeight();
        int buttonWidth = 100;
        //令按钮居中
        startButton.setBounds(this.getWidth() / 2 - buttonWidth / 2, this.getHeight() / 2, buttonWidth, 50);
        //画英雄机
        g.drawImage(hero.img, hero.x, hero.y, hero.w, hero.h, null);
        //画敌机
//        g.drawImage(ep.img, ep.x, ep.y, ep.w, ep.h, null);
        //遍历敌机集合，画出所有数据
        for (int i = 0; i < eps.size(); i++) {
            EnemyPlane ep = eps.get(i);
            g.drawImage(ep.img, ep.x, ep.y, ep.w, ep.h, null);
        }
        for (int i = 0; i < hero.fires.size(); i++) {
            //获取一个子弹
            Fire fire = hero.fires.get(i);
            g.drawImage(fire.img, fire.x, fire.y, fire.w, fire.h, null);
        }
        //画分数
        g.setColor(Color.white);
        g.setFont(new Font("楷体", Font.BOLD, 20));
        g.drawString("分数:" + score, 10, 30);
        //画血
        g.drawString("血量:" + hero.hp, 10, 55);
        //判断是否第一次开始游戏 点击屏幕开始游戏
        //当游戏结束 画出gameover
        if (gameover) {
            g.setColor(Color.red);
            g.setFont(new Font("楷体", Font.BOLD, 33));
            g.drawString("GAMEOVER!", 220, 300);
            g.drawString("点击屏幕重新开始游戏", 100, 400);
        }
    }
}
