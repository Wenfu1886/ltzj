package com;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class PlaneWars extends JPanel implements KeyListener {
    /**
        1、简单描述就是：迭代器遍历元素的时候，
           通过集合是不能修改元素的。 
        2、游戏暂停需要重新请求焦点。
        3、每一判断碰撞之后需要跳出循环，继续回到上一次碰撞的地方判断
     */
    private static final long serialVersionUID = 1L;

    // 指定按键的初始状态
    private boolean up, down, left, right, VK_Z, VK_X, VK_ENTER = false;
    // 开始状态
    private int state = START;
    // 设置游戏的各种状态的值
    private static int START = 0;
    private static int RUNNING = 1;
    private static int PAUSE = 2;
    private static int GAME_OVER = 3;
    // 时间初始化
    private int time = 0;
    // 背景的连续移动初始值
    private int backgroundmove = 0;
    // 记录英雄机的分数
    private int score = 0;
    // 设置游戏的关数的初始值
    private int stage = 0;
    
    //选飞机是否按下flag
    private boolean is_choose_flag=true;
    
    
    // 导弹的初始数量
    private int bomb = 10;
    // 英雄机的初始血量
    private int life = 20;
    // 用于产生随机数，敌机的初始X坐标
    Random r = new Random();
    // 背景音乐，爆炸 导弹 子弹音效
    private AudioClip bgmusic;
    private AudioClip blastmusic;
    private AudioClip bombmusic;
    private AudioClip bulletmusic;
    // 分数 炸弹 生命 关卡标签
    private JLabel scorelabel;
    private JLabel bomblabel;
    private JLabel lifelabel;
    private JLabel pointlabel;
    
    //设置切换
    int startX = 0,startY = 0;
      int startButtonX = 250,startButtonY = 125;
      Image[] startButtonImages = new Image[3];
      Image startImage;
      boolean isStartShow = true;
      boolean isstartButtonShow = true;
     
       int startButtonIndex = 0;
         private int start_DIST = 120,resume_DIST = 109,limUP = 240,limDOWN = 566,limRIGHT = 250,limLEFT = 10;
   
    
    
    
    
    // 使用集合保存所有对象
    private Hero hero;
    private Hero[] heros = new Hero[3];
    private String[] herobulletImages = new String[3];
    private Gift gift;
    private List<Blast> blast = new ArrayList<>();
    private List<Enemy1> enemy1 = new ArrayList<>();
    private List<Enemy2> enemy2 = new ArrayList<>();
    private List<Enemy3> enemy3 = new ArrayList<>();
    private List<Enemybullet> enemybullet = new ArrayList<>();
    private List<Herobullet> herobullet = new ArrayList<>();

    public PlaneWars() {
    	for (int i = 0; i < heros.length; i++) {
			heros[i] = new Hero(200, 600, "images/飞机"+i+".png");
		}
    	for (int i = 0; i < heros.length; i++) {
			herobulletImages[i]="images/zidan"+i+".png";
		}
    	
    	//开始切换
    		         // 加载自制按钮图标，测试图片要求：图片大小187x630
    		        for (int i = 0; i < startButtonImages.length; i++) {
    		             try {
    		                startButtonImages[i] = ImageIO.read(new File("images/飞机"+i+".png"));
    		             } catch (IOException e) {
    		                e.printStackTrace();
    		            }
    		         }
    		        
    	
    	//结束切换
    		        
    		        
    	
        URL u1 = PlaneWars.class.getResource("bgmusic.wav");
        URL u2 = PlaneWars.class.getResource("blastmusic.wav");
        URL u3 = PlaneWars.class.getResource("bombmusic.wav");
        URL u4 = PlaneWars.class.getResource("bulletmusic.wav");

        bgmusic = Applet.newAudioClip(u1);
        blastmusic = Applet.newAudioClip(u2);
        bombmusic = Applet.newAudioClip(u3);
        bulletmusic = Applet.newAudioClip(u4);

        // 初始化面板
        board();
        // 使用线程
        Thread game = new Thread() {

            public void run() {

                // 背景音乐的循环播放
                bgmusic.loop();

                while (true) {
                    /*
                     * 英雄机的移动,moving()方法需要写在外面。 因为刚开始需要控制键盘enter键。
                     * 使用到线程，使state变为RUNNING
                     */
                    moving();
                    if (state == RUNNING && stage == 1) {
                        // 控制关卡的分数
                        if (score > 3000) {
                            stage = 2;
                            pointlabel.setText("第二关");
                        }
                        // 敌机的移动
                        enemymoving(time);
                        // 英雄机子弹的移动
                        herobulletmoving(time);
                        // 敌机子弹的移动
                        enemybulletmoving(time);
                        // 生成敌机
                        enemy(time);
                        // 生成敌机子弹
                        enemyfire(time);
                        // 英雄机子弹和敌机碰撞
                        collision(time);
                        // 敌机子弹和英雄机碰撞
                        crash(time);
                        // 礼物和英雄机的碰撞
                        giftcrash(time);
                        //敌机和英雄机的碰撞
                        enemydestory(time);
                        // 时间的递增
                        time += 40;
                        try {
                            Thread.sleep(40);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // 第二关(把所有方法执行两次)
                    if (state == RUNNING && stage == 2) {
                        // 敌机的移动
                        enemymoving(time);
                        enemymoving(time);
                        // 英雄机子弹的移动
                        herobulletmoving(time);
                        herobulletmoving(time);
                        // 敌机子弹的移动
                        enemybulletmoving(time);
                        enemybulletmoving(time);
                        // 生成敌机
                        enemy(time);
                        enemy(time);
                        // 生成敌机子弹
                        enemyfire(time);
                        enemyfire(time);
                        // 英雄机子弹和敌机
                        collision(time);
                        collision(time);
                        // 敌机子弹和英雄机
                        crash(time);
                        crash(time);
                        // 礼物和英雄机的碰撞
                        giftcrash(time);
                        giftcrash(time);
                      //敌机和英雄机的碰撞
                        enemydestory(time);
                        // 时间的递增
                        time += 40;
                        try {
                            Thread.sleep(40);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    repaint();
                }
            }

            // 飞机移动和操作
            public void moving(){
                if (up) {
                    hero.MoveUp();
                }
                if (down) {
                    hero.MoveDown();
                }
                if (left) {
                    hero.MoveLeft();
                }
                if (right) {
                    hero.MoveRight();
                }
                if (VK_Z && bomb > 0) {
                    // 播放爆炸音效
                    bombmusic.play();
                    bomb--;
                    // 全屏爆炸的效果
                   
                    for (int i = 0; i <enemy1.size(); i++) {
                    	Blast blast1 = new Blast(enemy1.get(i).x,
                                enemy1.get(i).y);
                    	blast.add(blast1);
					}
                    for (int i = 0; i <enemy2.size(); i++) {
                    	Blast blast2 = new Blast(enemy2.get(i).x,
                                enemy2.get(i).y);
                    	blast.add(blast2);
					}
                    for (int i = 0; i <enemy3.size(); i++) {
                    	Blast blast3 = new Blast(enemy3.get(i).x,
                                enemy3.get(i).y);
                    	blast.add(blast3);
					}
                    // 加上全屏敌机的分数
                    score += (1 * enemy1.size() + 2 * enemy2.size() + 3 * enemy3
                            .size());
                    // 敌机集合清空
                    enemy1.clear();
                    enemy2.clear();
                    enemy3.clear();
                    // 敌机子弹清空
                    enemybullet.clear();
                    scorelabel.setText("分数:" + score);
                    // 刷新显示炸弹数
                    bomblabel.setText("炸弹" + bomb);
                }
                if (VK_X) {
                    // 生成英雄机子弹对象
                    //Herobullet herofire = hero.Herofire();
                	Herobullet herofire =new Herobullet(hero.x+heros[startButtonIndex].WIDTH/4, hero.y,herobulletImages[startButtonIndex]);
                    
                    // 控制子弹的生成速度
                    if (time % 200 == 0) {
                        bulletmusic.play();
                        herobullet.add(herofire);
                    }
                }
                if (VK_ENTER) {
                    // 判断游戏的是否为初始状态
                    if (state == START && stage == 0) {
                        state = RUNNING;
                        stage = 1;
                    }else if(state == GAME_OVER){
                        state=RUNNING;
                        Restart();
                    }
                }

            }

            //从新开始游戏
            public void Restart(){
                //所有对象集合初始化
                herobullet.clear();
                enemy1.clear();
                enemy2.clear();
                enemy3.clear();
                enemybullet.clear();
                state=1;
                score=0;
                life=20;
                bomb=10;
                stage = 1;
                //初始化面板
                board();
            }
            // 敌机和英雄子弹子弹碰撞的方法
            public void collision(long time) {
                // 英雄机子弹集合的长度
                int l1 = herobullet.size();
                // A敌机集合的长度
                int l2 = enemy1.size();
                // B敌机集合的长度
                int l3 = enemy2.size();
                // C敌机集合的长度
                int l4 = enemy3.size();
                // 遍历A敌机和子弹集合
                for (int i = 0; i < l1; i++) {
                    for (int j = 0; j < l2; j++) {
                        if (Flyer.boom(herobullet.get(i), enemy1.get(j))) {
                            // 一旦碰撞就开始播放爆炸音效
                            blastmusic.play();
                            Herobullet hb = herobullet.get(i);
                            Blast blast4 = new Blast(hb.x, hb.y);
                            // 加入爆炸对象集合
                            blast.add(blast4);
                            // 一旦碰撞，移除当前位置的英雄机子弹和敌机
                            herobullet.remove(herobullet.get(i));
                            enemy1.remove(enemy1.get(j));

                            score += 20;
                            scorelabel.setText("分数:" + score);
                            // 创建爆炸图效果
                            // 敌机和子弹集合的长度分别减1
                            l1--;
                            l2--;
                            // 返回上一次当前位置继续监测
                            i--;
                            j--;
                            // 一旦碰撞，跳出循环
                            break;
                        }
                    }
                }
                // 遍历B敌机和子弹集合
                for (int i = 0; i < l1; i++) {
                    for (int j = 0; j < l3; j++) {
                        // 如果碰撞
                        if (Flyer.boom(herobullet.get(i), enemy2.get(j))) {
                            // 一旦碰撞就开始播放爆炸音效
                            blastmusic.play();
                            // 创建爆炸图效果
                            Herobullet hb = herobullet.get(i);
                            Blast blast1 = new Blast(hb.x, hb.y);
                            // 加入爆炸对象集合
                            blast.add(blast1);
                            // 一旦碰撞，移除当前位置的英雄机子弹和敌机
                            herobullet.remove(herobullet.get(i));
                            enemy2.remove(enemy2.get(j));

                            score += 30;
                            scorelabel.setText("分数:" + score);
                            // 敌机和子弹集合的长度分别减1
                            l1--;
                            l3--;
                            // 返回上一次当前位置继续监测
                            i--;
                            j--;
                            // 一旦碰撞，跳出循环
                            break;
                        }
                    }
                }
                // 遍历C敌机和子弹集合
                for (int i = 0; i < l1; i++) {
                    for (int j = 0; j < l4; j++) {
                        if (Flyer.boom(herobullet.get(i), enemy3.get(j))) {
                            // 一旦碰撞就开始播放爆炸音效
                            blastmusic.play();
                            score += 50;
                            scorelabel.setText("分数:" + score);
                            // 创建爆炸图效果
                            Herobullet hb = herobullet.get(i);
                            Blast blast1 = new Blast(hb.x, hb.y);
                            // 加入爆炸对象集合
                            blast.add(blast1);
                            // 一旦碰撞，移除当前位置的英雄机子弹和敌机
                            herobullet.remove(herobullet.get(i));
                            enemy3.remove(enemy3.get(j));
                            // 敌机和子弹集合的长度分别减1
                            l1--;
                            l4--;
                            // 返回上一次当前位置继续监测
                            i--;
                            j--;
                            // 一旦碰撞，跳出循环
                            break;
                        }
                    }
                }
            }
            //同样开启对于敌机和主角机的碰撞-------------------------
            public void enemydestory(long time) {
            	int  list1 = enemy1.size();
            	int  list2 = enemy2.size();
            	int  list3 = enemy3.size();
            	
            	//enemy1的碰撞
            	for (int i = 0; i < list1; i++) {
					if(Flyer.boom(enemy1.get(i), hero)) {
						Blast blast1 = new Blast(enemy1.get(i).x, enemy1.get(i).y);
						blast.add(blast1);
						enemy1.remove(i);
						list1--;
						
						 life--;
	                        lifelabel.setText("生命:" + life);
	                        // 如果生命减少到0,游戏结束
	                        if (life <= 0) {
	                            state = GAME_OVER;
	                        }
  
					}
				}
            	
            	
            	//enemy2的碰撞
            	for (int i = 0; i < list2; i++) {
					if(Flyer.boom(enemy2.get(i), hero)) {
						Blast blast2 = new Blast(enemy2.get(i).x, enemy2.get(i).y);
						blast.add(blast2);
						enemy2.remove(i);
						list2--;
						
						 life--;
	                        lifelabel.setText("生命:" + life);
	                        // 如果生命减少到0,游戏结束
	                        if (life <= 0) {
	                            state = GAME_OVER;
	                        }
  
					}
				}
            	
            	//enemy3的碰撞
            	for (int i = 0; i < list3; i++) {
					if(Flyer.boom(enemy3.get(i), hero)) {
						Blast blast3 = new Blast(enemy3.get(i).x, enemy3.get(i).y);
						blast.add(blast3);
						enemy3.remove(i);
						list3--;
						
						 life--;
	                        lifelabel.setText("生命:" + life);
	                        // 如果生命减少到0,游戏结束
	                        if (life <= 0) {
	                            state = GAME_OVER;
	                        }
  
					}
				}
            	
            }
            
        
            // 判断礼物和英雄机碰撞的方法
            public void giftcrash(long time) {
                // 如果碰撞
                if (Flyer.boomgift(hero, gift)) {
                    // 礼物初始化
                    gift = new Gift(0, 0);
                    // 获得礼物加生命，加导弹
                    bomb+=3;
                    life+=1;
                    if(life>=20){
                    	life = 20;
                    }
                    lifelabel.setText("生命:" + life);
                    bomblabel.setText("导弹:" + bomb);
                }
            }

            // 敌机子弹和英雄机的碰撞方法
            public void crash(long time) {
                int l3 = enemybullet.size();
                for (int i = 0; i < l3; i++) {
                    if (Flyer.boom(enemybullet.get(i), hero)) {
                        enemybullet.remove(enemybullet.get(i));
                        life--;
                        lifelabel.setText("生命:" + life);
                        // 如果生命减少到0,游戏结束
                        if (life <= 0) {
                            state = GAME_OVER;
                        }
                        l3--;
                        i--;
                        break;
                    }
                }
            }

            // 敌机移动
            public void enemymoving(long time) {
                for (int i = 0; i < enemy1.size(); i++) {
                    enemy1.get(i).move(time);
                }
                for (int i = 0; i < enemy2.size(); i++) {
                    enemy2.get(i).move(time);
                }
                for (int i = 0; i < enemy3.size(); i++) {
                    enemy3.get(i).move(time);
                }
                gift.move(time);
            }

            public void herobulletmoving(long time) {
                for (int i = 0; i < herobullet.size(); i++) {
                    herobullet.get(i).move(time);
                }

            }

            public void enemybulletmoving(long time) {
                for (int i = 0; i < enemybullet.size(); i++) {
                    enemybullet.get(i).move(time);
                }
            }

            public void enemyfire(long time) {
                // 敌机A发射子弹
                if (time % 2000 == 0) {
                    for (int i = 0; i < enemy1.size(); i++) {
                        Enemybullet b = enemy1.get(i).enemyfire();
                        enemybullet.add(b);
                    }
                }
                // 敌机B发射子弹
                if (time % 3000 == 0) {

                    for (int i = 0; i < enemy2.size(); i++) {
                        Enemybullet b = enemy2.get(i).enemyfire();
                        enemybullet.add(b);
                    }
                }
                // 敌机C发射子弹
                if (time % 4000 == 0) {

                    for (int i = 0; i < enemy3.size(); i++) {
                        Enemybullet b = enemy3.get(i).enemyfire();
                        enemybullet.add(b);
                    }
                }
            }

            public void enemy(long time) {
                // 生成敌机1的时间
                if (time % 600 == 0) {
                    Enemy1 e = new Enemy1(r.nextInt(450), 0);
                    enemy1.add(e);
                }
                // 生成敌机2的时间
                if (time % 400 == 0) {
                    Enemy2 e = new Enemy2(r.nextInt(450), 0);
                    enemy2.add(e);
                }
                // 生成礼物的时间
                if (score != 0 && time % 5000==0) {
                    gift = new Gift(r.nextInt(450), 0);
                    System.out.println("!!!!!!!!!!!!!!!!!!!生成礼物!!!!!!!!!!!!!!!!!");
                }
                // 生成敌机3的时间
                if (time % 800 == 0) {
                    Enemy3 e = new Enemy3(r.nextInt(450), 0);
                    enemy3.add(e);
                }

            }

        };
        // 启动线程
        game.start();
    }
    
       

    /*
     * 连续背景的绘制
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            BufferedImage image;
            if (backgroundmove > 600) {
                backgroundmove = 0;
            }
            image = ImageIO.read(new FileInputStream("images/map.jpg"));// 加载背景
            g.drawImage(image, 0, backgroundmove, 600, 800, null);
            backgroundmove += 1;
            g.drawImage(image, 0, -600 + backgroundmove, 600, 800, null); // 使得背景是持续的
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 分数、炸弹、生命面板
    public void board() {
        // 清除容器内所有内容
        this.removeAll();
        this.addKeyListener(this);
        // 初始化礼物对象
        gift = new Gift(0, 0);
        // 关卡标签
        pointlabel = new JLabel("第一关");
        pointlabel.setForeground(new Color(3,76,170));
        
        Font font = new Font("黑体", Font.PLAIN, 20);
        pointlabel.setFont(font);
        // 分数标签
        scorelabel = new JLabel("分数:" + score);
        // 设置背景颜色
        scorelabel.setForeground(new Color(52,162,198));
        // 炸弹标签
        bomblabel = new JLabel("导弹:" + bomb);
        bomblabel.setForeground(new Color(52,162,198));
        // 生命标签
        lifelabel = new JLabel("生命:" + life);
        lifelabel.setForeground(new Color(52,162,198));
        // 游戏继续按钮
        final JButton gameon = new JButton("继续");
        gameon.setForeground(new Color(52,162,198));
        // 设置按钮为透明
        gameon.setContentAreaFilled(false);
        // 游戏暂停按钮
        JButton gamestop = new JButton("暂停");
        gamestop.setForeground(new Color(52,162,198));
        gamestop.setContentAreaFilled(false);
        // 继续按钮添加监听
        gameon.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (state == PAUSE) {
                    state = RUNNING;
                    /*
                     * 重新使JPanel成为焦点！一定需要从新请求焦点。不然暂停一次键盘就直接失灵了
                     */
                    PlaneWars.this.requestFocusInWindow();
                }
            }
        });
        // 暂停按钮添加监听
        gamestop.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (state == RUNNING) {
                    state = PAUSE;
                }
            }
        });
        // 新建一个面板，把按钮和标签都加入到面板中间
        JPanel jp = new JPanel();
        // 设置背景颜色为黑色
        jp.setBackground(Color.BLACK);
        // 使用浮动布局管理器
        jp.setLayout(new FlowLayout(FlowLayout.LEADING, 30, 10));
        jp.add(pointlabel);
        jp.add(bomblabel);
        jp.add(scorelabel);
        jp.add(lifelabel);
        jp.add(gameon);
        jp.add(gamestop);
        // JPanel 面板添加这个面板
        this.add(jp);
        // 英雄机的初始位置
        hero = new Hero(230, 600,heros[startButtonIndex].image);
        
    }

    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
            //绘画
	           //清空原有画面，重新画dialogImage
	       
	  
        // 如果为初始状态
        if (state == START) {
            paintstart(g);
            // 如果为游戏运行状态
        } else if (state == RUNNING) {
            // 绘制英雄机
            painthero(g);
            // 绘制敌机
            paintenemy(g);
            // 绘制一组敌机子弹
            paintenemybullets(g);
            // 绘制英雄机子弹
            paintherobullets(g);
            // 绘制爆炸效果和移除爆炸效果
            paintblast(g);
            // 绘制礼物
            paintgift(g);
        } else if (state == PAUSE) {
            g.setFont(new Font("黑体", Font.BOLD, 50));
            g.setColor(Color.RED);
            g.drawString("游戏暂停!!!", 120, 330);
        }
        // 如果为游戏结束状态
        else if (state == GAME_OVER) {
            paintover(g);
        }

    }

    // 该方法为绘制游戏结束界面
    public void paintover(Graphics g) {
    	 super.paint(g);
        g.drawImage(ImageRead.b1, 0, 0, 500, 800, null);
        Font font1 = new Font("黑体", Font.ITALIC, 60);
        Font font2 = new Font("黑体", Font.PLAIN, 30);
        g.setColor(Color.RED);
        g.setFont(font1);
        g.drawString("GAME OVER", 100, 300);
        g.setFont(font2);
        g.drawString("score:" + score + "---你已经很棒啦!", 80, 400);
        g.setFont(font2);
        g.drawString("按ENTER键从新开始游戏", 100, 500);
    }

    // 该方法为绘制初始界面
    public void paintstart(Graphics g) {
    	
    	 if(isStartShow){
             g.drawImage(startImage,startX,startY,this);
              if(isstartButtonShow){
                 g.drawImage(startButtonImages[startButtonIndex],startButtonX,startButtonY,this);
             }
         }
    	 g.drawImage(ImageRead.b1, 0, 0, 500, 800, null);
         Font font = new Font("宋体", Font.BOLD, 20);
         g.setColor(Color.red);
         g.setFont(font);
         g.drawString("游戏规则:", 50, 450);
         g.drawString("按ENTER键开始游戏", 50, 500);
         g.drawString("X:发射子弹,Z:使用超级技能", 50, 550);
         g.drawString("方向键:↑,↓,←,→", 50, 600);
         g.drawString("创作团队:The First", 50, 650);
         g.drawString("按←,→选择你的英雄:", 50, 100);
    }

    // 绘制礼物
    public void paintgift(Graphics g) {
        // 因为礼物的初始位置为坐标为0和0,
        if (gift.x != 0 && gift.y != 0) {
            g.drawImage(ImageRead.gift, gift.x, gift.y, 20, 20, null);
        }
    }

    // 绘制英雄机
    public void painthero(Graphics g) {

        g.drawImage(heros[startButtonIndex].image, hero.x, hero.y, 60, 60, null);
    }

    // 绘制爆炸图
    public void paintblast(Graphics g) {

        int l3 = blast.size();
        for (int i = 0; i < l3; i++) {
            Blast b = blast.get(i);
            // 绘制之后立即移除
            b.draw(g);
            blast.remove(b);
            l3--;
            i--;
            break;
        }
    }

    // 绘制所有敌机
    public void paintenemy(Graphics g) {

        for (int i = 0; i < enemy1.size(); i++) {
            Enemy1 e = enemy1.get(i);
            e.draw(g);

        }

        for (int i = 0; i < enemy2.size(); i++) {
            Enemy2 e = enemy2.get(i);
            e.draw(g);

        }

        for (int i = 0; i < enemy3.size(); i++) {
            Enemy3 e = enemy3.get(i);
            e.draw(g);

        }
    }

    // 绘制A类敌机子弹
    public void paintenemybullets(Graphics g) {

        for (int i = 0; i < enemybullet.size(); i++) {
            Enemybullet eb = enemybullet.get(i);
            eb.draw(g);

        }
    }

    // 绘制英雄机子弹
    public void paintherobullets(Graphics g) {

        for (int i = 0; i < herobullet.size(); i++) {
            Herobullet hb = herobullet.get(i);
            hb.draw(g);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // 键盘按下
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_SPACE:
        	if(state == RUNNING){
        		state = PAUSE;
        	}else if(state == PAUSE){
        		state = RUNNING;
        	}
        	break;
        case KeyEvent.VK_UP:
            up = true;
            break;
        case KeyEvent.VK_DOWN:
            down = true;
            break;
        case KeyEvent.VK_LEFT:
        	if(isStartShow&&is_choose_flag){
        		                  startButtonX -= start_DIST;
        		               startButtonIndex++;
        		                 if(startButtonIndex > startButtonImages.length-1){
        		                     startButtonIndex = 0;
        		                    startButtonX = limRIGHT;
        		                }
        		                
        		            }
            left = true;
            break;
        case KeyEvent.VK_RIGHT:
        	if(isStartShow&&is_choose_flag){    //RIGHT按钮切换开始游戏界面选项
        		                 startButtonX += start_DIST;
        		                  startButtonIndex--;
        		                  if(startButtonIndex < 0){
        		                     startButtonIndex = 2;
        		                      startButtonX = limLEFT;
        		                  }
        		              }
            right = true;
            
            break;
        case KeyEvent.VK_Z:
            VK_Z = true;
            break;
        case KeyEvent.VK_X:
            VK_X = true;
            break;
        case KeyEvent.VK_ENTER:
        	is_choose_flag=false;
            VK_ENTER = true;
            break;
        default:
            break;

        }
        repaint();
    }

    // 键盘释放
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
            up = false;
            break;
        case KeyEvent.VK_DOWN:
            down = false;
            break;
        case KeyEvent.VK_LEFT:
            left = false;
            break;
        case KeyEvent.VK_RIGHT:
            right = false;
            break;
        case KeyEvent.VK_Z:
            VK_Z = false;
            break;
        case KeyEvent.VK_X:
            VK_X = false;
            break;
        case KeyEvent.VK_ENTER:
            VK_ENTER = false;
            break;
        default:
            break;
        }
    }
}