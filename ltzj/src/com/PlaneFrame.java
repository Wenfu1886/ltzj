package com;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author first
 * 
 */
public class PlaneFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    // 创建JPlane的对象
    PlaneWars pw = new PlaneWars();

    // 创建PlaneFrame的构造器，初始化对象
    public PlaneFrame() {
        // 将JPanel的焦点状态设置为true。
        pw.setFocusable(true);
        // 让JFrame先获得焦点，然后JPanel在获得焦点
        pw.requestFocus();
        this.add(pw, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // 创建主窗口的对象
        PlaneFrame pf = new PlaneFrame();
        // 设置主窗体的名字
        pf.setTitle("飞机大战");
        // 设置主窗体的大小
        pf.setSize(500, 700);
        // 设置主窗体居中显示
        pf.setLocationRelativeTo(null);
        // 设置游戏界面图标
        pf.setIconImage(ImageRead.icon);
        // 设置主窗体的关闭和退出
        //pf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //确认对话框
        pf.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e){
        		super.windowClosing(e);
        		int jieguo = JOptionPane.showConfirmDialog(null, "真的要退出吗？","退出窗口",JOptionPane.YES_NO_OPTION);
                if(jieguo == JOptionPane.YES_OPTION){
                	System.exit(0);
                }
        	}
		});
        // 显示主窗体
        pf.setVisible(true);
        // 主窗体不可拉伸
        pf.setResizable(false);
    }

}