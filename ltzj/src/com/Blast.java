package com;

import java.io.IOException;

/*
 *  英雄机子弹和敌机相撞的爆炸类
 */
public class Blast extends Flyer{

    public Blast(int x, int y) {
        super(x-20, y-20,50,50,"images/blast.png");
    }
        //并没有移动，爆炸图刚刚出现就消失了
    @Override
    public void move(long time) {
        // TODO Auto-generated method stub

    }
}
