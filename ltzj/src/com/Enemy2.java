package com;

import java.io.IOException;
import java.util.Random;

public class Enemy2 extends Flyer{

    Random r=new Random();
    public Enemy2(int x, int y){
        super(x, y, 35, 35, "images/enemy2.png");
    }
//只能向左移动
    @Override
    public void move(long time) {
        // TODO Auto-generated method stub

        x-=r.nextInt(5);
        y+=r.nextInt(5);
    }
    

    public Enemybullet enemyfire() {

        Enemybullet e=new Enemybullet(this.x+WIDTH/4,this.y+HEIGHT,"images/enemybullet2.png");
        return e;
    }

}
