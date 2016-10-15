package ui;

import javax.swing.*;

import runner.ClientRunner;

import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class downloadFrame extends JFrame{

	String string ;

	public downloadFrame(String ID, String[] fileList) {
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
				MainFrame.project=string;

				dispose();
			}
		});

		JButton button_2 = new JButton("删除");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(downloadFrame.this, "确认删除吗？", "文件存在", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);// 显示一个对话框来实现是否删除项目
				if (i == JOptionPane.YES_OPTION) {
					try {
						if (ClientRunner.remoteHelper.getIOService().delete(ID, string)) {
							JOptionPane.showMessageDialog(downloadFrame.this, "成功删除！", "提示", JOptionPane.PLAIN_MESSAGE);
						}
					} catch (RemoteException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				
				}
				setVisible(false);
			}
		});
		panel.add(button_2);
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





	
}
