package serviceImpl;

import java.io.*;
import java.rmi.RemoteException;

import service.UserService;

public class UserServiceImpl implements UserService{
	String nowOn;
	File userMessage = new File("userMessage.txt") ;
	String[][] users = new String[100][2];
	/*
	 * 构�?�方法，初始化users数组，判断文件是否存在�??
	 */
	public UserServiceImpl() {
		if (!userMessage.exists()) {
			try {
				userMessage.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (String[] strings : users) {
			strings[0] = "";
			strings[1] = "";
		}
	}
	
	@Override
	public boolean login(String username, String password) throws RemoteException {
		load();
		for (String[] strings : users) {
			if (strings[0].equals(username)) {
				if (strings[1].equals(password)) {
					nowOn = username;
					return true;
				}else {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public boolean logout() throws RemoteException {
		if (nowOn!=null) {
			nowOn = null;
			return true;
		}else {
			return false;
		}		
	}

	@Override
	public boolean Register(String username, String password) throws RemoteException {
		load();
		for (String[] strings : users) {
			if (strings[0].equals(username)) {
				return false;
			}
		}
		try {
			FileWriter fWriter = new FileWriter(userMessage,true);
			fWriter.write(username+"&"+password+"\r\n");
			fWriter.close();
			File dir = new File(username);
			dir.mkdirs();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	/*
	 * 加载文件中用户信息到string数组�?
	 */
	public void load() {
		String string ="";
		int i = 0;
		try {
			FileReader Reader = new FileReader(userMessage);
			BufferedReader bufferedReader = new BufferedReader(Reader);
			while ((string=bufferedReader.readLine())!=null) {
				users[i] = string.split("&");
				i++;
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
