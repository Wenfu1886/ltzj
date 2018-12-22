package com;

import java.io.IOException;

public class Gift extends Flyer{

    public Gift(int x, int y) {
        //super(x, y, 50, 50, ImageRead.gift);
    	super(x, y, 60, 108, "images/three.png");
    }
    
    

    @Override
    public void move(long time) {
    	//时间20秒移动加10
            if(time%20==0){
                y+=10;
            }
    }

}
