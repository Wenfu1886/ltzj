package com;

import java.io.IOException;

public class Enemybullet extends Flyer{

    public Enemybullet(int x, int y,String localString)  {
        super(x, y, 10, 13, localString);
    }
    @Override
    public void move(long time) {
        if(time%20==0){
            y+=10;
        }
    }
}