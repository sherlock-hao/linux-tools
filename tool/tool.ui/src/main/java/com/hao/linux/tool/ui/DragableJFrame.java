package com.hao.linux.tool.ui;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JWindow;

public class DragableJFrame extends JWindow {
	@Override
	public Component add(Component comp) {
		// TODO Auto-generated method stub
		return super.add(comp);
	}

	private int xOld;
	private int yOld;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DragableJFrame() {
		this.setType(JFrame.Type.UTILITY);
		this.setAlwaysOnTop(true);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xOld = e.getX();// 记录鼠标按下时的坐标
				yOld = e.getY();
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int xOnScreen = e.getXOnScreen();
				int yOnScreen = e.getYOnScreen();
				int xx = xOnScreen - xOld;
				int yy = yOnScreen - yOld;
				DragableJFrame.this.setLocation(xx, yy);// 设置拖拽后，窗口的位置
			}
		});
	}

}
