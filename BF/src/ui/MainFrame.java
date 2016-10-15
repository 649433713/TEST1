package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

import java.awt.event.*;
import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

import runner.ClientRunner;

public class MainFrame extends JFrame {
	public static ArrayList<String> temp = new ArrayList<String>() ;
	private ArrayList<String> temp2 = new ArrayList<String>();
	private JTextArea textArea = new JTextArea();
	private JTextField txtInputSection;
	private JPanel panel = new JPanel();
	private JTextPane textPane = new JTextPane();
	private File file = null;
	public static String ID;
	public static String project;
	
	public String getCode() {
		return textArea.getText().trim();
	}

	public MainFrame() {
		// 创建窗体

		getContentPane().setLayout(new BorderLayout());

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);

		JMenu mnSave = new JMenu("Save");
		fileMenu.add(mnSave);

		JMenuItem mntmLocalSave = new JMenuItem("Local Save");
		mntmLocalSave.addActionListener(new SaveActionListener());
		mnSave.add(mntmLocalSave);

		JMenuItem mntmUploadToServer = new JMenuItem("Upload to Server");
		mntmUploadToServer.addActionListener(new UploadActionListener());
		mnSave.add(mntmUploadToServer);

		JMenuItem mntmDownloadFormServer = new JMenuItem("Download");
		fileMenu.add(mntmDownloadFormServer);
		mntmDownloadFormServer.addActionListener(new DownloadActionListener());
		JMenuItem LogoutMenuItem = new JMenuItem("Log out");

		setJMenuBar(menuBar);

		JMenu mnRun = new JMenu("Run");
		menuBar.add(mnRun);

		JMenuItem mntmExecute = new JMenuItem("Execute");
		mntmExecute.addActionListener(new ExecuteActionListener());
		mnRun.add(mntmExecute);

		JMenu mnVersion = new JMenu("Version");
		menuBar.add(mnVersion);

		JMenuItem mntmGetVersionList = new JMenuItem("Get Version List");
		mntmGetVersionList.addActionListener(new GetVersionListActionListener());
		mnVersion.add(mntmGetVersionList);

		JMenu mnUser = new JMenu("User");
		menuBar.add(mnUser);

		JMenuItem mntmLogIn = new JMenuItem("Log in");
		mntmLogIn.addActionListener(new LoginActionListener());
		mnUser.add(mntmLogIn);
		mnUser.add(LogoutMenuItem);

		JMenuItem mntmNewMenuItem = new JMenuItem("Register");
		mntmNewMenuItem.addActionListener(new RegisterActionListener());
		mnUser.add(mntmNewMenuItem);

		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		LogoutMenuItem.addActionListener(new LogoutActionListener());
		
