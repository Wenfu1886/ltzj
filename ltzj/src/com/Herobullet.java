package com;

import java.io.IOException;

public class Herobullet extends Flyer {

    public Herobullet(int x, int y,String herobullets) {
    	
        super(x, y, 40,40, herobullets);
    }
  

    @Override
    public void move(long time) {
        // TODO Auto-generated method stub
        if(time%20==0){
            y-=10;
        }
    }
}