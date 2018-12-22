package com;

import java.io.IOException;
import java.util.Random;

public class Enemy3 extends Flyer{

    Random r=new Random();
    public Enemy3(int x, int y)  {
        super(x, y,30,30,"images/enemy3.png");
    }

/*
*和A类B类敌机移动不同，只能够在Y轴上面移动
*/
    @Override
    public void move(long time) {
        // TODO Auto-generated method stub
        y+=r.nextInt(5);
    }

    public Enemybullet enemyfire() {

        Enemybullet e=new Enemybullet(this.x+WIDTH/4,this.y+HEIGHT,"images/enemybullet3.png");
        return e;
    }
}