		String name[]={"撤销","恢复" };
	    JPopupMenu mainMenu = new JPopupMenu();//右键弹出菜单类
	    JMenuItem item[] = new JMenuItem[2];
	    for(int i=0;i<2;++i){
            item[i]=new JMenuItem(name[i]);
            mainMenu.add(item[i]);
            item[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
				            if(e.getSource()==item[0]){//撤销
				                undo();
				            }else if(e.getSource()==item[1]){//恢复
				                redo();
				            
				        }
				    }
				
			});
        }
        textArea.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                maybeShowPopup(e);
            }
            public void mouseReleased(MouseEvent e){
                maybeShowPopup(e);
            }
            private void maybeShowPopup(MouseEvent e){
                if(e.isPopupTrigger()){//如果有弹出菜单
                    mainMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

		getContentPane().add(textArea, BorderLayout.CENTER);
		textArea.setRows(100);
		textArea.setLineWrap(true);
		JScrollPane pane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		getContentPane().add(pane);
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		txtInputSection = new JTextField();
		txtInputSection.setText("Input Section");
		panel.add(txtInputSection);
		txtInputSection.setColumns(10);

		textPane.setText("Output Section");
		panel.add(textPane);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		int windowWidth = this.getWidth(); // 获得窗口宽
		int windowHeight = this.getHeight();

		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高

		setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
		setVisible(true);
		Thread thread = new Thread(new VersionList(this));
		thread.start();
		
	}
	/*
	 * 一大坨监控
	 */

	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			Savelocal();
		}
	}

	class OpenActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Open();
		}
	}

	class NewActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			if (ID == null) {
				New();
			} else {
				NewProject();
			}
			textArea.setText("");
		}
	}

	class LogoutActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			try {
				boolean b = ClientRunner.remoteHelper.getUserService().logout();
				if (b) {
					JOptionPane.showMessageDialog(MainFrame.this, "注销成功！", "提示", JOptionPane.PLAIN_MESSAGE);
					ID = null;
					project = null;
				} else {
					JOptionPane.showMessageDialog(MainFrame.this, "当前无用户登录！", "提示", JOptionPane.PLAIN_MESSAGE);
				}
			} catch (RemoteException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
	}

	class ExecuteActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			String code = textArea.getText();
			String param = txtInputSection.getText();
			try {
				String output = ClientRunner.remoteHelper.getExecuteService().execute(code, param);
				textPane.setText(output);
			} catch (RemoteException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
	}

	class LoginActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			LogFrame logFrame = new LogFrame(ID);
		}
	}

	class RegisterActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			new RegisterFrame();
		}
	}

	class DownloadActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (ID != null) {
				Download();
			} else {
				JOptionPane.showMessageDialog(MainFrame.this, "当前未登录！", "提示", JOptionPane.PLAIN_MESSAGE);
			}

		}
	}

	class UploadActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (ID != null) {
				if (project != null) {
					UpdateVersion();
				} else {
					Upload();
				}
			} else {
				JOptionPane.showMessageDialog(MainFrame.this, "当前未登录！", "提示", JOptionPane.PLAIN_MESSAGE);
			}

		}

	}

	class GetVersionListActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (ID != null) {
				if (project != null) {
					GetVersionList();
				} else {
					JOptionPane.showMessageDialog(MainFrame.this, "当前未选择项目！", "提示", JOptionPane.PLAIN_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(MainFrame.this, "当前未登录！", "提示", JOptionPane.PLAIN_MESSAGE);
			}

		}

	}


	/*
	 * 一大堆方法
	 */
	private void Open() {
		JFileChooser chooser = new JFileChooser();
		chooser.setBounds(100, 100, 450, 300);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("仅支持文本文件格式", "txt", "doc", "docx", "java");
		chooser.setFileFilter(filter);
		int value = chooser.showOpenDialog(MainFrame.this);
		if (value == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			System.out.println(chooser.getSelectedFile().getAbsolutePath());

			try {
				FileReader fileReader = new FileReader(file);
				BufferedReader reader = new BufferedReader(fileReader);
				String string = null;
				while ((string = reader.readLine()) != null) {
					textArea.append(string + "\r\n");
				}
				reader.close();
				JOptionPane.showMessageDialog(MainFrame.this, "成功打开文件！", "提示", JOptionPane.PLAIN_MESSAGE);

			} catch (IOException e1) {
				System.out.println("未选择文件！");
			}
		}

	}

	private void New() {
		// TODO 自动生成的方法存根
		JFileChooser chooser = new JFileChooser();
		chooser.setBounds(100, 100, 450, 300);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int value = chooser.showDialog(MainFrame.this, "选择");
		if (value == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			JOptionPane.showMessageDialog(MainFrame.this, "成功选择文件路径！", "提示", JOptionPane.PLAIN_MESSAGE);
			System.out.println(chooser.getSelectedFile().getAbsolutePath());
		}
	}

	private void Savelocal() {
		if (file == null) {
			JOptionPane.showMessageDialog(MainFrame.this, "请先选择保存路径！", "提示", JOptionPane.PLAIN_MESSAGE);
			New();
		} else if (file.isDirectory()) {
			String string = (String) JOptionPane.showInputDialog(MainFrame.this, "请输入文件名：\n", "保存",
					JOptionPane.PLAIN_MESSAGE, null, null, "在这输入");
			File file2 = new File(file.getAbsolutePath() + "\\" + string);
			try {
				if (!file2.exists()) {
					file2.createNewFile();
					String ss = textArea.getText();// 获得要保存的文本（可以包含回车）
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(file2));
						bw.write(ss);
						bw.close();
						JOptionPane.showMessageDialog(MainFrame.this, "成功保存文件！", "提示", JOptionPane.PLAIN_MESSAGE);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				} else {
					int i = JOptionPane.showConfirmDialog(MainFrame.this, "文件已经存在,是否覆盖!", "文件存在",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);// 显示一个对话框来实现是否覆盖源文件
					if (i == JOptionPane.YES_OPTION) {
						file2.createNewFile();
						String ss = textArea.getText();// 获得要保存的文本（可以包含回车）
						try {
							BufferedWriter bw = new BufferedWriter(new FileWriter(file2));
							bw.write(ss);
							bw.close();
							JOptionPane.showMessageDialog(MainFrame.this, "成功保存文件！", "提示", JOptionPane.PLAIN_MESSAGE);
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			String ss = textArea.getText();// 获得要保存的文本（可以包含回车）
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(ss);
				bw.close();
				JOptionPane.showMessageDialog(MainFrame.this, "成功保存文件！", "提示", JOptionPane.PLAIN_MESSAGE);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	private void Upload() {

		String string = (String) JOptionPane.showInputDialog(MainFrame.this, "请输入工程名：\n", "保存",
				JOptionPane.PLAIN_MESSAGE, null, null, "在这输入");
		try {
			if (!ClientRunner.remoteHelper.getIOService().fileIsExist(string, ID)) {
				String ss = textArea.getText();// 获得要保存的文本（可以包含回车）

				ClientRunner.remoteHelper.getIOService().writeFile(ss, ID, string);

				JOptionPane.showMessageDialog(MainFrame.this, "成功保存工程！", "提示", JOptionPane.PLAIN_MESSAGE);
				project = string;
			} else {
				int i = JOptionPane.showConfirmDialog(MainFrame.this, "工程已经存在,是否覆盖!", "文件存在", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);// 显示一个对话框来实现是否覆盖源文件
				if (i == JOptionPane.YES_OPTION) {
					String ss = textArea.getText();// 获得要保存的文本（可以包含回车）
					ClientRunner.remoteHelper.getIOService().writeFile(ss, ID, string);
					JOptionPane.showMessageDialog(MainFrame.this, "成功保存工程！", "提示", JOptionPane.PLAIN_MESSAGE);
					project = string;
				}
			}
		} catch (HeadlessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	private void UpdateVersion() {
		// TODO 自动生成的方法存根
		String ss = textArea.getText();// 获得要保存的文本（可以包含回车）

		try {
			ClientRunner.remoteHelper.getIOService().updateVersion(ss, ID, project);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	private void Download() {
		try {
			String fileList = ClientRunner.remoteHelper.getIOService().readFileList(ID);
			String list[] = fileList.split("&");
			downloadFrame sDownloadFrame = new downloadFrame(ID, list);
			sDownloadFrame.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
					String code = null;

					try {
						code = ClientRunner.remoteHelper.getIOService().readFile(MainFrame.ID, MainFrame.project);
					} catch (RemoteException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}

					textArea.setText(code);
				}
			});
		} catch (RemoteException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	private String GetVersionList() {
		String selection = null;
		try {
			String fileList = ClientRunner.remoteHelper.getIOService().readVersion(ID, project);
			String list[] = fileList.split("&");

			VersionListFrame versionListFrame = new VersionListFrame(ID, list);
			versionListFrame.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
					String code = null;

					try {
						if (versionListFrame.getChoise() != null) {
							code = ClientRunner.remoteHelper.getIOService().readVersion2(ID, project,
									versionListFrame.getChoise());
						}
					} catch (RemoteException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}

					textArea.setText(code);
				}
			});

		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return selection;

	}

	private void NewProject() {
		// TODO 自动生成的方法存根
		project = null;
	}
	
	private void undo() {
		if (temp.size()>1) {
			textArea.setText(temp.get(temp.size()-2));
			temp2.add(temp.get(temp.size()-1));
			temp.remove(temp.size()-1);
		}

	}
	
	private void redo() {
		if (!temp2.isEmpty()) {
			textArea.setText(temp2.get(temp2.size()-1));
			temp.add(temp2.get(temp2.size()-1));
			temp2.remove(temp2.size()-1);
		}
	}
}
