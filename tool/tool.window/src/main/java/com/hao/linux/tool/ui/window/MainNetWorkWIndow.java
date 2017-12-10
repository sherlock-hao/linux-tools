package com.hao.linux.tool.ui.window;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.hao.linux.tool.cpu.CpuCalculator;
import com.hao.linux.tool.memory.MemoryCalculator;
import com.hao.linux.tool.ui.DragableJFrame;
import com.hao.linux.tools.network.NetworkCalculator;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class MainNetWorkWIndow {
	DragableJFrame frame;

	Label uploadLabel;
	Label downloadLabel;
	JLabel memoryLabel;
	JProgressBar cpuProgressBar;
	JProgressBar memoryProgressBar;
	
	private JLabel cpuLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainNetWorkWIndow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainNetWorkWIndow() {
		initialize();
		Thread thread = new Thread() {
			NetworkCalculator networkCalculator = new NetworkCalculator();
			MemoryCalculator memoryCalculator = new MemoryCalculator();
			CpuCalculator cpuCalculator = new CpuCalculator();

			int delay = 1000; // milliseconds

			@Override
			public void run() {
				while (true) {
					cpuCalculator.update();
					networkCalculator.update();
					memoryCalculator.update();
					uploadLabel.setText(networkCalculator.getFixedTXSpeed());
					downloadLabel.setText(networkCalculator.getFixedRXSpeed());
					
					String cpuValueStr=  cpuCalculator.getFixedInfo();
					String memValueStr=  memoryCalculator.getFixedInfo();
					
					int cpuPercentage = cpuCalculator.getUsedPercentage().intValue();
					int memPercentage = memoryCalculator.getPercentage().intValue();
					setProgressColor(cpuProgressBar,cpuPercentage);
					setProgressColor(memoryProgressBar,memPercentage);
					
					cpuProgressBar.setValue(cpuPercentage);
					cpuProgressBar.setString(cpuValueStr);
					
					memoryProgressBar.setValue(memPercentage);
					memoryProgressBar.setString(memValueStr);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
	}
	
	private void setProgressColor(JProgressBar progressBar,int value) {
		if (value>90) {
			progressBar.setForeground(Color.RED);
		}else if (value>80) {
			progressBar.setForeground(Color.ORANGE);
		}else {
			progressBar.setForeground(Color.GREEN);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new DragableJFrame();
		frame.setBounds(100, 100, 220, 60);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setPreferredSize(new Dimension(60, 60));
		frame.getContentPane().add(panel, BorderLayout.WEST);

		JPanel panel1 = new JPanel();
		panel1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel1.setBackground(Color.LIGHT_GRAY);
		panel1.setPreferredSize(new Dimension(60, 60));
		frame.getContentPane().add(panel1, BorderLayout.CENTER);

		cpuLabel = new JLabel("CPU");
		cpuLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel1.add(cpuLabel);
		cpuLabel.setPreferredSize(new Dimension(60, 20));

		cpuProgressBar = new JProgressBar();
		cpuProgressBar.setPreferredSize(new Dimension(55, 20));
		cpuProgressBar.setStringPainted(true);
		panel1.add(cpuProgressBar);


		memoryLabel = new JLabel("MEM");
		memoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		memoryLabel.setPreferredSize(new Dimension(60, 20));
		panel.add(memoryLabel);

		memoryProgressBar = new JProgressBar();
		memoryProgressBar.setPreferredSize(new Dimension(55, 20));
		memoryProgressBar.setStringPainted(true);
		panel.add(memoryProgressBar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setPreferredSize(new Dimension(100, 60));
		frame.getContentPane().add(panel_1, BorderLayout.EAST);

		Label label = new Label("▲");
		label.setPreferredSize(new Dimension(15, 20));
		panel_1.add(label);

		uploadLabel = new Label("");
		uploadLabel.setFont(UIManager.getFont("Button.font"));
		uploadLabel.setPreferredSize(new Dimension(70, 20));
		panel_1.add(uploadLabel);

		Label label_1 = new Label("▼");
		label_1.setPreferredSize(new Dimension(15, 20));
		panel_1.add(label_1);

		downloadLabel = new Label("");
		downloadLabel.setFont(UIManager.getFont("Button.font"));
		downloadLabel.setPreferredSize(new Dimension(70, 20));
		panel_1.add(downloadLabel);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width - 200, screenSize.height - 150);

		frame.setVisible(true);
	}

}
