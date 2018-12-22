package com;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

/*
 * 所有资源加载类
 */
public class ImageRead {
	public static BufferedImage enemyA;// 敌机一
	public static BufferedImage enemyB;// 敌机二
	public static BufferedImage enemyC;// 敌机三
	public static BufferedImage hero;// 英雄机
	public static BufferedImage herobullet;// 英雄机子弹
	public static BufferedImage enemybullet;// 敌机子弹
	public static BufferedImage gift;// 礼物
	public static BufferedImage blast;// 爆炸
	public static BufferedImage map1;// 地图
	public static BufferedImage b1;
	public static BufferedImage icon;

	/*
	 * 资源加载使用静态初始化块 因为只需要启动的时候使用一次
	 */
	static {
		try {
			//敌人的飞机
			enemyA = ImageIO.read(new FileInputStream("images/enemy1.png"));
			enemyB = ImageIO.read(new FileInputStream("images/enemy2.png"));
			enemyC = ImageIO.read(new FileInputStream("images/enemy3.png"));
			//自己的飞机
			hero = ImageIO.read(new FileInputStream("images/hero.png"));
			//自己的飞机子弹
			herobullet = ImageIO.read(new FileInputStream("images/zidan0.png"));
			//敌人的子弹
			enemybullet = ImageIO.read(new FileInputStream("images/enemybullet.png"));
			//礼物奖励
			gift = ImageIO.read(new FileInputStream("images/gift.png"));
			//爆炸效果，敌方飞机打爆动画
			blast = ImageIO.read(new FileInputStream("images/blast.png"));
			//背景图片
			map1 = ImageIO.read(new FileInputStream("images/map.jpg"));
			//游戏界面图标
			icon = ImageIO.read(new FileInputStream("images/icon.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}