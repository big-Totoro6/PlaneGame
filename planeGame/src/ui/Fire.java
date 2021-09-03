package ui;
/*
* 子弹
* */
public class Fire extends FlyObject {
    //子弹当前移动方向
    int dir=1;// dir=0 左上角飞 ; dir=1 垂直飞 ;dir=2 右上角飞
/*
* 构造方法
* */

    public Fire() {
    }

    public  Fire(int hx, int hy,int dir){
    //确定子弹图片
    img=App.getImg("/img/fire01.png");
    //确定子弹大小
    w=img.getWidth()/8;
    h=img.getHeight()/8;
    //确定子弹的位置
    x=hx;
    y=hy;
    this.dir=dir;
}
public void move(){
        if(dir==0){
            //左上角飞
            x-=2;
            y-=10;
        }else if(dir==1){
           //垂直飞
            y-=10;
        }else{
            //右上角飞
            x+=2;
            y-=10;
        }

}
}
