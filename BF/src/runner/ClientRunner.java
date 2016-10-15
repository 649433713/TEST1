package runner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi.RemoteHelper;
import ui.MainFrame;

public class ClientRunner {
	static public RemoteHelper remoteHelper = new RemoteHelper();
	
	public ClientRunner() {
		linkToServer();
		initGUI();
	}
	
	private void linkToServer() {
		try {
			remoteHelper.setRemote(Naming.lookup("rmi://127.0.0.1:8888/DataRemoteObject"));
			System.out.println("linked");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private void initGUI() {
		MainFrame mainFrame = new MainFrame();
	}
	
	
	
	public static void main(String[] args){
		ClientRunner cr = new ClientRunner();
	}
}
