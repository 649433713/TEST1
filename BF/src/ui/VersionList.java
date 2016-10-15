package ui;

public class VersionList implements Runnable {
	MainFrame mainFrame;
	public VersionList(MainFrame mainFrame) {
		// TODO 自动生成的构造函数存根
		this.mainFrame = mainFrame;
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		String str1,str2;
		while (true) {
			str1 = mainFrame.getCode();

	 		 try {
		Thread.sleep(500);
	} catch (InterruptedException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
			if (!(str1.equals(""))) {
				MainFrame.temp.add(str1);
				break;
			}	
		}
		 str2 = mainFrame.getCode();
		 while (true) {
			 if (!(str2.equals(MainFrame.temp.get(MainFrame.temp.size()-1)))) {
				 MainFrame.temp.add(str2);				 
			}
			 try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				 str2 = mainFrame.getCode();
		}
	}

}
