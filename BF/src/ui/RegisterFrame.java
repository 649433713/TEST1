package ui;

import javax.swing.*;

import runner.ClientRunner;
import ui.LogFrame.querenActionListner;
import ui.LogFrame.quxiaoActionListner;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;

public class RegisterFrame extends JFrame{
	private JTextField textField;
	private JPasswordField passwordField;
	public RegisterFrame(){
		setAlwaysOnTop(true);
		setVisible(true);
		setSize(400,300);
		int windowWidth = this.getWidth();                     //获得窗口宽  
		 int windowHeight = this.getHeight();                   //获得窗口高  
		 Toolkit kit = Toolkit.getDefaultToolkit();              //定义工具包  
		 Dimension screenSize = kit.getScreenSize();             //获取屏幕的尺寸  
		 int screenWidth = screenSize.width;                     //获取屏幕的宽  
		 int screenHeight = screenSize.height;                   //获取屏幕的高  
		this.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//设置窗口居中显示
		setTitle("欢迎");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(new GridLayout( 3, 1));
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		
		JLabel label = new JLabel("用户名");
		
		textField = new JTextField();
		textField.setColumns(16);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_1.add(label);
		panel_1.add(textField);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		
		JLabel label_1 = new JLabel(" 密码 ");
		panel_2.add(label_1);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(16);
		panel_2.add(passwordField);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		JButton button = new JButton("注册");
		button.addActionListener(new zhuceActionListner());
		panel.add(button);
		
		JButton button_1 = new JButton("取消");
		button_1.addActionListener(new quxiaoActionListner());
		panel.add(button_1);
	}
	
	class zhuceActionListner implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			String userName = textField.getText();
			String password = new String(passwordField.getPassword());
			try {
				boolean b = ClientRunner.remoteHelper.getUserService().Register(userName, password);
				if (b) {
					JOptionPane.showMessageDialog(RegisterFrame.this, "注册成功！", "提示",JOptionPane.PLAIN_MESSAGE);  
				} else {
					JOptionPane.showMessageDialog(RegisterFrame.this, "该用户名已被申请！", "提示",JOptionPane.PLAIN_MESSAGE);  
				}
			} catch (RemoteException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}			
		}		
	}
	class quxiaoActionListner implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			setVisible(false);
		}		
	}
}
