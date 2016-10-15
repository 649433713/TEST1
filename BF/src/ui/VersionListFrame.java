package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.*;

import runner.ClientRunner;

public class VersionListFrame extends JFrame {
	String selection = null;
	String string ;

	public VersionListFrame(String ID, String[] fileList) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle(ID);
		setSize(400,300);
		int windowWidth = this.getWidth();                     //获得窗口宽  
		 int windowHeight = this.getHeight();                   //获得窗口高  
		 Toolkit kit = Toolkit.getDefaultToolkit();              //定义工具包  
		 Dimension screenSize = kit.getScreenSize();             //获取屏幕的尺寸  
		 int screenWidth = screenSize.width;                     //获取屏幕的宽  
		 int screenHeight = screenSize.height;                   //获取屏幕的高  
		this.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//设置窗口居中显示
		getContentPane().setLayout(new GridLayout(2, 1));

		JComboBox comboBox = new JComboBox(fileList);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				string= (String) comboBox.getSelectedItem();
			}
		});
		comboBox.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		getContentPane().add(comboBox);

		JPanel panel = new JPanel();
		getContentPane().add(panel);

		JButton button = new JButton("确认");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selection = string;
				dispose();
			}
		});
		panel.add(button);

		JButton button_1 = new JButton("取消");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(button_1);
		setVisible(true);
	}
	public String getChoise() {
		// TODO 自动生成的方法存根
		return selection;
	}
}
