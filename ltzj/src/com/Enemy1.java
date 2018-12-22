package com;

import java.io.IOException;
import java.util.Random;
public class Enemy1 extends Flyer{
	  Random r=new Random();
	    //构造方法
	    public Enemy1(int x, int y){
	        super(x, y, 40, 40,"images/enemy1.png");
	    }

	    /*
	     * A类敌机的移动方法，X,Y每次随机加上一个小于5的值
	     */
	    @Override
	    public void move(long time) {
	        // TODO Auto-generated method stub
	        x+=r.nextInt(5);
	        y+=r.nextInt(5);
	        }
	    /*
	     * 
	     * 敌机子弹是随着敌机位置出现的，所以这里写一个返回敌机火力的方法
	     */
	    public Enemybullet enemyfire(){

	        Enemybullet e=new Enemybullet(this.x+WIDTH/4,this.y+HEIGHT,"images/enemybullet.png");
	        return e;
	    }

}
