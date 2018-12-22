package com;

import java.awt.image.BufferedImage;
import java.io.IOException;

/*
 * 英雄机的类，因为英雄机需要键盘来控制移动，所以。移动的方法需要从新定义
 */
public class Hero extends Flyer{

    public Hero(int x, int y,String localString) {
    	
        super(x, y, 40, 40, localString);
        // TODO Auto-generated constructor stub
    }
    
    public Hero(int x,int y,BufferedImage img) {
    	super(x, y, 40, 40, img);
    }
    public void MoveUp() { // 以防飞机上移出界，每次移五个单位
        if (y >= 10)
            y -= 10;
    }

    public void MoveDown() {
        if (y <= 606) // 游戏界面减去飞机高79和最小移动步长
            y += 10;
    }

    public void MoveLeft() {
        if (x >= 10)
            x -= 10;
    }

    public void MoveRight() {
        if (x <= 431)
            x += 10;
    }

    //飞机子弹发射的位置由飞机位置确定
    public Herobullet Herofire() {
        Herobullet bullet=new Herobullet(this.x+WIDTH/4,this.y,"");
        return bullet;
    }
    @Override
    public void move(long time) {

    }
}