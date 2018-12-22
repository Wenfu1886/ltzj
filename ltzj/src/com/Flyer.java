package com;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * 
 * 封装所有飞行器的属性和方法
 * @author first
 *
 */
public abstract class Flyer {
    /*
     * 飞行器的坐标
     */
     public int x;
     public int y;
    /*
     * 飞行器的大小
     */
    public int WIDTH;
    public int HEIGHT;
    /*
     * 飞行器的图片
     */

    
    String localImg;
    
     BufferedImage image;
	public Image[] herobullet;
    //初始化飞行器对象
     public Flyer(int x,int y,int WIDTH,int HEIGHT,String localImg){
         this.x=x;
         this.y=y;
         this.WIDTH=WIDTH;
         this.HEIGHT=HEIGHT;
         this.localImg = localImg;
         try {
        	    image = ImageIO.read(new File(localImg));
		} catch (Exception e) {
			// TODO: handle exception
		}
     }
  
   
     
     
     
    public Flyer(int x, int y, int wIDTH, int hEIGHT, BufferedImage image) {
		super();
		this.x = x;
		this.y = y;
		WIDTH = wIDTH;
		HEIGHT = hEIGHT;
		this.image = image;
	}




	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getWIDTH() {
		return WIDTH;
	}


	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}


	public int getHEIGHT() {
		return HEIGHT;
	}


	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}


	public String getLocalImg() {
		return localImg;
	}


	public void setLocalImg(String localImg) {
		this.localImg = localImg;
	}


	public BufferedImage getImage() {
		return image;
	}


	public void setImage(BufferedImage image) {
		this.image = image;
	}


	//每个飞行器都需要自己移动，但是需要子类自己去实现移动的方法
    public abstract void move(long time);
    //判断两个飞行器是否碰撞
    public static boolean boom(Flyer f1,Flyer f2){  
        //step1: 求出两个矩形的中心点  
        int f1x = f1.x + f1.WIDTH/2;  
        int f1y = f1.y + f1.HEIGHT/2;  
        int f2x = f2.x + f2.WIDTH/2;  
        int f2y = f2.y + f2.HEIGHT/2;  
        //step2: 横向和纵向碰撞检测  
        boolean H = Math.abs(f1x - f2x) < (f1.WIDTH + f2.WIDTH)/2;  
        boolean V = Math.abs(f1y -f2y) < (f1.HEIGHT + f2.HEIGHT)/2;  
        //step3: 必须两个方向同时碰撞  
        return H&V;  
    }
    
    public static boolean boomgift(Flyer f1,Flyer f2){  
        //step1: 求出两个矩形的中心点  
        int f1x = f1.x + f1.WIDTH/2;  
        int f1y = f1.y + f1.HEIGHT;  
        int f2x = f2.x + f2.WIDTH/2;  
        int f2y = f2.y;  
        //step2: 横向和纵向碰撞检测  
        boolean H = Math.abs(f1x - f2x) < (f1.WIDTH + f2.WIDTH)/2;  
        boolean V = Math.abs(f1y -f2y) < (f1.HEIGHT + f2.HEIGHT)/2;  
        //step3: 必须两个方向同时碰撞  
        return H&V;  
    }
    
    //绘制出每个飞行物
    public void draw(Graphics g)
    {
        g.drawImage(image, x, y, WIDTH, HEIGHT, null);
    }

}