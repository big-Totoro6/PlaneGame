package ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*（简化代码）
* 处理图片的工具类
* 工具类的写法：工具类中一般会将项目中需要重复使用代码抽离出来，定义成工具方法，
* 工具类中的方法一般需要加static 防止总是要建立来调用方法
* */
public class App {
    /*
    * 读取指定位置上的图片
    * */
    public static BufferedImage getImg(String path){
        //加载图片
        //Java 中的IO流 输送数据的管道
        //App.class 找到App类的路径
        //getResource()获取资源
        try {
            BufferedImage img= ImageIO.read(App.class.getResource(path));
            //找到图片就将图片返回
            return img;
        }catch (IOException e){
            //捕获找不到图片的原因
            e.printStackTrace();
        }
        return null;
    }
}
